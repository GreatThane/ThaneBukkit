package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

public class ThaneBukkit extends JavaPlugin implements Listener {
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
                sender.sendMessage("Uh oh, I don't understand what you want!");
                return false;
            }

            String userName = args[0];
            int hungerValue = -1;

            try {
                hungerValue = Integer.parseInt(args[1]);
                if (hungerValue < 0 || hungerValue > 20) {
                    sender.sendMessage("Oops, hunger value should be between 0 and 20");
                    return false;
                }
            } catch (NumberFormatException ex) {
                sender.sendMessage("Looks like your second parameter was not an integer!");
                return false;
            }

            for (Player player: Bukkit.getOnlinePlayers()) {
                if (player.getName().equalsIgnoreCase(userName)) {
                    player.setFoodLevel(hungerValue);
                    player.getWorld().setDifficulty(Difficulty.EASY);
                    sender.sendMessage(userName + "hunger set to " + hungerValue);
                    return true;
                }
            }

            sender.sendMessage("Unable to find any players online named " + userName);

        }
        return false;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFoodLevelChange(final FoodLevelChangeEvent event) {
        //This stops food level from changing due to exhaustion and eating
        //HOWEVER. it doesn't stop food level from increasing due to saturation.  Run difficulty EASY or more.
        event.setCancelled(true);
    }

}
