package net.karolek.trade.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import net.karolek.trade.data.TradeHolder;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.Listener;

public class InventoryCloseListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClose(final InventoryCloseEvent event) {
        if (!(event.getInventory().getHolder() instanceof TradeHolder))
            return;

        final TradeHolder tradeHolder = (TradeHolder)event.getInventory().getHolder();
        if (tradeHolder.getTradePlayer().getTrade().isCanClose())
            return;

        tradeHolder.getTradePlayer().getTrade().cancelTrade();
    }
}
