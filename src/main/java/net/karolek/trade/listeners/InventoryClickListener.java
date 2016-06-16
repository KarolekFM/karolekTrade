package net.karolek.trade.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import net.karolek.trade.data.TradeHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;

public class InventoryClickListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player p = (Player)event.getWhoClicked();
        if (!(event.getInventory().getHolder() instanceof TradeHolder))
            return;

        if (event.isShiftClick()) {
            event.setCancelled(true);
            return;
        }
        if (event.getClick() == ClickType.RIGHT || event.getClick() == ClickType.LEFT) {
            ((TradeHolder)event.getInventory().getHolder()).onClick(event);
            return;
        }
        event.setCancelled(true);
    }
}
