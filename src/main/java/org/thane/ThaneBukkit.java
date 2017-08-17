package org.thane;

/**
 * Created by GreatThane on 8/16/16.
 */

import org.bukkit.Bukkit;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.thane.command.*;

public class ThaneBukkit extends JavaPlugin implements Listener {


    public static Plugin plugin() {

        return ThaneBukkit.getPlugin(ThaneBukkit.class);
    }

    @Override
    public void onEnable() {
        getLogger().info("ThaneBukkit has been enabled");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new ArmorStandClick(), this);
        this.getServer().getPluginManager().registerEvents(new Hunger(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerVsParkour(), this);

        if(!plugin().getDataFolder().exists()) {
            plugin().getDataFolder().mkdirs();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("ThaneBukkit has been disabled");
        Bukkit.getScheduler().cancelTasks(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, 172, 133, 197, 90, 0);
        Player player = event.getPlayer();
        player.getInventory().clear();
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Game Selector");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(0, itemStack);
        player.teleport(location);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sethunger")) {

           return Hunger.handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("timer")) {

            return new ThaneTimer().handleCommand(sender, args);
        }
        else if (command.getName().equalsIgnoreCase("pvp")) {

            return new PlayerVsParkour().handleCommand(sender, args, this);
        }
        else if (command.getName().equalsIgnoreCase("survivorminer")) {

            return new Zombies().handleCommand(sender, args, this);
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
        else if (command.getName().equalsIgnoreCase("thaneworld")) {
            return ThaneWorld.handleCommand(sender, args);
        }
        return false;
    }
}
