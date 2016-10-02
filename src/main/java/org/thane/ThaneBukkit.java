package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.thane.command.Animation;
import org.thane.command.Hunger;
import org.thane.command.ThaneTimer;
import org.thane.command.Zombies;

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

           return new Hunger().handleCommand(sender, args, this);
        }
        else if (command.getName().equalsIgnoreCase("timer")) {

            return new ThaneTimer().handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("survivorminer")) {

            return new Zombies().handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("animate")) {

            return new Animation().handleCommand(sender, args, this);
        }
        return false;
    }


}
