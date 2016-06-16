
package net.karolek.trade;

import net.karolek.trade.data.Trade;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

public class TradeManager
{
    private static final Set<Trade> trades = new HashSet<>();
    private static final WeakHashMap<Player, UUID> tradeInvites = new WeakHashMap<>();
    
    public static boolean hasInvite(final Player inviter, final Player invited) {
        return TradeManager.tradeInvites.containsKey(invited) && TradeManager.tradeInvites.get(invited).equals(inviter.getUniqueId());
    }
    
    public static void addInvite(final Player inviter, final Player invited) {
        TradeManager.tradeInvites.put(inviter, invited.getUniqueId());
    }
    
    public static void resetInvite(final Player player) {
        TradeManager.tradeInvites.remove(player);
    }
    
    public static Trade getTrade(final Player player) {
        for (final Trade t : TradeManager.trades)
            if (t.getTradePlayerTwo().getPlayer().equals(player) || t.getTradePlayerOne().getPlayer().equals(player))
                return t;
        return null;
    }
    
    public static void addTrade(final Trade trade) {
        TradeManager.trades.add(trade);
    }
    
    public static void removeTrade(final Trade trade) {
        TradeManager.trades.remove(trade);
    }
    
    public static Set<Trade> getTrades() {
        return TradeManager.trades;
    }

}
