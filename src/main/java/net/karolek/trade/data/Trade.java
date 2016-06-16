package net.karolek.trade.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.trade.Message;
import net.karolek.trade.TradeManager;
import net.karolek.trade.utils.ChatUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;


@Getter
@Setter
public class Trade {

    private final Player playerOne;
    private final Player playerTwo;
    private final TradePlayer tradePlayerOne;
    private final TradePlayer tradePlayerTwo;
    private boolean canClose;

    public Trade(final Player playerOne, final Player playerTwo) {
        this.canClose = false;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.tradePlayerOne = new TradePlayer(this, playerOne);
        this.tradePlayerTwo = new TradePlayer(this, playerTwo);
    }

    public Player getOppositePlayer(final Player player) {
        if (this.getPlayerOne().equals(player))
            return this.getPlayerTwo();
        if (this.getPlayerTwo().equals(player))
            return this.getPlayerOne();
        throw new IllegalArgumentException();
    }

    public TradePlayer getOppositeTradePlayer(final TradePlayer tradePlayer) {
        if (this.getTradePlayerOne().equals(tradePlayer))
            return this.getTradePlayerTwo();
        if (this.getTradePlayerTwo().equals(tradePlayer))
            return this.getTradePlayerOne();
        throw new IllegalArgumentException();
    }

    public void doTrade() {
        this.canClose = true;
        this.getTradePlayerOne().giveItems(this.getTradePlayerOne().getOppositePlayer());
        this.getTradePlayerTwo().giveItems(this.getTradePlayerTwo().getOppositePlayer());
        this.getPlayerOne().closeInventory();
        this.getPlayerTwo().closeInventory();
        this.getPlayerOne().playSound(this.getPlayerOne().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
        this.getPlayerTwo().playSound(this.getPlayerTwo().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
        ChatUtil.send(this.getPlayerOne(), Message.THANK$YOU);
        ChatUtil.send(this.getPlayerTwo(), Message.THANK$YOU);
        TradeManager.removeTrade(this);
    }

    public void cancelTrade() {
        this.canClose = true;
        this.getTradePlayerOne().giveItems(this.getPlayerOne());
        this.getTradePlayerTwo().giveItems(this.getPlayerTwo());
        this.getPlayerOne().closeInventory();
        this.getPlayerTwo().closeInventory();
        this.getPlayerOne().playSound(this.getPlayerOne().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
        this.getPlayerTwo().playSound(this.getPlayerTwo().getLocation(), Sound.BLOCK_CHEST_CLOSE, 1.0f, 1.0f);
        TradeManager.removeTrade(this);
    }

}
