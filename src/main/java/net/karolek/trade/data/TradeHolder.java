package net.karolek.trade.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.trade.Config;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@Getter
@Setter
public class TradeHolder implements InventoryHolder{

    private final TradePlayer tradePlayer;
    private Inventory inventory;
    public static final int[] ALLOWED_SLOTS =  { 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30, 39, 36, 37, 38, 39, 45, 46, 47, 48 };

    public TradeHolder(final TradePlayer tradePlayer, final String title) {
        this.tradePlayer = tradePlayer;
        this.inventory = Bukkit.createInventory(this, 54, ChatColor.translateAlternateColorCodes('&', title));
        this.setupInventory(this.getInventory());
    }
    
    public HashMap<Integer, ItemStack> getTradeItems() {
        final HashMap<Integer, ItemStack> items = new HashMap<>();
        for (int i = 9; i <= 48; i += 9) {
            for (int j = 0; j < 4; ++j) {
                final int slotId = i + j;
                final ItemStack itemStack = this.getInventory().getItem(slotId);
                if (itemStack != null) {
                    if (itemStack.getType() != Material.AIR) {
                        items.put(slotId, itemStack);
                    }
                }
            }
        }
        return items;
    }
    
    public void onClick(final InventoryClickEvent event) {
        final Player p = (Player)event.getWhoClicked();
        final int clickedSlot = event.getRawSlot();
        if (!this.contains(TradeHolder.ALLOWED_SLOTS, clickedSlot)) {
            event.setCancelled(true);
            return;
        }
        if (clickedSlot == 2) {
            this.tradePlayer.redClick();
            event.setCancelled(true);
            return;
        }
        if (clickedSlot == 3) {
            this.tradePlayer.greenClick();
            event.setCancelled(true);
            return;
        }
        if (clickedSlot <= 53) {
            this.tradePlayer.setTraderItem(clickedSlot + 5, event.getCursor());
            if (this.getTradePlayer().getTradeStatus() != TradeStatus.NOT_READY) {
                this.getTradePlayer().setTradeStatus(TradeStatus.NOT_READY);
                this.getTradePlayer().getTrade().getOppositeTradePlayer(this.getTradePlayer()).setTradeStatus(TradeStatus.NOT_READY);
            }
        }
    }
    
    private void setupInventory(final Inventory inv) {
        inv.setItem(0, Config.INVENTORY_ICONS_NOT$READY.getItemStack(this.getTradePlayer().getPlayer()));
        inv.setItem(2, Config.INVENTORY_ICONS_DENY.getItemStack(this.getTradePlayer().getPlayer()));
        inv.setItem(3, Config.INVENTORY_ICONS_ACCEPT.getItemStack(this.getTradePlayer().getPlayer()));
        inv.setItem(8, Config.INVENTORY_ICONS_NOT$READY.getItemStack(this.getTradePlayer().getOppositePlayer()));
        for (int i = 4; i < 53; i += 9)
            inv.setItem(i, Config.INVENTORY_ICONS_SEPARATOR.getItemStack(this.getTradePlayer().getPlayer()));

    }
    
    private boolean contains(final int[] array, final int check) {
        if (check >= 54 && check <= 89)
            return true;
        for (final int a : array)
            if (a == check)
                return true;
        return false;
    }

}
