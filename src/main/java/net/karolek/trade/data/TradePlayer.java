package net.karolek.trade.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.trade.Config;
import net.karolek.trade.Message;
import net.karolek.trade.utils.ChatUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@Setter
public class TradePlayer
{
    private final Trade trade;
    private final Player player;
    private final Player oppositePlayer;
    private final TradeHolder tradeHolder;
    private TradeStatus tradeStatus;
    
    public TradePlayer(final Trade trade, final Player player) {
        this.tradeStatus = TradeStatus.NOT_READY;
        this.trade = trade;
        this.player = player;
        this.oppositePlayer = this.getTrade().getOppositePlayer(this.getPlayer());
        this.tradeHolder = new TradeHolder(this, Config.INVENTORY_NAME.replace("{PLAYER}", this.getOppositePlayer().getName()));
        this.getPlayer().openInventory(this.getTradeHolder().getInventory());
        this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
    }
    
    public void refreshTraderItems() {
        for (final Map.Entry<Integer, ItemStack> e : this.getTradeHolder().getTradeItems().entrySet()) {
            this.setTraderItem(e.getKey() + 5, e.getValue());
        }
        this.getPlayer().updateInventory();
        this.getOppositePlayer().updateInventory();
    }
    
    public void giveItems(final Player player) {
        for (final Map.Entry<Integer, ItemStack> e : player.getInventory().addItem((ItemStack[])this.getTradeHolder().getTradeItems().values().toArray(new ItemStack[this.getTradeHolder().getTradeItems().values().size()])).entrySet()) {
            player.getWorld().dropItemNaturally(player.getLocation(), (ItemStack)e.getValue());
        }
        player.updateInventory();
        ChatUtil.send(player, Message.NO$ITEMS$INFO);
    }
    
    public void setItem(final int slot, final ItemStack itemStack) {
        this.getTradeHolder().getInventory().setItem(slot, itemStack);
    }
    
    public void setTraderItem(final int slot, final ItemStack itemStack) {
        this.getTrade().getOppositeTradePlayer(this).getTradeHolder().getInventory().setItem(slot, itemStack);
    }
    
    public void greenClick() {
        if (this.tradeStatus == TradeStatus.NOT_READY) {
            this.setTradeStatus(TradeStatus.READY);
            this.refreshTraderItems();
        }
        else if (this.tradeStatus == TradeStatus.READY) {
            this.setTradeStatus(TradeStatus.ACCEPTED);
        }
        this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        if (this.getTradeStatus() == TradeStatus.ACCEPTED && this.getTrade().getOppositeTradePlayer(this).getTradeStatus() == TradeStatus.ACCEPTED) {
            this.getTrade().doTrade();
        }
    }
    
    public void redClick() {
        if (this.tradeStatus == TradeStatus.READY) {
            this.setTradeStatus(TradeStatus.NOT_READY);
        }
        else if (this.tradeStatus == TradeStatus.ACCEPTED) {
            this.setTradeStatus(TradeStatus.READY);
        }
        this.getPlayer().playSound(this.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }
    
    public void setTradeStatus(final TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
        switch (this.tradeStatus) {
            case NOT_READY:
                this.setItem(0, Config.INVENTORY_ICONS_NOT$READY.getItemStack(this.getPlayer()));
                this.setTraderItem(8, Config.INVENTORY_ICONS_NOT$READY.getItemStack(this.getPlayer()));
                break;
            case READY:
                this.setItem(0, Config.INVENTORY_ICONS_READY.getItemStack(this.getPlayer()));
                this.setTraderItem(8, Config.INVENTORY_ICONS_READY.getItemStack(this.getPlayer()));
                break;
            case ACCEPTED:
                this.setItem(0, Config.INVENTORY_ICONS_ACCEPTED.getItemStack(this.getPlayer()));
                this.setTraderItem(8, Config.INVENTORY_ICONS_ACCEPTED.getItemStack(this.getPlayer()));
                break;

        }
    }

}
