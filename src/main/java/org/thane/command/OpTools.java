package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thane.Utils;

/**
 * Created by GreatThane on 11/24/16.
 */
public class OpTools {

    public static boolean handleCommand(CommandSender sender, String[] args) {

        String commandType = args[0];
        Player player = (Player) sender;
        switch (commandType.toLowerCase()) {
            case "setcurrency":
                if(args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Incorrect parameters!");
                    return false;
                }
                String value = args[1];
                int amount = Integer.parseInt(value);
                Utils.setCurrency(player, amount);
                break;

            case "showcurrency":
                sender.sendMessage(ChatColor.YELLOW + "You have " + Utils.getCurrency(player));
                break;

            case "settings":
                for (Player player1 : player.getWorld().getPlayers()) {
                    if (player1.getInventory().contains(Material.BLAZE_ROD)) {
                        player1.getInventory().remove(Material.BLAZE_ROD);
                    }
                }
                ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (player.getWorld().equals(Bukkit.getServer().getWorld("arena"))) {
                    itemMeta.setDisplayName(ChatColor.GOLD + "Arena Settings");
                }
                player.getInventory().setItem(4, itemStack);
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Not an option! Options are:" + ChatColor.YELLOW + ChatColor.ITALIC + " SetCurrency, ShowCurrency");
                break;
        }

        return true;
    }
}