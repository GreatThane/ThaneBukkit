package org.thane.command;

import org.bukkit.Difficulty;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.thane.Utils;

/**
 * Created by ethan on 8/24/16.
 */
public class Hunger {

    public static boolean handleCommand(CommandSender sender, String[] args) {

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
}
