package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class ThaneBukkit extends JavaPlugin implements Listener {

    private static HashMap<String, PlayerTimer> playerTimers = new HashMap<String, PlayerTimer>();

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("ThaneBukkit has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("ThaneBukkit has been disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sethunger")) {

            // Validation

            if (args.length != 2) {
                sender.sendMessage("§cUh oh, I don't understand what you want!");
                return false;
            }

            String userName = args[0];
            int hungerValue = -1;

            try {
                hungerValue = Integer.parseInt(args[1]);
                if (hungerValue < 0 || hungerValue > 20) {
                    sender.sendMessage("§cOops, hunger value should be between 0 and 20");
                    return false;
                }
            } catch (NumberFormatException ex) {
                sender.sendMessage("§cLooks like your second parameter was not an integer!");
                return false;
            }

            Player player = getOnlinePlayer(userName);
            if (player == null) {
                sender.sendMessage("§cUnable to find any players online named " + userName);
                return false;
            }

            player.setFoodLevel(hungerValue);
            player.getWorld().setDifficulty(Difficulty.EASY);
            sender.sendMessage("§a" + userName + " hunger set to " + hungerValue);

            return true;
        }
        else if (command.getName().equalsIgnoreCase("timer")) {

            //validation

            if (args.length != 2) {
                sender.sendMessage("Wrong number of parameters");
                return false;
            }

            String userName = args[0];
            String action = args[1];

            Player player = getOnlinePlayer(userName);
            if(player == null) {
                sender.sendMessage("§cUnable to find any players online named " + userName);
                return false;
            }

            switch (action.toLowerCase()) {
                case "start":
                    //make sure player doesn't already have a running timer
                    if (playerTimers.containsKey(player.getName())) {
                        sender.sendMessage(player.getName() + " already has a timer running!");
                        return false;
                    }

                    //add new timer for this player
                    Timer timer = new Timer();
                    sendTitle(sender, player, "§6GO!");
                    timer.scheduleAtFixedRate(new Task(player, sender), 1000, 1000);
                    PlayerTimer playerTimer = new PlayerTimer(sender, player, 0, timer);
                    playerTimers.put(player.getName(), playerTimer);
                    return true;
                case "stop":
                    //find the right timer!
                    if(!playerTimers.containsKey(player.getName())) {
                        sender.sendMessage("No timer running for " + player.getName());
                        return false;
                    }

                    //Stop the timer!
                    PlayerTimer playerToStop = playerTimers.get(player.getName());
                    Timer timerToStop = playerToStop.getTimer();
                    timerToStop.cancel();
                    sendTitle(sender, player, "§aTotal Time §2" + formatTime(playerToStop.getSeconds()), "");
                    playerTimers.remove(player.getName());
                    return true;
                default:
                    sender.sendMessage("Not a timer option!");
                    break;
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        //This stops food level from changing due to exhaustion and eating
        //HOWEVER. it doesn't stop food level from increasing due to saturation.  Run difficulty EASY or more.
        event.setCancelled(true);
    }

    private class Task extends TimerTask {

        private Player player;
        private CommandSender sender;

        public Task(Player player, CommandSender sender) {
            this.player = player;
            this.sender = sender;
        }

        @Override
        public void run() {
            if(!playerTimers.containsKey(player.getName())) {
                this.cancel();
                return;
            }
            PlayerTimer playerTimer = playerTimers.get(player.getName());

            if (player.isOnline() && playerTimer.getSeconds() < 3599) {
                playerTimer.incrementSeconds();
                sendTitle(sender, player, "", "§a" + formatTime(playerTimer.getSeconds()));
            } else {
                this.cancel();
                playerTimers.remove(player.getName());
            }
        }
    }

    private void sendTitle(CommandSender sender, Player player, String title) {

        sendTitle(sender, player, title, null);
    }

    private void sendTitle(CommandSender sender, Player player, String title, String subTitle) {

        if (subTitle != null) {
            player.getServer().dispatchCommand(sender, "title " + player.getName() + " subtitle {\"text\":\"" + subTitle + "\"}");
        }
        player.getServer().dispatchCommand(sender, "title " + player.getName() + " title {\"text\":\"" + title + "\"}");
    }

    private Player getOnlinePlayer(String userName) {

        for (Player player: Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(userName)) {
                return player;
            }
        }
        return null;
    }

    private String formatTime (int seconds) {

        int minutes = seconds / 60;
        int remainderSeconds = seconds - (minutes * 60);
        return String.format("%1d:%2$02d", minutes, remainderSeconds);
    }
}
