package org.thane.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.thane.Utils;

/**
 * Created by GreatThane on 11/24/16.
 */
public class OpTools {

    public static boolean handleCommand(CommandSender sender, String[] args) {

        String commandType = args[0];
        Player player = (Player) sender;
        String userName = player.getName();
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

            default:
                sender.sendMessage(ChatColor.RED + "Not an option! Options are:" + ChatColor.YELLOW + ChatColor.ITALIC + " SetCurrency, ShowCurrency");
                break;
        }

        return true;
    }
}