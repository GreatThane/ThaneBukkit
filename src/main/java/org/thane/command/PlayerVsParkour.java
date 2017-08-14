package org.thane.command;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Button;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.thane.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class PlayerVsParkour implements Listener {

    private static List<Player> currentPlayers = new ArrayList<>();
    private static int yLevel = 133;

    private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private static Team pvp = scoreboard.registerNewTeam("pvpplayers");

    public boolean handleCommand(CommandSender sender, String[] args, Plugin plugin) {

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "You've given an incorrect number of arguments!");
            return false;
        }

        String action = args[0];
        Player author = plugin.getServer().getPlayer(args[1]);

        if (action.equalsIgnoreCase("start")) {
            World world = Bukkit.getWorld("pvp");
            if (world.getPlayers().size() > 1) {
                world.getBlockAt(207, 4, 199).setType(Material.AIR);
                pvp.setAllowFriendlyFire(true);
                pvp.setDisplayName("Players");
                pvp.setPrefix(ChatColor.YELLOW + "");
                int timesDone = 0;

                // ItemStacks to be applied to players later, instead of being recreated repeatedly in for loop
                ItemStack bow = new ItemStack(Material.BOW);
                bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 100);
                bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
                ItemMeta bowMeta = bow.getItemMeta();
                bowMeta.setUnbreakable(true);
                bowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                bow.setItemMeta(bowMeta);

                ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                boots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 9999);
                ItemMeta bootMeta = boots.getItemMeta();
                bootMeta.setUnbreakable(true);
                bootMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                boots.setItemMeta(bootMeta);

                ItemStack arrow = new ItemStack(Material.ARROW);

                ItemStack stick = new ItemStack(Material.STICK);
                stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 100);
                ItemMeta stickMeta = stick.getItemMeta();
                stickMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

                // Prevent player advantages by clearing inventories and effects
                for (Player player : world.getPlayers()) {
                    pvp.addEntry(player.getName());
                    player.setGameMode(GameMode.ADVENTURE);
                    player.getInventory().clear();
                    player.getActivePotionEffects().clear();
                    player.sendMessage(ChatColor.YELLOW + "Beginning in:");

                    // Figure out which player goes to which spawnpoint
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
                    // Set up player inventory and effects

                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 0, true));
                    player.getInventory().setItemInOffHand(bow);
                    player.getInventory().setBoots(boots);
                    player.getInventory().setItem(9, arrow);
                    player.getInventory().setItem(0, stick);
                }
                for (int second = 1; second <= 6; second++) {
                    countDown(world, second, plugin);
                }
                // Begin rising lava effect

                lavaLevel(plugin);
                barrierLevel(plugin);
                airLevel(plugin);

            } else {
                author.sendMessage(ChatColor.RED + "You don't have enough players!");
            }

        } else if (action.equalsIgnoreCase("stop")) {
            endGame();
        } else {
            author.sendMessage(ChatColor.RED + "This is not a parameter!");
        }
        return false;
    }
    // Player death detection

    @EventHandler
    public static void onPlayerDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (currentPlayers.contains(player) && world.getName().equalsIgnoreCase("pvp")) {
            Location location = new Location(event.getPlayer().getWorld(), 666, 177, 210, -90, 90);
            player.teleport(location);
            player.setGameMode(GameMode.SPECTATOR);
            player.getInventory().clear();
            currentPlayers.remove(player);
            if (currentPlayers.size() == 1) {
                pvp.removeEntry(player.getName());
                Player winner = currentPlayers.get(0);
                endGame();
                player.sendMessage(ChatColor.YELLOW + "The winner is " + ChatColor.GOLD + ChatColor.BOLD + winner.getName() + ChatColor.RESET + ChatColor.YELLOW + "!");
                Location spawn = new Location(world, 200, 3, 199, -90, 0);
                player.getInventory().clear();
                player.getActivePotionEffects().clear();
                player.teleport(spawn);
                player.setGameMode(GameMode.ADVENTURE);
                player.setHealth(20);
            }
        }

    }
    // Countdown from 1 to 5 before beginning the game

    private static void countDown(World world, int second, Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (second > 0) {
                    for (Player player : world.getPlayers()) {
                        String message = "";
                        switch (second) {
                            case 1:
                                message = "5";
                                break;
                            case 2:
                                message = "4";
                                break;
                            case 3:
                                message = "3";
                                break;
                            case 4:
                                message = "2";
                                break;
                            case 5:
                                message = "1";
                                break;
                            case 6:
                                message = ChatColor.BOLD + "GO!";
                                // Open doors to allow players to begin the game
                                Location loc1 = new Location(world, 683, 141, 209);
                                Location loc2 = new Location(world, 684, 145, 211);
                                clearArea(loc1, loc2);
                                loc1 = new Location(world, 665, 141, 192);
                                loc2 = new Location(world, 667, 145, 193);
                                clearArea(loc1, loc2);
                                loc1 = new Location(world, 648, 141, 209);
                                loc2 = new Location(world, 649, 145, 211);
                                clearArea(loc1, loc2);
                                loc1 = new Location(world, 665, 141, 227);
                                loc2 = new Location(world, 667, 145, 228);
                                clearArea(loc1, loc2);
                                break;
                        }
                        player.sendMessage(ChatColor.YELLOW + message);
                    }
                }
            }
        }.runTaskLater(plugin, second*20);
    }
    // Actual rising lava

    private static void lavaLevel(Plugin plugin) {
        BukkitTask lavaLevel = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentPlayers.size() > 1) {

                 World world = Bukkit.getWorld("pvp");
                 Location loc1 = new Location(world, 688, yLevel, 188);
                 Location loc2 = new Location(world, 644, yLevel, 232);
                     for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                         for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                             if (world.getBlockAt(x, yLevel, z).getType().equals(Material.AIR)) {
                                 world.getBlockAt(x, yLevel, z).setType(Material.STATIONARY_LAVA);
                             }
                         }
                     }
                 if (yLevel == 173) {
                     cancel();
                 }
                 yLevel++;
                } else {
                   cancel();
                }
            }
        }.runTaskTimer(plugin, 100, 200);
    }
    // Barriers that follow up lava to prevent lag on endGame

    private static void barrierLevel(Plugin plugin) {
        BukkitTask barrierLevel = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentPlayers.size() > 1) {
                    if (yLevel >= 136) {

                        World world = Bukkit.getWorld("pvp");
                        Location loc1 = new Location(world, 688, yLevel - 3, 188);
                        Location loc2 = new Location(world, 644, yLevel - 3, 232);
                        for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                            for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                                if (world.getBlockAt(x, yLevel - 3, z).getType().equals(Material.STATIONARY_LAVA)
                                        || world.getBlockAt(x, yLevel - 3, z).getType().equals(Material.LAVA)) {
                                    world.getBlockAt(x, yLevel - 3, z).setType(Material.BARRIER);
                                }
                            }
                        }
                        if (yLevel == 172) {
                            cancel();
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 133, 200);
    }
    // Air created over top old barriers in order to delete them

    private static void airLevel(Plugin plugin) {

        BukkitTask airLevel = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentPlayers.size() > 1) {
                    if (yLevel >= 137) {

                        World world = Bukkit.getWorld("pvp");
                        Location loc1 = new Location(world, 688, yLevel - 4, 188);
                        Location loc2 = new Location(world, 644, yLevel - 4, 232);
                        for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                            for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                                if (world.getBlockAt(x, yLevel - 4, z).getType().equals(Material.BARRIER)) {
                                    world.getBlockAt(x, yLevel - 4, z).setType(Material.AIR);
                                }
                            }
                        }
                        if (yLevel == 172) {
                            cancel();
                        }
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 166, 200);
    }
    // Game ending sequence

    private static void endGame() {
        String victoryMessage;
        if (currentPlayers.size() == 1) {
            Player player = currentPlayers.get(0);
            victoryMessage = ChatColor.YELLOW + "The winner is " + ChatColor.GOLD + ChatColor.BOLD + player.getName() + ChatColor.RESET + ChatColor.YELLOW + "!";
        } else {
            victoryMessage = ChatColor.YELLOW + "There is no winner!";
        }
        currentPlayers.clear();
        World world = Bukkit.getWorld("pvp");
        Location spawn = new Location(world, 200, 3, 199, -90, 0);
        world.getBlockAt(207, 4, 199).setType(Material.STONE_BUTTON);
        Location location = new Location(world, 207, 4, 199);
        createButton(location, BlockFace.WEST);

        // Find and delete all remaining lava and barriers left over in the map
        Location loc1 = new Location(world, 688, 173, 188);
        Location loc2 = new Location(world, 644, 131, 232);
        for (int y = loc2.getBlockY(); y <= loc1.getBlockY(); y++) {
            for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                    if (world.getBlockAt(x, y, z).getType().equals(Material.BARRIER)
                            || world.getBlockAt(x, y, z).getType().equals(Material.STATIONARY_LAVA)
                            || world.getBlockAt(x, y, z).getType().equals(Material.LAVA)) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
        // Recreate doors
        location = new Location(world, 666, 141, 193);
        setDoor(Direction.NORTH, location);
        location = new Location(world, 683, 141, 210);
        setDoor(Direction.EAST, location);
        location = new Location(world, 666, 141, 227);
        setDoor(Direction.SOUTH, location);
        location = new Location(world, 649, 141, 210);
        setDoor(Direction.WEST, location);

        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Game Selector");
        itemStack.setItemMeta(itemMeta);

        for (Player player : world.getPlayers()) {
            player.getInventory().clear();

            player.teleport(spawn);
            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(20);
            pvp.removeEntry(player.getName());
            player.getActivePotionEffects().clear();
            player.getInventory().setItem(0, itemStack);
            player.sendMessage(victoryMessage);
        }
        pvp.unregister();
    }
    // Method used to clear door areas

    private static void clearArea(Location loc1, Location loc2) {
        World world = loc1.getWorld();
        for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y++) {
            for (int x = loc1.getBlockX(); x <= loc2.getBlockX(); x++) {
                for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                        world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }
    }
    // Method used to recreated said doors

    private static void setDoor(Direction direction, Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        World world = location.getWorld();
        Location loc;

        for (int timesDone = 0; timesDone <= 4; timesDone++) {
            loc = new Location(world, x, y + timesDone, z);
            loc.getBlock().setType(Material.LOG);
        }
        if (direction.equals(Direction.NORTH)) {
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x - 1, y + timesDone, z);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x + 1, y + timesDone, z);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            loc = new Location(world, x + 1, y, z - 1);
            createSign(loc, BlockFace.NORTH);
            loc = new Location(world, x - 1, y, z - 1);
            createSign(loc, BlockFace.NORTH);
            loc = new Location(world, x + 1, y + 4, z - 1);
            createSign(loc, BlockFace.NORTH);
            loc = new Location(world, x - 1, y + 4, z - 1);
            createSign(loc, BlockFace.NORTH);

            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location(world, x - 1, y + timesDone, z - 1);
                createButton(loc, BlockFace.NORTH);
            }
            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location( world, x + 1, y + timesDone, z - 1);
                createButton(loc, BlockFace.NORTH);
            }

        } else if (direction.equals(Direction.EAST)) {
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x, y + timesDone, z - 1);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x, y + timesDone, z + 1);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            loc = new Location(world, x + 1, y, z + 1);
            createSign(loc, BlockFace.EAST);
            loc = new Location(world, x + 1, y, z - 1);
            createSign(loc, BlockFace.EAST);
            loc = new Location(world, x + 1, y + 4, z + 1);
            createSign(loc, BlockFace.EAST);
            loc = new Location(world, x + 1, y + 4, z - 1);
            createSign(loc, BlockFace.EAST);

            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location(world, x + 1, y + timesDone, z - 1);
                createButton(loc, BlockFace.EAST);
            }
            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location( world, x + 1, y + timesDone, z + 1);
                createButton(loc, BlockFace.EAST);
            }

        } else if (direction.equals(Direction.SOUTH)) {
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x - 1, y + timesDone, z);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x + 1, y + timesDone, z);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            loc = new Location(world, x + 1, y, z + 1);
            createSign(loc, BlockFace.SOUTH);
            loc = new Location(world, x - 1, y, z + 1);
            createSign(loc, BlockFace.SOUTH);
            loc = new Location(world, x + 1, y + 4, z + 1);
            createSign(loc, BlockFace.SOUTH);
            loc = new Location(world, x - 1, y + 4, z + 1);
            createSign(loc, BlockFace.SOUTH);

            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location(world, x - 1, y + timesDone, z + 1);
                createButton(loc, BlockFace.SOUTH);
            }
            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location( world, x + 1, y + timesDone, z + 1);
                createButton(loc, BlockFace.SOUTH);
            }

        } else if (direction.equals(Direction.WEST)) {
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x, y + timesDone, z - 1);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            for (int timesDone = 0; timesDone <= 4; timesDone++) {
                loc = new Location(world, x, y + timesDone, z + 1);
                loc.getBlock().setType(Material.WOOD);
                loc.getBlock().setData((byte) 1);
            }
            loc = new Location(world, x - 1, y, z + 1);
            createSign(loc, BlockFace.WEST);
            loc = new Location(world, x - 1, y, z - 1);
            createSign(loc, BlockFace.WEST);
            loc = new Location(world, x - 1, y + 4, z + 1);
            createSign(loc, BlockFace.WEST);
            loc = new Location(world, x - 1, y + 4, z - 1);
            createSign(loc, BlockFace.WEST);

            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location(world, x - 1, y + timesDone, z - 1);
                createButton(loc, BlockFace.WEST);
            }
            for (int timesDone = 1; timesDone <= 3; timesDone++) {
                loc = new Location(world, x - 1, y + timesDone, z + 1);
                createButton(loc, BlockFace.WEST);
            }
        }
    }
    // Method to create a blank sign in a desired location

    private static void createSign(Location location, BlockFace blockFace) {
        location.getBlock().setType(Material.WALL_SIGN);
        Sign sign = (Sign) location.getBlock().getState();
        sign.setLine(1, " ");
        sign.setLine(2, " ");
        sign.setLine(3, " ");
        sign.setLine(0, " ");
        org.bukkit.material.Sign matSign =  new org.bukkit.material.Sign(Material.WALL_SIGN);
        matSign.setFacingDirection(blockFace);
        sign.setData(matSign);
        sign.update();
    }
    // Method to create a stone button in a desired location

    private static void createButton(Location location, BlockFace blockFace) {
        Block block = location.getBlock();
        BlockState blockState = block.getState();
        blockState.setType(Material.STONE_BUTTON);
        Button button = (Button) blockState.getData();
        button.setFacingDirection(blockFace);
        blockState.setData(button);
        blockState.update(true);
    }
}