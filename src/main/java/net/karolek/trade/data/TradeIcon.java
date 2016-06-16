package net.karolek.trade.data;

import lombok.Getter;
import net.karolek.trade.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SerializableAs("TradeIcon")
@Getter
public class TradeIcon implements ConfigurationSerializable
{
    private final Material material;
    private final byte data;
    private final int amount;
    private final String name;
    private final List<String> lore;
    
    public TradeIcon(final Material material, final byte data, final int amount, final String name, final List<String> lore) {
        this.material = material;
        this.data = data;
        this.amount = amount;
        this.name = name;
        this.lore = ((lore == null) ? new ArrayList<>() : lore);
    }
    
    public ItemStack getItemStack(final Player player) {
        return ItemUtil.createNamedItem(this.getMaterial(), this.getData(), this.getAmount(), this.getName().replace("{PLAYER}", player.getName()), this.getLore());
    }
    
    public Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();
        map.put("material", this.material.name());
        map.put("data", this.data);
        map.put("amount", this.amount);
        map.put("name", this.name);
        map.put("lore", this.lore);
        return map;
    }
    
    public static TradeIcon deserialize(final Map<String, Object> map) {
        Material material = null;
        byte data = 0;
        int amount = 1;
        String name = "";
        final List<String> lore = new ArrayList<>();
        if (map.containsKey("material")) {
            material = Material.matchMaterial((String)map.get("material"));
        }
        if (map.containsKey("data")) {
            data = ((Number)map.get("data")).byteValue();
        }
        if (map.containsKey("amount")) {
            amount = ((Number)map.get("amount")).intValue();
        }
        if (map.containsKey("name")) {
            name = (String) map.get("name");
        }
        if (map.containsKey("lore")) {
            lore.addAll((Collection<? extends String>) map.get("lore"));
        }
        if (material == null) {
            throw new IllegalArgumentException("Material can not be null");
        }
        return new TradeIcon(material, data, amount, name, lore);
    }

}
