package org.thane.command;

import org.bukkit.command.CommandSender;

/**
 * Created by GreatThane on 9/17/16.
 */
public class Zombies {

    public boolean handleCommand(CommandSender sender, String[] args) {

        //Validation

        if (args.length != 1) {
            sender.sendMessage("§cIncorrect number of arguments!");
            return false;
        }

        String commandType = args[0];

        switch (commandType) {
            case "shop":
                break;

            case "stop":
                break;

            case "start":
                break;
            case "setup":
                break;
            default:
                sender.sendMessage("§cNot an option!");
                break;
        }

        return true;
    }
}
