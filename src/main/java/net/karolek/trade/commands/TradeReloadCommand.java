package net.karolek.trade.commands;

import net.karolek.trade.Message;
import net.karolek.trade.TradeManager;
import net.karolek.trade.TradePlugin;
import net.karolek.trade.data.Trade;
import net.karolek.trade.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TradeReloadCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!commandSender.hasPermission("tradeplugin.command.reload"))
            return ChatUtil.send(commandSender, Message.NO$PERMISSIONS);

        TradePlugin.getTradeConfig().reloadConfig();
        TradePlugin.getTradeMessage().reloadConfig();
        for (final Trade t : TradeManager.getTrades()) {
            t.cancelTrade();
            ChatUtil.send(t.getPlayerOne(), Message.CANCELLED$BY$ADMIN, t.getPlayerTwo().getName());
            ChatUtil.send(t.getPlayerTwo(), Message.CANCELLED$BY$ADMIN, t.getPlayerOne().getName());
        }
        return ChatUtil.send(commandSender, Message.RELOADED);
    }
}
