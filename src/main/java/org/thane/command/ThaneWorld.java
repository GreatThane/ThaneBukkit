package org.thane.command;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ThaneWorld {

    public static boolean handleCommand(CommandSender sender, String[] args) {

        if (!(args.length == 4 || args.length == 6)) {
            sender.sendMessage(ChatColor.RED + "Incorrect number of parameters!");
            return false;
        }
        double x = Integer.parseInt(args[1]) + 0.5;
        int y = Integer.parseInt(args[2]);
        double z = Integer.parseInt(args[3]) + 0.5;
        World world = Bukkit.getWorld(args[0]);
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        Location location = new Location(world, x, y, z);
        if (args.length == 6) {
            int pitch = Integer.parseInt(args[4]);
            int yaw = Integer.parseInt(args[5]);
            location = new Location(world, x, y, z, pitch, yaw);
        }
        teleportWorld(player, location);
        return true;
    }

    public static void teleportWorld(Player player, Location location) {
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Game Selector");
        itemStack.setItemMeta(itemMeta);
        player.setFireTicks(0);
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getActivePotionEffects().clear();
        player.getInventory().setItem(0, itemStack);
        player.teleport(location);
        player.setGameMode(GameMode.ADVENTURE);
    }
}
