package org.thane.command;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.thane.ThaneBukkit;
import org.thane.ThaneTag;
import org.thane.enums.ArenaClass;
import org.thane.enums.ArenaMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Arena implements Listener {

    public static HashMap<ArenaClass, String> availableClasses = new HashMap<>();

    private static boolean gameRunning = false;
    private static boolean ffa = true;
    private static ArenaMap map = ArenaMap.CASTLE;

    public static boolean handleCommand(CommandSender sender, String[] args) {

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "You have given an incorrect number of parameters!");
            return false;
        }
        if (args[0].equalsIgnoreCase("class") && !gameRunning) {

        if (sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();

                switch (args[1]) {
                    case "brute":
                        setClass(ArenaClass.BRUTE, player);
                        break;
                    case "archer":
                        setClass(ArenaClass.ARCHER, player);
                        break;
                    case "miner":
                        setClass(ArenaClass.MINER, player);
                        break;
                    case "unicorn":
                        setClass(ArenaClass.UNICORN, player);
                        break;
                    case "dragon":
                        setClass(ArenaClass.DRAGON, player);
                        break;
                    case "scout":
                        setClass(ArenaClass.SCOUT, player);
                        break;
                    case "wither":
                        setClass(ArenaClass.WITHER, player);
                        break;
                    case "ninja":
                        setClass(ArenaClass.NINJA, player);
                        break;
                    case "snowman":
                        setClass(ArenaClass.SNOWMAN, player);
                        break;
                }
            }
        }
        if (args[0].equalsIgnoreCase("stop") && gameRunning) {
            availableClasses.clear();
        }
        if (args[0].equalsIgnoreCase("start") && !gameRunning) {
            startGame(ffa, map);
        }
        return true;
    }

    @EventHandler
    public static void onWorldJoin(PlayerChangedWorldEvent event) {
        if (event.getFrom().equals(Bukkit.getServer().getWorld("arena"))) {
            removePlayerClass(event.getPlayer());
        }

        if (event.getPlayer().getWorld().equals(Bukkit.getServer().getWorld("arena"))) {
            World world = Bukkit.getServer().getWorld("arena");
            if (world.getPlayers().size() == 1) {
                ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD);
                ItemMeta blazeRodMeta = blazeRod.getItemMeta();
                blazeRodMeta.setDisplayName(ChatColor.RED + "Game Settings");
                blazeRod.setItemMeta(blazeRodMeta);
                event.getPlayer().getInventory().setItem(4, blazeRod);
            }
            ItemStack netherstar = new ItemStack(Material.NETHER_STAR);
            event.getPlayer().getInventory().setItem(8, netherstar);
        }
    }

    @EventHandler
    public static void onPlayerQuit(PlayerQuitEvent event) {
        removePlayerClass(event.getPlayer());
    }

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) throws InterruptedException {
        if (gameRunning && event.getAction() != Action.PHYSICAL
                && event.getMaterial().equals(Material.QUARTZ)
                && availableClasses.get(ArenaClass.SNOWMAN).equals(event.getPlayer().getName())) {
            Location loc = event.getPlayer().getLocation();
            World world = event.getPlayer().getWorld();
            Block block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() - 1);
            Block block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() - 1, loc.getBlockZ() + 1);
            for (int x = block1.getX(); x <= block2.getX(); x++) {
                for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                    if (world.getBlockAt(x, loc.getBlockY() - 1, z).getType().equals(Material.AIR)) {
                        world.getBlockAt(x, loc.getBlockY() - 1, z).setType(Material.SNOW_BLOCK);
                    }
                }
            }
            block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY() + 3, loc.getBlockZ() - 1);
            block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() + 3, loc.getBlockZ() + 1);
            for (int x = block1.getX(); x <= block2.getX(); x++) {
                for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                    if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.AIR)) {
                        world.getBlockAt(x, loc.getBlockY(), z).setType(Material.SNOW_BLOCK);
                    }
                }
            }
            block1 = world.getBlockAt(loc.getBlockX() + 2, loc.getBlockY(), loc.getBlockZ() - 1);
            block2 = world.getBlockAt(loc.getBlockX() + 2, loc.getBlockY() + 2, loc.getBlockZ() + 1);
            for (int x = block1.getX(); x <= block2.getX(); x++) {
                for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                    if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.AIR)) {
                        world.getBlockAt(x, loc.getBlockY(), z).setType(Material.SNOW_BLOCK);
                    }
                }
            }
            block1 = world.getBlockAt(loc.getBlockX() - 2, loc.getBlockY(), loc.getBlockZ() - 1);
            block2 = world.getBlockAt(loc.getBlockX() - 2, loc.getBlockY() + 2, loc.getBlockZ() + 1);
            for (int x = block1.getX(); x <= block2.getX(); x++) {
                for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                    if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.AIR)) {
                        world.getBlockAt(x, loc.getBlockY(), z).setType(Material.SNOW_BLOCK);
                    }
                }
            }
            block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ() + 2);
            block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() + 2, loc.getBlockZ() + 2);
            for (int x = block1.getX(); x <= block2.getX(); x++) {
                for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                    if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.AIR)) {
                        world.getBlockAt(x, loc.getBlockY(), z).setType(Material.SNOW_BLOCK);
                    }
                }
            }
            block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ() - 2);
            block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() + 2, loc.getBlockZ() - 2);
            for (int x = block1.getX(); x <= block2.getX(); x++) {
                for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                    if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.AIR)) {
                        world.getBlockAt(x, loc.getBlockY(), z).setType(Material.SNOW_BLOCK);
                    }
                }
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                    Block block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY() - 1, loc.getBlockZ() - 1);
                    Block block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() - 1, loc.getBlockZ() + 1);
                    for (int x = block1.getX(); x <= block2.getX(); x++) {
                        for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                            if (world.getBlockAt(x, loc.getBlockY() - 1, z).getType().equals(Material.SNOW_BLOCK)) {
                                world.getBlockAt(x, loc.getBlockY() - 1, z).setType(Material.AIR);
                            }
                        }
                    }
                    block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY() + 3, loc.getBlockZ() - 1);
                    block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() + 3, loc.getBlockZ() + 1);
                    for (int x = block1.getX(); x <= block2.getX(); x++) {
                        for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                            if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.SNOW_BLOCK)) {
                                world.getBlockAt(x, loc.getBlockY(), z).setType(Material.AIR);
                            }
                        }
                    }
                    block1 = world.getBlockAt(loc.getBlockX() + 2, loc.getBlockY(), loc.getBlockZ() - 1);
                    block2 = world.getBlockAt(loc.getBlockX() + 2, loc.getBlockY() + 2, loc.getBlockZ() + 1);
                    for (int x = block1.getX(); x <= block2.getX(); x++) {
                        for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                            if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.SNOW_BLOCK)) {
                                world.getBlockAt(x, loc.getBlockY(), z).setType(Material.AIR);
                            }
                        }
                    }
                    block1 = world.getBlockAt(loc.getBlockX() - 2, loc.getBlockY(), loc.getBlockZ() - 1);
                    block2 = world.getBlockAt(loc.getBlockX() - 2, loc.getBlockY() + 2, loc.getBlockZ() + 1);
                    for (int x = block1.getX(); x <= block2.getX(); x++) {
                        for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                            if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.SNOW_BLOCK)) {
                                world.getBlockAt(x, loc.getBlockY(), z).setType(Material.AIR);
                            }
                        }
                    }
                    block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ() + 2);
                    block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() + 2, loc.getBlockZ() + 2);
                    for (int x = block1.getX(); x <= block2.getX(); x++) {
                        for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                            if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.SNOW_BLOCK)) {
                                world.getBlockAt(x, loc.getBlockY(), z).setType(Material.AIR);
                            }
                        }
                    }
                    block1 = world.getBlockAt(loc.getBlockX() - 1, loc.getBlockY(), loc.getBlockZ() - 2);
                    block2 = world.getBlockAt(loc.getBlockX() + 1, loc.getBlockY() + 2, loc.getBlockZ() - 2);
                    for (int x = block1.getX(); x <= block2.getX(); x++) {
                        for (int z = block1.getZ(); z <= block2.getZ(); z++) {
                            if (world.getBlockAt(x, loc.getBlockY(), z).getType().equals(Material.SNOW_BLOCK)) {
                                world.getBlockAt(x, loc.getBlockY(), z).setType(Material.AIR);
                            }
                        }
                    }

                }
            }.runTaskLater(ThaneBukkit.plugin(), 400);

            new BukkitRunnable() {
                @Override
                public void run() {

                    Player player = Bukkit.getPlayer(availableClasses.get(ArenaClass.SNOWMAN));
                    ItemStack snowfort = new ItemStack(Material.QUARTZ);
                    ItemMeta snowfortMeta = snowfort.getItemMeta();
                    snowfortMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Build Snow Fort");
                    snowfort.setItemMeta(snowfortMeta);
                }
            }.runTaskLater(ThaneBukkit.plugin(), 800);

        }
    }

    public static void setClass(ArenaClass inventory, Player player) {
        if (!availableClasses.containsKey(inventory)) {
            removePlayerClass(player);
            availableClasses.put(inventory, player.getName());
            switch (inventory) {

                case BRUTE:
                    ItemStack ferocity = new ItemStack(Material.DIAMOND_AXE);
                    ferocity.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 15);
                    ItemMeta ferocityMeta = ferocity.getItemMeta();
                    ferocityMeta.setDisplayName(ChatColor.AQUA + "Ferocity");
                    ferocityMeta.setUnbreakable(true);
                    ferocityMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    ferocity.setItemMeta(ferocityMeta);

                    ItemStack chestPlate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                    ItemMeta chestMeta = chestPlate.getItemMeta();
                    chestMeta.setUnbreakable(true);
                    chestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    chestPlate.setItemMeta(chestMeta);

                    ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                    ItemMeta leggingsMeta = leggings.getItemMeta();
                    leggingsMeta.setUnbreakable(true);
                    leggingsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    leggings.setItemMeta(leggingsMeta);

                    ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                    ItemMeta bootsMeta = boots.getItemMeta();
                    bootsMeta.setUnbreakable(true);
                    bootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    boots.setItemMeta(bootsMeta);

                    player.getInventory().setItem(0, ferocity);
                    setSkull(player, "bd1b1518-b00d-438f-a9a0-68898f0a520c");
                    player.getInventory().setChestplate(chestPlate);
                    player.getInventory().setLeggings(leggings);
                    player.getInventory().setBoots(boots);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1, false, false));

                    HumanEntity humanEntity = player;
                    AttributeModifier attributeModifier = new AttributeModifier("Knockback", 1, AttributeModifier.Operation.ADD_NUMBER);
                    humanEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).addModifier(attributeModifier);

                    break;
                case ARCHER:
                    ItemStack shooter = new ItemStack(Material.BOW);
                    ItemMeta shooterMeta = shooter.getItemMeta();
                    shooter.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 7);
                    shooterMeta.setUnbreakable(true);
                    shooterMeta.setDisplayName(ChatColor.RED + "Sharp Shooter");
                    shooterMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    shooter.setItemMeta(shooterMeta);

                    ItemStack dagger = new ItemStack(Material.WOOD_SWORD);
                    ItemMeta daggerMeta = dagger.getItemMeta();
                    dagger.addEnchantment(Enchantment.KNOCKBACK, 2);
                    daggerMeta.setUnbreakable(true);
                    daggerMeta.setDisplayName(ChatColor.GOLD + "Dagger");
                    daggerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    dagger.setItemMeta(daggerMeta);

                    ItemStack mailChestPlate = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                    ItemMeta mailChestMeta = mailChestPlate.getItemMeta();
                    mailChestPlate.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
                    mailChestMeta.setUnbreakable(true);
                    mailChestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    mailChestPlate.setItemMeta(mailChestMeta);

                    ItemStack mailLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    ItemMeta mailLegginsMeta = mailLeggings.getItemMeta();
                    mailLeggings.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
                    mailLegginsMeta.setUnbreakable(true);
                    mailLegginsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    mailLeggings.setItemMeta(mailLegginsMeta);

                    ItemStack mailBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
                    ItemMeta mailBootsMeta = mailBoots.getItemMeta();
                    mailBoots.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
                    mailBootsMeta.setUnbreakable(true);
                    mailBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    mailBoots.setItemMeta(mailBootsMeta);

                    ItemStack slowPotion = new ItemStack(Material.SPLASH_POTION, 3);
                    PotionMeta potionMeta = (PotionMeta) slowPotion.getItemMeta();
                    PotionData potionData = new PotionData(PotionType.SLOWNESS, false, true);
                    potionMeta.setBasePotionData(potionData);
                    potionMeta.clearCustomEffects();
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1), true);
                    potionMeta.setColor(Color.GRAY);
                    slowPotion.setItemMeta(potionMeta);

                    player.getInventory().setItem(0, dagger);
                    player.getInventory().setItemInOffHand(shooter);
                    player.getInventory().setItem(1, slowPotion);
                    setSkull(player, "b7b78de7-61fd-4472-a8ec-584dbed74bca");
                    player.getInventory().setChestplate(mailChestPlate);
                    player.getInventory().setLeggings(mailLeggings);
                    player.getInventory().setBoots(mailBoots);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, false, false));
                    break;
                case MINER:
                    List<String> destroyBlocks = new ArrayList<>();
                    destroyBlocks.add("minecraft:tnt");
                    List<String> placeBlocks = new ArrayList<>();
                    ItemStack pick = ThaneTag.attributeItemStack(Material.IRON_PICKAXE, destroyBlocks, placeBlocks);
                    ItemMeta pickMeta = pick.getItemMeta();
                    pick.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
                    pickMeta.setUnbreakable(true);
                    pickMeta.setDisplayName(ChatColor.DARK_GRAY + "Trusty Pick");
                    pickMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DESTROYS);
                    pick.setItemMeta(pickMeta);

                    List<String> destroyBlocks1 = new ArrayList<>();
                    destroyBlocks1.add("minecraft:tnt");
                    List<String> placeBlocks1 = new ArrayList<>();
                    ItemStack flintandsteel = ThaneTag.attributeItemStack(Material.FLINT_AND_STEEL, destroyBlocks1, placeBlocks1);
                    ItemMeta flintMeta = flintandsteel.getItemMeta();
                    flintMeta.setUnbreakable(true);
                    flintMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_DESTROYS);
                    flintandsteel.setItemMeta(flintMeta);

                    ItemStack goldChestPlate = new ItemStack(Material.GOLD_CHESTPLATE);
                    ItemMeta goldChestMeta = goldChestPlate.getItemMeta();
                    goldChestPlate.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 9999);
                    goldChestPlate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL,2);
                    goldChestMeta.setUnbreakable(true);
                    goldChestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    goldChestPlate.setItemMeta(goldChestMeta);

                    ItemStack goldLeggings = new ItemStack(Material.GOLD_LEGGINGS);
                    ItemMeta goldLeggingsMeta = goldLeggings.getItemMeta();
                    goldLeggings.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 9999);
                    goldLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    goldLeggingsMeta.setUnbreakable(true);
                    goldLeggingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    goldLeggings.setItemMeta(goldLeggingsMeta);

                    ItemStack goldBoots = new ItemStack(Material.GOLD_BOOTS);
                    ItemMeta goldBootsMeta = goldBoots.getItemMeta();
                    goldBoots.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 9999);
                    goldBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    goldBootsMeta.setUnbreakable(true);
                    goldBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    goldBoots.setItemMeta(goldBootsMeta);

                    List<String> breakBlocks = new ArrayList<>();
                    breakBlocks.add("minecraft:tnt");
                    List<String> useBlocks = new ArrayList<>();
                    useBlocks.add("minecraft:grass");
                    useBlocks.add("minecraft:log");
                    useBlocks.add("minecraft:planks");
                    useBlocks.add("minecraft:cobblestone");
                    useBlocks.add("minecraft:wooden_slab");
                    useBlocks.add("minecraft:wool");
                    useBlocks.add("minecraft:stained_hardened_clay");
                    useBlocks.add("minecraft:stonebrick");
                    useBlocks.add("minecraft:stone_slab");
                    useBlocks.add("minecraft:brick_block");
                    useBlocks.add("minecraft:fence");
                    useBlocks.add("minecraft:stone_brick_stairs");
                    useBlocks.add("minecraft:stone_stairs");
                    useBlocks.add("minecraft:glass");
                    useBlocks.add("minecraft:stained_glass");
                    useBlocks.add("minecraft:stained_glass_pane");
                    useBlocks.add("minecraft:glass_pane");
                    useBlocks.add("minecraft:coal_block");
                    useBlocks.add("minecraft:carpet");
                    useBlocks.add("miencraft:tnt");
                    ItemStack tnt = ThaneTag.attributeItemStack(Material.TNT, breakBlocks, useBlocks);
                    ItemMeta tntMeta = tnt.getItemMeta();
                    tntMeta.setDisplayName(ChatColor.GRAY + "Explosives");
                    tntMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS);
                    tnt.setItemMeta(tntMeta);

                    player.getInventory().setItem(0, pick);
                    player.getInventory().setItemInOffHand(flintandsteel);
                    player.getInventory().setItem(1, tnt);
                    setSkull(player, "47b8d116-ec07-4287-b153-b45b66ba4fbf");
                    player.getInventory().setChestplate(goldChestPlate);
                    player.getInventory().setLeggings(goldLeggings);
                    player.getInventory().setBoots(goldBoots);
                    break;
                case UNICORN:
                    ItemStack horn = new ItemStack(Material.STONE_SWORD);
                    ItemMeta hornMeta = horn.getItemMeta();
                    horn.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                    hornMeta.setDisplayName(ChatColor.RED + "U" + ChatColor.GOLD + "n"
                            + ChatColor.YELLOW + "i" + ChatColor.GREEN + "c" + ChatColor.DARK_GREEN
                            + "o" + ChatColor.DARK_AQUA + "r" + ChatColor.BLUE + "n" + ChatColor.DARK_BLUE
                            + "'s" + ChatColor.LIGHT_PURPLE + " H" + ChatColor.DARK_PURPLE + "o" + ChatColor.DARK_RED
                            + "r" + ChatColor.RED + "n");
                    hornMeta.setUnbreakable(true);
                    hornMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    horn.setItemMeta(hornMeta);

                    ItemStack shield = new ItemStack(Material.SHIELD);
                    ItemMeta shieldMeta = shield.getItemMeta();
                    shieldMeta.setUnbreakable(true);
                    shieldMeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "BRING ME DEATH");
                    shieldMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    shield.setItemMeta(shieldMeta);

                    ItemStack whiteChestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta whiteChestmeta = (LeatherArmorMeta) whiteChestPlate.getItemMeta();
                    whiteChestmeta.setColor(Color.WHITE);
                    whiteChestmeta.setUnbreakable(true);
                    whiteChestmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    whiteChestPlate.setItemMeta(whiteChestmeta);

                    ItemStack whiteLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta whiteLeggingsMeta = (LeatherArmorMeta) whiteLeggings.getItemMeta();
                    whiteLeggingsMeta.setColor(Color.WHITE);
                    whiteLeggingsMeta.setUnbreakable(true);
                    whiteLeggingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    whiteLeggings.setItemMeta(whiteLeggingsMeta);

                    ItemStack pinkBoots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta pinkBootsMeta = (LeatherArmorMeta) pinkBoots.getItemMeta();
                    pinkBootsMeta.setColor(Color.PURPLE);
                    pinkBootsMeta.setUnbreakable(true);
                    pinkBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    pinkBoots.setItemMeta(pinkBootsMeta);

                    ItemStack endRod = new ItemStack(Material.END_ROD);
                    ItemMeta endRodMeta = endRod.getItemMeta();
                    endRodMeta.setUnbreakable(true);
                    endRodMeta.setDisplayName(ChatColor.WHITE + "Horn");
                    endRodMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    endRod.setItemMeta(endRodMeta);

                    player.getInventory().setItemInOffHand(shield);
                    player.getInventory().setItem(0, horn);
                    player.getInventory().setHelmet(endRod);
                    player.getInventory().setChestplate(whiteChestPlate);
                    player.getInventory().setLeggings(whiteLeggings);
                    player.getInventory().setBoots(pinkBoots);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1, false, false));
                    break;
                case DRAGON:
                    ItemStack dragonTooth = new ItemStack(Material.IRON_SWORD);
                    ItemMeta dragonToothMeta = dragonTooth.getItemMeta();
                    dragonToothMeta.setDisplayName(ChatColor.WHITE + "Dragon Tooth");
                    dragonToothMeta.setUnbreakable(true);
                    dragonToothMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    dragonTooth.setItemMeta(dragonToothMeta);

                    ItemStack dragonBreath = new ItemStack(Material.BOW);
                    ItemMeta dragonBreathMeta = dragonBreath.getItemMeta();
                    dragonBreathMeta.setUnbreakable(true);
                    dragonBreathMeta.setDisplayName(ChatColor.RED + "Dragon Breath");
                    dragonBreathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    dragonBreath.setItemMeta(dragonBreathMeta);

                    ItemStack chainMailLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    ItemMeta chainMailLeggingsMeta = chainMailLeggings.getItemMeta();
                    chainMailLeggingsMeta.setUnbreakable(true);
                    chainMailLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    chainMailLeggingsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_UNBREAKABLE);
                    chainMailLeggings.setItemMeta(chainMailLeggingsMeta);

                    ItemStack chainMailBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
                    ItemMeta chainMailBootsMeta = chainMailBoots.getItemMeta();
                    chainMailBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    chainMailBoots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);
                    chainMailBootsMeta.setUnbreakable(true);
                    chainMailBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    chainMailBoots.setItemMeta(chainMailBootsMeta);

                    ItemStack elytra = new ItemStack(Material.ELYTRA);
                    ItemMeta elytraMeta = elytra.getItemMeta();
                    elytraMeta.setDisplayName(ChatColor.DARK_GRAY + "Wings");
                    elytraMeta.setUnbreakable(true);
                    elytraMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    elytra.setItemMeta(elytraMeta);

                    player.getInventory().setItem(0, dragonTooth);
                    player.getInventory().setItemInOffHand(dragonBreath);
                    setSkull(player, "db341464-6d45-4b60-ad8f-9759c78cde7d");
                    player.getInventory().setChestplate(elytra);
                    player.getInventory().setLeggings(chainMailLeggings);
                    player.getInventory().setBoots(chainMailBoots);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 15, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
                    break;
                case SCOUT:
                    ItemStack bat = new ItemStack(Material.STONE_SWORD);
                    ItemMeta batMeta = bat.getItemMeta();
                    batMeta.setDisplayName(ChatColor.YELLOW + "Baseball Bat");
                    bat.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                    bat.addEnchantment(Enchantment.KNOCKBACK, 2);
                    batMeta.setUnbreakable(true);
                    batMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    bat.setItemMeta(batMeta);

                    ItemStack scoutChest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
                    ItemMeta scoutChestMeta = scoutChest.getItemMeta();
                    scoutChest.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
                    scoutChestMeta.setUnbreakable(true);
                    scoutChestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    scoutChest.setItemMeta(scoutChestMeta);

                    ItemStack scoutLeggings = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                    ItemMeta scoutLeggingsMeta = scoutLeggings.getItemMeta();
                    scoutLeggings.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
                    scoutLeggingsMeta.setUnbreakable(true);
                    scoutLeggingsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    scoutLeggings.setItemMeta(scoutLeggingsMeta);

                    ItemStack scoutBoots = new ItemStack(Material.CHAINMAIL_BOOTS);
                    ItemMeta scoutBootsMeta = scoutBoots.getItemMeta();
                    scoutBoots.addEnchantment(Enchantment.PROTECTION_FIRE, 3);
                    scoutBootsMeta.setUnbreakable(true);
                    scoutBootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    scoutBoots.setItemMeta(scoutBootsMeta);

                    player.getInventory().setItem(0, bat);
                    setSkull(player, "42870cc4-c5ca-4cb5-91b0-97c0d09faa96");
                    player.getInventory().setChestplate(scoutChest);
                    player.getInventory().setLeggings(scoutLeggings);
                    player.getInventory().setBoots(scoutBoots);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, false, false));
                    break;
                case WITHER:
                    ItemStack purifier = new ItemStack(Material.STONE_SWORD);
                    ItemMeta purifierMeta = purifier.getItemMeta();
                    purifierMeta.setDisplayName(ChatColor.GRAY + "Purifier");
                    purifier.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 4);
                    purifierMeta.setUnbreakable(true);
                    purifierMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    purifier.setItemMeta(purifierMeta);

                    ItemStack curser = new ItemStack(Material.BOW);
                    ItemMeta curserMeta = curser.getItemMeta();
                    curserMeta.setDisplayName(ChatColor.RED + "Curser");
                    curserMeta.setUnbreakable(true);
                    curserMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    curser.setItemMeta(curserMeta);

                    ItemStack nauseaArrow = new ItemStack(Material.TIPPED_ARROW, 64);
                    PotionMeta nauseaArrowMeta = (PotionMeta) nauseaArrow.getItemMeta();
                    nauseaArrowMeta.setBasePotionData(new PotionData(PotionType.AWKWARD));
                    nauseaArrowMeta.clearCustomEffects();
                    nauseaArrowMeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1), true);
                    nauseaArrowMeta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1), true);
                    nauseaArrowMeta.setDisplayName(ChatColor.DARK_GRAY + "Hexes");
                    nauseaArrowMeta.setColor(Color.BLACK);
                    nauseaArrow.setItemMeta(nauseaArrowMeta);

                    ItemStack witherPotion = new ItemStack(Material.SPLASH_POTION, 5);
                    PotionMeta witherPotionMeta = (PotionMeta) witherPotion.getItemMeta();
                    witherPotionMeta.setBasePotionData(new PotionData(PotionType.AWKWARD));
                    witherPotionMeta.clearCustomEffects();
                    witherPotionMeta.setColor(Color.BLACK);
                    witherPotionMeta.setDisplayName(ChatColor.BLACK + "Tainted Water");
                    witherPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 80, 1), true);
                    witherPotion.setItemMeta(witherPotionMeta);

                    ItemStack blackChest = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta blackChestMeta = (LeatherArmorMeta) blackChest.getItemMeta();
                    blackChest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    blackChestMeta.setColor(Color.BLACK);
                    blackChestMeta.setUnbreakable(true);
                    blackChest.setItemMeta(blackChestMeta);

                    ItemStack blackLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta blackLeggingsMeta = (LeatherArmorMeta) blackLeggings.getItemMeta();
                    blackLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    blackLeggingsMeta.setColor(Color.BLACK);
                    blackLeggingsMeta.setUnbreakable(true);
                    blackLeggings.setItemMeta(blackLeggingsMeta);

                    ItemStack blackBoots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta blackBootsMeta = (LeatherArmorMeta) blackBoots.getItemMeta();
                    blackBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    blackBootsMeta.setColor(Color.BLACK);
                    blackBootsMeta.setUnbreakable(true);
                    blackBoots.setItemMeta(blackBootsMeta);

                    player.getInventory().setItemInOffHand(curser);
                    player.getInventory().setItem(0, purifier);
                    player.getInventory().setItem(1, witherPotion);
                    player.getInventory().setItem(9, nauseaArrow);
                    setSkull(player, "119c371b-ea16-47c9-ad7f-23b3d894520a");
                    player.getInventory().setChestplate(blackChest);
                    player.getInventory().setLeggings(blackLeggings);
                    player.getInventory().setBoots(blackBoots);
                    break;
                case NINJA:
                    ItemStack katana = new ItemStack(Material.STONE_SWORD);
                    ItemMeta katanaMeta = katana.getItemMeta();
                    katana.addEnchantment(Enchantment.DAMAGE_ALL, 2);
                    katanaMeta.setUnbreakable(true);
                    katanaMeta.setDisplayName(ChatColor.GRAY + "Katana");
                    katanaMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    katana.setItemMeta(katanaMeta);

                    ItemStack marker = new ItemStack(Material.BOW);
                    ItemMeta markerMeta = marker.getItemMeta();
                    markerMeta.setDisplayName(ChatColor.YELLOW + "Marker");
                    markerMeta.setUnbreakable(true);
                    markerMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    marker.setItemMeta(markerMeta);

                    ItemStack spectralArrow = new ItemStack(Material.SPECTRAL_ARROW,64);
                    ItemMeta spectralArrowMeta = spectralArrow.getItemMeta();
                    spectralArrowMeta.setDisplayName(ChatColor.WHITE + "Markers");
                    spectralArrow.setItemMeta(spectralArrowMeta);

                    ItemStack blackLeatherChestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta blackLeatherChestPlateMeta = (LeatherArmorMeta) blackLeatherChestPlate.getItemMeta();
                    blackLeatherChestPlateMeta.setColor(Color.BLACK);
                    blackLeatherChestPlateMeta.setUnbreakable(true);
                    blackLeatherChestPlate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    blackLeatherChestPlateMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    blackLeatherChestPlate.setItemMeta(blackLeatherChestPlateMeta);

                    ItemStack blackLeatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta blackLeatherLeggingsMeta = (LeatherArmorMeta) blackLeatherLeggings.getItemMeta();
                    blackLeatherLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    blackLeatherLeggingsMeta.setColor(Color.BLACK);
                    blackLeatherLeggingsMeta.setUnbreakable(true);
                    blackLeatherLeggingsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    blackLeatherLeggings.setItemMeta(blackLeatherLeggingsMeta);

                    ItemStack blackLeatherBoots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta blackLeatherBootsMeta = (LeatherArmorMeta) blackLeatherBoots.getItemMeta();
                    blackLeatherBootsMeta.setColor(Color.BLACK);
                    blackLeatherBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    blackLeatherBootsMeta.setUnbreakable(true);
                    blackLeatherBootsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    blackLeatherBoots.setItemMeta(blackLeatherBootsMeta);

                    player.getInventory().setItem(0, katana);
                    player.getInventory().setItemInOffHand(marker);
                    player.getInventory().setItem(9, spectralArrow);
                    setSkull(player, "c8fac29a-5874-4cdc-8a52-537438e0fbf7");
                    player.getInventory().setChestplate(blackLeatherChestPlate);
                    player.getInventory().setLeggings(blackLeatherLeggings);
                    player.getInventory().setBoots(blackLeatherBoots);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1, false, false));
                    break;
                case SNOWMAN:
                    ItemStack snowmanArm = new ItemStack(Material.STONE_SWORD);
                    ItemMeta snowmanArmMeta = snowmanArm.getItemMeta();
                    snowmanArm.addEnchantment(Enchantment.KNOCKBACK, 3);
                    snowmanArmMeta.setUnbreakable(true);
                    snowmanArmMeta.setDisplayName(ChatColor.GOLD + "Snowman Arm");
                    snowmanArmMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    snowmanArm.setItemMeta(snowmanArmMeta);

                    ItemStack snowfort = new ItemStack(Material.QUARTZ);
                    ItemMeta snowfortMeta = snowfort.getItemMeta();
                    snowfortMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Build Snow Fort");
                    snowfort.setItemMeta(snowfortMeta);

                    ItemStack snowball = new ItemStack(Material.SNOW_BALL, 64);

                    ItemStack whitechest = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta whitechestmeta = (LeatherArmorMeta) whitechest.getItemMeta();
                    whitechestmeta.setColor(Color.WHITE);
                    whitechestmeta.setUnbreakable(true);
                    whitechest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    whitechestmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    whitechest.setItemMeta(whitechestmeta);

                    ItemStack whiteleggings = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta whiteleggingsmeta = (LeatherArmorMeta) whiteleggings.getItemMeta();
                    whiteleggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                    whiteleggingsmeta.setColor(Color.WHITE);
                    whiteleggingsmeta.setUnbreakable(true);
                    whiteleggingsmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
                    whiteleggings.setItemMeta(whiteleggingsmeta);

                    ItemStack whiteboots = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta whitebootsmeta = (LeatherArmorMeta) whiteboots.getItemMeta();
                    whitebootsmeta.setColor(Color.WHITE);
                    whitebootsmeta.setUnbreakable(true);
                    whitebootsmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
                    whiteboots.setItemMeta(whitebootsmeta);

                    player.getInventory().setItem(0, snowmanArm);
                    player.getInventory().setItem(1, snowfort);
                    player.getInventory().setItem(2, snowball);
                    setSkull(player, "698f449d-c3be-4028-b929-8d51224e2665");
                    player.getInventory().setChestplate(whitechest);
                    player.getInventory().setLeggings(whiteleggings);
                    player.getInventory().setBoots(whiteboots);
                    break;
                default:
                    Bukkit.getLogger().info("Improper class was selected:" + inventory);
                    break;
            }

        } else {
            player.sendMessage(ChatColor.RED + "This class is already filled!");
        }

    }


    public static void startGame(boolean ffa, ArenaMap map) {
        Location mapKey;
        World world = Bukkit.getServer().getWorld("arena");
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        if (map == ArenaMap.HOUSE) {

        } else if (map == ArenaMap.CASTLE) {

        } else if (map == ArenaMap.CITY) {

        } else if (map == ArenaMap.TREE) {

        }
        Team spectator = board.registerNewTeam("Spectators");
        if (ffa) {
            Team ffaTeam = board.registerNewTeam("Players");
            ffaTeam.setPrefix(ChatColor.YELLOW + "");
            ffaTeam.setAllowFriendlyFire(true);
            ffaTeam.setCanSeeFriendlyInvisibles(false);
        } else if (!ffa) {
            Team blueTeam = board.registerNewTeam("Blue");
            Team redTeam = board.registerNewTeam("Red");
            blueTeam.setPrefix(ChatColor.BLUE + "");
            redTeam.setPrefix(ChatColor.RED + "");
            blueTeam.setAllowFriendlyFire(false);
            redTeam.setAllowFriendlyFire(false);
            redTeam.setCanSeeFriendlyInvisibles(true);
            blueTeam.setCanSeeFriendlyInvisibles(true);
            redTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
            blueTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
            Collections.shuffle(world.getPlayers());
            for (Player player : world.getPlayers()) {

            }
        }
    }

    private static void setSkull(Player player, String code) {
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "replaceitem entity "+ player +" slot.armor.head skull 1 3 {display:{Name:\"Hat\"},SkullOwner:{Id:\"" + code + "\",Properties:{textures:[{Value:\"ello\"}]}}}");
    }

    private static void removePlayerClass(Player player) {
        availableClasses.entrySet().removeIf(entry -> entry.getValue().equals(player.getName()));
        //            for (Map.Entry entry : availableClasses.entrySet()) {
        //                if (entry.getValue().equals(event.getPlayer().getName())) {
        //                    entry.setValue(null);
        //                }
        //            }
    }
}
