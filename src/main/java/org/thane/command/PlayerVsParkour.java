package org.thane.command;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.thane.ThaneBukkit;

import javax.management.Attribute;
import java.util.ArrayList;
import java.util.List;

public class PlayerVsParkour {

    static List<Player> currentPlayers;

    public static boolean handleCommand(CommandSender sender, String[] args, Plugin plugin) {

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "You've given an incorrect number of arguments!");
            return false;
        }

        String action = args[0];
        if (action.equalsIgnoreCase("begin")) {
            World world = Bukkit.getWorld("pvp");
            if (world.getPlayers().size() < 5 && world.getPlayers().size() > 1) {
                Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                Team pvp = scoreboard.registerNewTeam("pvpplayers");
                pvp.setAllowFriendlyFire(true);
                pvp.setDisplayName("Players");
                pvp.setPrefix(String.valueOf(ChatColor.YELLOW));

                for (Player player : world.getPlayers()) {
                    pvp.addEntry(player.getName());
                    player.setGameMode(GameMode.ADVENTURE);
                    player.getInventory().clear();
                    player.sendMessage(ChatColor.YELLOW + "Beginning in:");
                    int timesDone = 0;
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    timesDone++;
                    if (timesDone == 1) {
                        Location location = new Location(world, 666, 141, 191, 0, 0);
                        player.teleport(location);
                        currentPlayers.add(player);
                    } else if (timesDone == 2) {
                        Location location = new Location(world, 685, 141, 210, 90, 0);
                        player.teleport(location);
                        currentPlayers.add(player);
                    } else if (timesDone == 3) {
                        Location location = new Location(world, 666, 141, 229, 180, 0);
                        player.teleport(location);
                        currentPlayers.add(player);
                    } else if (timesDone == 4) {
                        Location location = new Location(world, 647, 141, 210, -90, 0);
                        player.teleport(location);
                        currentPlayers.add(player);
                    } else if (timesDone > 4) {
                        Location location = new Location(world, 666, 177, 210, -90, 90);
                        player.teleport(location);
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 0, true));

                    ItemStack bow = new ItemStack(Material.BOW);
                    bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 100);
                    bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                    ItemMeta bowMeta = bow.getItemMeta();
                    bowMeta.setUnbreakable(true);
                    player.getInventory().setItemInOffHand(bow);

                    ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                    boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 9999);
                    ItemMeta bootMeta = boots.getItemMeta();
                    bootMeta.setUnbreakable(true);
                    player.getInventory().setBoots(boots);

                    ItemStack arrow = new ItemStack(Material.ARROW);
                    player.getInventory().setItem(9, arrow);

                    ItemStack stick = new ItemStack(Material.STICK);
                    stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 100);
                    player.getInventory().setItem(0, stick);
                }
                for (int second = 1; second <= 5; second++) {
                    countDown(world, second, plugin);
                }
                lavaLevel(plugin);
                barrierLevel(plugin);
                airLevel(plugin);

            } else {
                sender.sendMessage(ChatColor.RED + "You don't have enough players!");
            }

        } else if (action.equalsIgnoreCase("stop")) {
            endGame();
        } else {
            sender.sendMessage(ChatColor.RED + "This is not a parameter!");
        }
        return false;
    }

    @EventHandler
    public static void onPlayerDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (currentPlayers.contains(player) && world.getName().equalsIgnoreCase("pvp")) {
            Location location = new Location(event.getPlayer().getWorld(), 666, 177, 210, -90, 90);
            player.teleport(location);
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            if (currentPlayers.size() == 1) {
                endGame();
            }
        }

    }

    private static void countDown(World world, int second, Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (world.getPlayers().size() >= 4) {
                    for (Player player : world.getPlayers()) {
                        player.sendMessage(ChatColor.YELLOW + String.valueOf(second));
                    }
                } else {

                }
            }
        }.runTaskLater(plugin, second*20);
    }

    private static void lavaLevel(Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (currentPlayers.size() > 1) {

                 int lava = 133;
                 World world = Bukkit.getWorld("pvp");
                 Location loc1 = new Location(world, 688, lava, 188);
                 Location loc2 = new Location(world, 644, lava, 232);

                 for (int lavaY = 133; lavaY <= 172; lavaY++) {
                     for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                         for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                             if (world.getBlockAt(x, lavaY, z).getType().equals(Material.AIR)) {
                                 world.getBlockAt(x, lavaY, z).setType(Material.LAVA);
                             }
                         }
                     }
                 }


                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 100, 300);
    }

    private static void barrierLevel(Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getWorld("pvp").getBlockAt(688, 136, 188).getType().equals(Material.LAVA)) {
                    World world = Bukkit.getWorld("pvp");
                    Location loc1 = new Location(world, 688, 133, 188);
                    Location loc2 = new Location(world, 644, 133, 232);

                    for (int barrierY = 133; barrierY <= 168; barrierY++) {
                        for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                            for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                                if (world.getBlockAt(x, barrierY, z).getType().equals(Material.LAVA)) {
                                    world.getBlockAt(x, barrierY, z).setType(Material.BARRIER);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 133, 300);
    }

    private static void airLevel(Plugin plugin) {

        new BukkitRunnable() {
            @Override
            public void run() {
                int airY = 133;
                if (Bukkit.getWorld("pvp").getBlockAt(688, 134, 188).getType().equals(Material.BARRIER)) {
                    World world = Bukkit.getWorld("pvp");
                    Location loc1 = new Location(world, 688, 133, 188);
                    Location loc2 = new Location(world, 644, 133, 232);

                    if (airY <= 167) {
                        for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                            for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                                if (world.getBlockAt(x, airY, z).getType().equals(Material.BARRIER)) {
                                    world.getBlockAt(x, airY, z).setType(Material.AIR);
                                }
                            }
                        }
                    }
                    airY++;
                }
            }
        }.runTaskTimer(plugin, 166, 300);
    }

    private static void endGame() {
        String victoryMessage;
        if (currentPlayers.size() == 1) {
            Player player = currentPlayers.get(0);
            victoryMessage = ChatColor.YELLOW + "The winner is " + ChatColor.BOLD + player.getName() + ChatColor.RESET + ChatColor.YELLOW + "!";
        } else {
            victoryMessage = ChatColor.YELLOW + "There is no winner!";
        }
        currentPlayers.clear();
        World world = Bukkit.getWorld("pvp");
        Location spawn = new Location(world, 200, 3, 199, -90, 0);
        for (Player player : world.getPlayers()) {
            player.teleport(spawn);
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            player.sendMessage(victoryMessage);
        }
    }
}
