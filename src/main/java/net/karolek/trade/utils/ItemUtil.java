package net.karolek.trade.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public final class ItemUtil
{
    public static ItemStack createNamedItem(final Material material, final byte data, final int amount, final String name, final List<String> lore) {
        final ItemStack itemStack = new ItemStack(material, amount, (short)data);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        final List<String> lores = new ArrayList<String>();
        if (lore != null) {
            for (final String s : lore) {
                lores.add(ChatColor.translateAlternateColorCodes('&', s));
            }
        }
        itemMeta.setLore((List)lores);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
