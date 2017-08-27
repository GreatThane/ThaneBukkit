package org.thane.command;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Button;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.thane.ThaneBukkit;
import org.thane.ThaneLocation;
import org.thane.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class PlayerVsParkour implements Listener {

    private static List<Player> currentPlayers = new ArrayList<>();
    private static int yLevel = 133;
    private static boolean gameRunning = true;
    private static final World WORLD = Bukkit.getWorld("pvp");
    private static final Location LOBBY = new Location(WORLD, 200, 3, 199, -90, 0);
    private static final Location FLAG = new Location(WORLD, 666, 177, 210, -90, 90);

    public boolean handleCommand(CommandSender sender, String[] args, Plugin plugin) {

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "You've given an incorrect number of arguments!");
            return false;
        }

        String action = args[0];
        Player author = plugin.getServer().getPlayer(args[1]);

        if (action.equalsIgnoreCase("start")) {
            yLevel = 133;
            gameRunning = true;
            if (WORLD.getPlayers().size() > 1) {
                WORLD.getBlockAt(207, 4, 199).setType(Material.AIR);
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
                for (Player player : WORLD.getPlayers()) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.getInventory().clear();
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    player.sendMessage(ChatColor.YELLOW + "Beginning in:");

                    // Figure out which player goes to which spawnpoint
                    timesDone++;
                    if (timesDone == 1) {
                        Location location = new Location(WORLD, 666, 141, 191, 0, 0);
                        ThaneLocation.teleport(player, location);
                        currentPlayers.add(player);
                    } else if (timesDone == 2) {
                        Location location = new Location(WORLD, 685, 141, 210, 90, 0);
                        ThaneLocation.teleport(player, location);
                        currentPlayers.add(player);
                    } else if (timesDone == 3) {
                        Location location = new Location(WORLD, 666, 141, 229, 180, 0);
                        ThaneLocation.teleport(player, location);
                        currentPlayers.add(player);
                    } else if (timesDone == 4) {
                        Location location = new Location(WORLD, 647, 141, 210, -90, 0);
                        ThaneLocation.teleport(player, location);
                        currentPlayers.add(player);
                    } else if (timesDone > 4) {
                        ThaneLocation.teleport(player, FLAG);
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                    // Set up player inventory and effects

                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100000, 0, false, false));
                    player.getInventory().setItemInOffHand(bow);
                    player.getInventory().setBoots(boots);
                    player.getInventory().setItem(9, arrow);
                    player.getInventory().setItem(0, stick);
                }
                for (int second = 1; second <= 6; second++) {
                    countDown(WORLD, second, plugin);
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
            return true;
        } else {
            author.sendMessage(ChatColor.RED + "This is not a parameter!");
            return false;
        }
        return true;
    }
    // Player death detection

    @EventHandler
    public static void onEntityDamageEvent(EntityDamageEvent event) {

        if (gameRunning &&
                event.getEntity().getWorld().equals(WORLD) &&
                event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            if (currentPlayers.contains(player)) {
                double health = player.getHealth();
                if (health - event.getFinalDamage() < 1) {
                    //You're gonna die
                    event.setCancelled(true);
                    ItemStack itemStack = new ItemStack(Material.COMPASS);
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Game Selector");
                    itemStack.setItemMeta(itemMeta);

                    currentPlayers.remove(player);
                    if (currentPlayers.size() == 1) {
                        player.setHealth(20);
                        Bukkit.getLogger().info("Last Player Teleported!");
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                endGame();
                            }
                        }.runTaskLater(ThaneBukkit.plugin(), 5);

                    } else if (currentPlayers.size() > 1) {
                        player.setHealth(20);
                        player.getInventory().clear();
                        player.getInventory().setItem(0, itemStack);
                        player.setGameMode(GameMode.SPECTATOR);
                        ThaneLocation.teleport(player, FLAG);
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public static void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (currentPlayers.contains(player)) {
            currentPlayers.remove(player);
            if (currentPlayers.size() == 1) {
                endGame();
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
        }.runTaskLater(plugin, second * 20);
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
                                    world.getBlockAt(x, yLevel - 3, z).setType(Material.NETHERRACK);
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
                                if (world.getBlockAt(x, yLevel - 4, z).getType().equals(Material.NETHERRACK)) {
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

        Location location;
        // Recreate doors
        location = new Location(WORLD, 666, 141, 193);
        setDoor(Direction.NORTH, location);
        location = new Location(WORLD, 683, 141, 210);
        setDoor(Direction.EAST, location);
        location = new Location(WORLD, 666, 141, 227);
        setDoor(Direction.SOUTH, location);
        location = new Location(WORLD, 649, 141, 210);
        setDoor(Direction.WEST, location);

        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Game Selector");
        itemStack.setItemMeta(itemMeta);

        int delay = 1;

        for (Player player : WORLD.getPlayers()) {
            new BukkitRunnable() {
                @Override
                public void run() {

                    ThaneLocation.teleport(player, LOBBY);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.getInventory().clear();
                            player.setGameMode(GameMode.ADVENTURE);
                            player.setFireTicks(0);
                            player.setHealth(20);
                            player.getInventory().setItem(0, itemStack);
                            player.sendMessage(victoryMessage);
                            for (PotionEffect effect : player.getActivePotionEffects()) {
                                player.removePotionEffect(effect.getType());
                            }
                        }
                    }.runTaskLater(ThaneBukkit.plugin(), 5);
                }
            }.runTaskLater(ThaneBukkit.plugin(), delay);

            delay += 2;
        }

        new BukkitRunnable() {
            @Override
            public void run () {
                // Find and delete all remaining lava and barriers left over in the map
                Location loc1 = new Location(WORLD, 688, yLevel, 188);
                Location loc2 = new Location(WORLD, 644, yLevel - 4, 232);
                int delay = 1;
                for (int y = loc1.getBlockY(); y <= loc2.getBlockY(); y--) {
                    for (int x = loc2.getBlockX(); x <= loc1.getBlockX(); x++) {
                        clearLava(x, y, loc1, loc2, delay);
                        delay++;
                    }
                }
            }
        }.runTaskLater(ThaneBukkit.plugin(), 20);
    }

    private static void clearLava(int x, int y, Location loc1, Location loc2, int delay) {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (int z = loc1.getBlockZ(); z <= loc2.getBlockZ(); z++) {
                    if (WORLD.getBlockAt(x, y, z).getType().equals(Material.NETHERRACK)
                            || WORLD.getBlockAt(x, y, z).getType().equals(Material.STATIONARY_LAVA)
                            || WORLD.getBlockAt(x, y, z).getType().equals(Material.LAVA)) {
                        WORLD.getBlockAt(x, y, z).setType(Material.AIR);
                    }
                }
                if(x == loc1.getBlockX() && y == loc1.getBlockY()) {
                    WORLD.getBlockAt(207, 4, 199).setType(Material.STONE_BUTTON);
                    Location location = new Location(WORLD, 207, 4, 199);
                    createButton(location, BlockFace.WEST);

                    gameRunning = false;
                }
            }
        }.runTaskLater(ThaneBukkit.plugin(), delay);
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
                loc = new Location(world, x + 1, y + timesDone, z - 1);
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
                loc = new Location(world, x + 1, y + timesDone, z + 1);
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
                loc = new Location(world, x + 1, y + timesDone, z + 1);
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
        org.bukkit.material.Sign matSign = new org.bukkit.material.Sign(Material.WALL_SIGN);
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