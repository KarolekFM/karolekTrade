package net.karolek.trade.listeners;

import net.karolek.trade.Message;
import net.karolek.trade.TradeManager;
import net.karolek.trade.data.Trade;
import net.karolek.trade.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(final PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) {
            return;
        }
        final Player p = event.getPlayer();
        final Player o = (Player)event.getRightClicked();

        final Trade ot = TradeManager.getTrade(o);
        if (ot != null) {
            ChatUtil.send(p, Message.ALREADY$IN$TRADE);
            return;
        }
        if (!p.isSneaking())
            return;

        if (TradeManager.hasInvite(p, o)) {
            TradeManager.resetInvite(p);
            TradeManager.resetInvite(o);
            final Trade trade = new Trade(p, o);
            TradeManager.addTrade(trade);
            return;
        }
        if (TradeManager.hasInvite(o, p)) {
            ChatUtil.send(p, Message.ALREADY$INVITE);
            return;
        }
        TradeManager.addInvite(p, o);
        ChatUtil.send(p, Message.INVITE, o.getName());
        ChatUtil.send(o, Message.INVITED, p.getName());
    }
}
