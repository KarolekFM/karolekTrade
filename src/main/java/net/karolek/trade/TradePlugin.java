package net.karolek.trade;

import lombok.Getter;
import net.karolek.trade.commands.TradeReloadCommand;
import net.karolek.trade.data.Trade;
import net.karolek.trade.data.TradeIcon;
import net.karolek.trade.listeners.*;
import net.karolek.trade.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class TradePlugin extends JavaPlugin
{
    @Getter
    private static TradePlugin plugin;
    @Getter
    private static Config tradeConfig;
    @Getter
    private static Message tradeMessage;
    
    public void onLoad() {
        TradePlugin.plugin = this;
    }
    
    public void onEnable() {
        TradePlugin.tradeConfig = new Config();
        TradePlugin.tradeMessage = new Message();
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItemListener(), this);
        getCommand("tradereload").setExecutor(new TradeReloadCommand());
    }
    
    public void onDisable() {
        for (final Trade t : TradeManager.getTrades()) {
            t.cancelTrade();
            ChatUtil.send(t.getPlayerOne(), Message.CANCELLED$BY$ADMIN, t.getPlayerTwo().getName());
            ChatUtil.send(t.getPlayerTwo(), Message.CANCELLED$BY$ADMIN, t.getPlayerOne().getName());
        }
    }

    
    static {
        ConfigurationSerialization.registerClass(TradeIcon.class, "TradeIcon");
    }
}
