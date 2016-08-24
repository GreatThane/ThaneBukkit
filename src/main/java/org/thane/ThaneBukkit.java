package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.thane.command.Hunger;
import org.thane.command.ThaneTimer;

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

           return Hunger.handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("timer")) {

            return new ThaneTimer().handleCommand(sender, args);
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
