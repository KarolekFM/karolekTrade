package net.karolek.trade.listeners;

import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import net.karolek.trade.data.TradeHolder;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class PlayerLeaveListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        cancelTrade(event.getPlayer());
    }
    
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerKick(final PlayerKickEvent event) {
        cancelTrade(event.getPlayer());
    }

    private void cancelTrade(Player p) {
        if (!(p.getOpenInventory().getTopInventory().getHolder() instanceof TradeHolder)) {
            return;
        }
        final TradeHolder tradeHolder = (TradeHolder)p.getOpenInventory().getTopInventory().getHolder();
        if (tradeHolder.getTradePlayer().getTrade().isCanClose()) {
            return;
        }
        tradeHolder.getTradePlayer().getTrade().cancelTrade();
    }
}
