package net.karolek.trade;

import net.karolek.trade.configuration.bukkit.BukkitConfiguration;
import net.karolek.trade.data.TradeIcon;
import org.bukkit.Material;

public class Config extends BukkitConfiguration
{
    public static String INVENTORY_NAME = "Handel z &c{PLAYER}";
    public static TradeIcon INVENTORY_ICONS_ACCEPT = new TradeIcon(Material.INK_SACK, (byte)10, 1, "&aPotwierdz wymiane", null);
    public static TradeIcon INVENTORY_ICONS_DENY = new TradeIcon(Material.INK_SACK, (byte)1, 1, "&cOdrzuc wymiane", null);
    public static TradeIcon INVENTORY_ICONS_NOT$READY = new TradeIcon(Material.STAINED_CLAY, (byte)14, 1, "&c{PLAYER} nie jest gotowy do wymiany", null);
    public static TradeIcon INVENTORY_ICONS_READY = new TradeIcon(Material.STAINED_CLAY, (byte)4, 1, "&6{PLAYER} jest gotowy do wymiany", null);
    public static TradeIcon INVENTORY_ICONS_ACCEPTED = new TradeIcon(Material.STAINED_CLAY, (byte)5, 1, "&a{PLAYER} zaakceptowal wymiane", null);
    public static TradeIcon INVENTORY_ICONS_SEPARATOR = new TradeIcon(Material.STAINED_GLASS_PANE, (byte)15, 1, "", null);
    
    public Config() {
        super(TradePlugin.getPlugin(), "config.yml", "config.");
    }

}
