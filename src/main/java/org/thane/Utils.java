package org.thane;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by jprice on 8/24/16.
 */
public class Utils {

    public static void sendTitle(CommandSender sender, Player player, String title) {

        sendTitle(sender, player, title, null);
    }

    public static void sendTitle(CommandSender sender, Player player, String title, String subTitle) {

        if (subTitle != null) {
            player.getServer().dispatchCommand(sender, "title " + player.getName() + " subtitle {\"text\":\"" + subTitle + "\"}");
        }
        player.getServer().dispatchCommand(sender, "title " + player.getName() + " title {\"text\":\"" + title + "\"}");
    }

    public static Player getOnlinePlayer(String userName) {

        for (Player player: Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(userName)) {
                return player;
            }
        }
        return null;
    }

    public static String formatTime (int seconds) {

        int minutes = seconds / 60;
        int remainderSeconds = seconds - (minutes * 60);
        return String.format("%1d:%2$02d", minutes, remainderSeconds);
    }
}
