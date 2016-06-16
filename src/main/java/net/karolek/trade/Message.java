package net.karolek.trade;

import net.karolek.trade.configuration.bukkit.BukkitConfiguration;

public class Message extends BukkitConfiguration
{
    public static String NO$PERMISSIONS = "&4Blad: &cNie masz praw do uzycia tej komendy!";
    public static String RELOADED = "&aPrzeladowano plik config.yml oraz messages.yml";
    public static String THANK$YOU = "&aDziekujemy za wymiane i skorzystanie z &7karolekTrade&a! Milego dnia!";
    public static String NO$ITEMS$INFO = "&7Nie pojawily Ci sie Twoje przedmioty z wymiany? Przeloguj sie!";
    public static String ALREADY$IN$TRADE = "&4Blad: &cTen gracz jest w trakcie wymiany!";
    public static String ALREADY$INVITE = "&4Blad: &cZaprosiles juz tego gracza, poczekaj az odpowie na Twoja prosbe!";
    public static String INVITE = "&aZaprosiles gracza &7{0} &ado wymiany!";
    public static String INVITED = "&aZostales zaproszony do wymiany z graczem &7{0}&a! Aby zaakceptowac kliknij na niego PPM+Shift!";
    public static String CANCELLED$BY$ADMIN = "&aWymiana z graczem &7{0} &azostala zakonczona przez administratora!";
    
    public Message() {
        super(TradePlugin.getPlugin(), "messages.yml", "messages.");
    }

}
