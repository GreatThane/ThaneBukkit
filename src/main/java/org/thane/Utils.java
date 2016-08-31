package org.thane;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by GreatThane on 8/24/16.
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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getValue() ).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
