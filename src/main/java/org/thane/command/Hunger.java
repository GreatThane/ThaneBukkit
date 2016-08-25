package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.thane.Utils;

/**
 * Created by ethan on 8/24/16.
 */
public class Hunger extends JavaPlugin implements Listener {

    public boolean handleCommand(CommandSender sender, String[] args) {

        Bukkit.getServer().getPluginManager().registerEvents(this, this);
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

        Player player = Utils.getOnlinePlayer(userName);
        if (player == null) {
            sender.sendMessage("§cUnable to find any players online named " + userName);
            return false;
        }

        player.setFoodLevel(hungerValue);
        player.getWorld().setDifficulty(Difficulty.EASY);
        sender.sendMessage("§a" + userName + " hunger set to " + hungerValue);

        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        //This stops food level from changing due to exhaustion and eating
        //HOWEVER. it doesn't stop food level from increasing due to saturation.  Run difficulty EASY or more.
        event.setCancelled(true);
    }
}
