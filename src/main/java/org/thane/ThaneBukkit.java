package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.thane.command.*;

public class ThaneBukkit extends JavaPlugin {


    public static Plugin plugin() {

        return ThaneBukkit.getPlugin(ThaneBukkit.class);
    }

    @Override
    public void onEnable() {
        getLogger().info("ThaneBukkit has been enabled");
        this.getServer().getPluginManager().registerEvents(new ArmorStandClick(), this);
        this.getServer().getPluginManager().registerEvents(new Hunger(), this);
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
        else if (command.getName().equalsIgnoreCase("survivorminer")) {

            return new Zombies().handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("animate")) {

            return new Animation().handleCommand(sender, args, this);
        }
        else if (command.getName().equalsIgnoreCase("optool")) {
            return OpTools.handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("thanereload")) {
            Utils.reloadPlugin(sender);
            return true;
        }
        else if (command.getName().equalsIgnoreCase("zombiebuy")) {
            return ZombieBuy.handleCommand(sender, args);
        }
        return false;
    }




}
