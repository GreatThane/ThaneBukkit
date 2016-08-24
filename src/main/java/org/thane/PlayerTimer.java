package org.thane;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Timer;

/**
 * Created by GreatThane on 8/24/16.
 */
public class PlayerTimer {

    private CommandSender sender;
    private Player player;
    private int seconds;
    private Timer timer;


    public PlayerTimer(CommandSender sender, Player player, int seconds, Timer timer) {
        this.sender = sender;
        this.player = player;
        this.seconds = seconds;
        this.timer = timer;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSeconds() {
        return seconds;
    }

    public void incrementSeconds() {
        seconds++;
    }

    public Timer getTimer() {
        return timer;
    }
}
