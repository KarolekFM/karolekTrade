package net.karolek.trade.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import net.karolek.trade.data.Trade;
import org.bukkit.entity.Player;
import net.karolek.trade.TradeManager;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.Listener;

public class PlayerPickupItemListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickupEvent(final PlayerPickupItemEvent event) {
        final Player p = event.getPlayer();
        final Trade t = TradeManager.getTrade(p);
        if (t != null) {
            event.setCancelled(true);
        }
    }
}
