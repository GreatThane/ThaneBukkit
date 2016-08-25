package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.thane.command.Hunger;
import org.thane.command.ThaneTimer;

public class ThaneBukkit extends JavaPlugin {



    @Override
    public void onEnable() {
        getLogger().info("ThaneBukkit has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("ThaneBukkit has been disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sethunger")) {

           return new Hunger().handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("timer")) {

            return new ThaneTimer().handleCommand(sender, args);
        }
        return false;
    }


}
