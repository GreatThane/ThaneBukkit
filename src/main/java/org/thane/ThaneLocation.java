package org.thane;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class ThaneLocation {

    public static final World WORLD = Bukkit.getWorld("world");
    public static final Location MAIN_LOBBY = new Location(WORLD, 172, 133, 197, 90, 0);

    public static void teleport(Player player, Location location) {
        Bukkit.getLogger().info("Teleporting " + player.getName() + " to " + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
        player.teleport(location);
    }

}
