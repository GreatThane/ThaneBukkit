package org.thane;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import java.io.File;
import java.util.*;

/**
 * Created by GreatThane on 8/24/16.
 */
public class Utils {

    private static String CURRENCY = "org.thane.currency";

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

    public static int getCurrency(Player player) {

        if(player.hasMetadata(CURRENCY)) {
            List<MetadataValue> values = player.getMetadata(CURRENCY);
            if (values.size() > 0) {
                return values.get(0).asInt();
            }
        }
        return 0;
    }

    public static void setCurrency(Player player, int currency) {

        MetadataValue value = new FixedMetadataValue(ThaneBukkit.plugin(), currency);
        player.setMetadata(CURRENCY, value);
    }

    public static void incrementCurrency(Player player) {

        int currency = getCurrency(player);
        setCurrency(player, currency++);
    }

    public static void clearCurrency(Player player) {

        if (player.hasMetadata(CURRENCY)) {
            player.removeMetadata(CURRENCY, ThaneBukkit.plugin());
        }
    }

    public static void reloadPlugin(CommandSender sender) {
        String name = "";
        String pluginName = ThaneBukkit.plugin().getName().toLowerCase();
        Bukkit.getServer().getLogger().info("plugin name=" + pluginName);
        Bukkit.getPluginManager().disablePlugin(ThaneBukkit.plugin());
        File[] listOfFiles = new File(ThaneBukkit.plugin().getDataFolder().getParent()).listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".jar")) {
                try {
                    name = ThaneBukkit.plugin().getPluginLoader().getPluginDescription(file).getName();
                    Bukkit.getServer().getLogger().info(name);
                } catch (InvalidDescriptionException e) {
                    sender.sendMessage(ChatColor.RED + file.getName() + " has an incorrect description");
                    return;
                }
                if(name.toLowerCase().startsWith(pluginName)) {
                    try {
                        Utils.log("Found it!  Trying to reload");
                        Bukkit.getServer().getPluginManager().loadPlugin(file);
                    } catch (UnknownDependencyException e) {
                        sender.sendMessage(ChatColor.RED + file.getName() + "is missing a dependant plugin");
                        return;
                    } catch (InvalidPluginException e) {
                        sender.sendMessage(ChatColor.RED + file.getName() + "is not a plugin");
                        return;
                    } catch (InvalidDescriptionException e) {
                        sender.sendMessage(ChatColor.RED + file.getName() + " has an incorrect description");
                        return;
                    } catch (Exception e) {
                        sender.sendMessage("ERROR" + e.getMessage());
                    }
                }
            }
        }
    }

    public static void log(String message) {
        Bukkit.getLogger().info(message);
    }
}
