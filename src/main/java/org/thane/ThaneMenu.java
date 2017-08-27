package org.thane;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.Wood;
import org.thane.command.Arena;
import org.thane.command.ThaneWorld;
import org.thane.enums.ArenaClass;
import org.thane.enums.ArenaMap;
import org.thane.enums.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.thane.enums.ArenaClass.*;

public class ThaneMenu implements Listener {

    private static Inventory gameSelector = Bukkit.createInventory(null, 45, "Game Selector");
    private static Inventory arenaClass = Bukkit.createInventory(null, 18, "Class Selector");
    private static Inventory arenaSettings = Bukkit.createInventory(null, 9, "Arena Settings");
    private static boolean ffa = true;
    public static ArenaMap map = ArenaMap.CASTLE;

    public static void showMenu(Player player, Menu menu) {
        switch (menu) {

            case GAME_SELECTOR:
                List<String> crazySpleefLore = new ArrayList<>();
                crazySpleefLore.add(ChatColor.GRAY + "Spleef, with a few quirks.");
                List<String> arenaLore = new ArrayList<>();
                arenaLore.add(ChatColor.GRAY + "Class based PvP game.");
                List<String> ftbLore = new ArrayList<>();
                ftbLore.add(ChatColor.GRAY + "A fast paced race against the");
                ftbLore.add(ChatColor.GRAY + "clock to find the button.");
                List<String> runnerLore = new ArrayList<>();
                runnerLore.add(ChatColor.GRAY + "A fast paced race against");
                runnerLore.add(ChatColor.GRAY + "the clock to the end.");
                List<String> pvpLore = new ArrayList<>();
                pvpLore.add(ChatColor.GRAY + "Fast paced pvp and parkour minigame.");
                List<String> cannonLore = new ArrayList<>();
                cannonLore.add(ChatColor.GRAY + "1 on 1 TNT cannon warfare.");
                setSlot(gameSelector, 4, Material.SNOW_BALL, ChatColor.WHITE + "Crazy Spleef", crazySpleefLore);
                setSlot(gameSelector, 11, Material.DIAMOND_AXE, ChatColor.DARK_RED + "Arena", arenaLore);
                setSlot(gameSelector, 15, Material.STONE_BUTTON, ChatColor.GRAY + "Find The Button", ftbLore);
                setSlot(gameSelector, 22, Material.COMPASS, ChatColor.AQUA + "Spa" + ChatColor.LIGHT_PURPLE + "wn");
                setSlot(gameSelector, 29, Material.DIAMOND_BOOTS, ChatColor.BLUE + "Runner", runnerLore);
                setSlot(gameSelector, 33, Material.BOW, ChatColor.YELLOW + "Player Versus Parkour", pvpLore);
                setSlot(gameSelector, 40, Material.FIREBALL, ChatColor.RED + "Cannoneers", cannonLore);
                player.openInventory(gameSelector);
                break;

            case ARENA_CLASS:
                HashMap<ArenaClass, String> availableClasses = Arena.availableClasses;

                List<String> bruteLore = new ArrayList<>();
                bruteLore.add(ChatColor.GREEN + "Weapons:");
                bruteLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Ferocity, the diamond axe");
                bruteLore.add(ChatColor.DARK_GRAY + "   \u27A1 Sharpness 15");
                bruteLore.add(ChatColor.GREEN + "Armor:");
                bruteLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Diamond chestplate");
                bruteLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Iron leggings");
                bruteLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Iron boots");
                bruteLore.add(ChatColor.GREEN + "Nerfs:");
                bruteLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Slowness II");

                List<String> archerLore = new ArrayList<>();
                archerLore.add(ChatColor.GREEN + "Weapons:");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Sharp Shooter, the bow");
                archerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Power 7");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Dagger, the wooden sword");
                archerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Knockback 2");
                archerLore.add(ChatColor.GREEN + "Armor:");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail chestplate");
                archerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Protection 3");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail leggings");
                archerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Protection 3");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail boots");
                archerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Protection 3");
                archerLore.add(ChatColor.GREEN + "Buffs:");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Speed II");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Jump Boost II");
                archerLore.add(ChatColor.GREEN + "Accessories:");
                archerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "3 Splash Potion of Slowness 3 (0:20)");

                List<String> minerLore = new ArrayList<>();
                minerLore.add(ChatColor.GREEN + "Weapons:");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Trusty Pick, the iron pickaxe");
                minerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Sharpness 4");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Lighter, the flint and steel");
                minerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Can break TNT");
                minerLore.add(ChatColor.GREEN + "Armor:");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Gold chestplate");
                minerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 2");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Gold leggings");
                minerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 2");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Gold boots");
                minerLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 2");
                minerLore.add(ChatColor.GREEN + "Buffs:");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Explosion Immunity");
                minerLore.add(ChatColor.GREEN + "Accessories:");
                minerLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "40 TNT");

                List<String> unicornLore = new ArrayList<>();
                unicornLore.add(ChatColor.GREEN + " \u2022 " + ChatColor.GRAY + "Unicorn Horn, the stone sword");
                unicornLore.add(ChatColor.DARK_GRAY + "   \u27A1 Sharpness 1");
                unicornLore.add(ChatColor.GREEN + "Armor:");
                unicornLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather chestplate");
                unicornLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather leggings");
                unicornLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather boots");
                unicornLore.add(ChatColor.GREEN + "Accessories:");
                unicornLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Shield");
                unicornLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "2 Extra hearts");
                unicornLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Haste II");

                List<String> dragonLore = new ArrayList<>();
                dragonLore.add(ChatColor.GREEN + "Weapons:");
                dragonLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Dragon Tooth, the iron sworld");
                dragonLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Dragon Breath, the bow");
                dragonLore.add(ChatColor.GREEN + "Armor:");
                dragonLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail leggings");
                dragonLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                dragonLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail boots");
                dragonLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                dragonLore.add(ChatColor.DARK_GRAY + "   \u27A1 Featherfalling 5");
                dragonLore.add(ChatColor.GREEN + "Accessories:");
                dragonLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Elytra flight");

                List<String> scoutLore = new ArrayList<>();
                scoutLore.add(ChatColor.GREEN + "Weapons:");
                scoutLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Bat, the stone sword");
                scoutLore.add(ChatColor.DARK_GRAY + "   \u27A1 Sharpness 1");
                scoutLore.add(ChatColor.DARK_GRAY + "   \u27A1 Knockback 2");
                scoutLore.add(ChatColor.GREEN + "Armor:");
                scoutLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail chestplate");
                scoutLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Protection 3");
                scoutLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail leggings");
                scoutLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Protection 3");
                scoutLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Chainmail boots");
                scoutLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Protection 3");
                scoutLore.add(ChatColor.GREEN + "Buffs:");
                scoutLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Speed IV");
                scoutLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Jump Boost II");

                List<String> witherLore = new ArrayList<>();
                witherLore.add(ChatColor.GREEN + "Weapons:");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Purifier, the stone sword");
                witherLore.add(ChatColor.DARK_GRAY + "   \u27A1 Fire Aspect 4");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Curser, the bow");
                witherLore.add(ChatColor.DARK_GRAY + "   \u27A1 Nausea Effect (0:10)");
                witherLore.add(ChatColor.DARK_GRAY + "   \u27A1 Blinding Effect (0:05)");
                witherLore.add(ChatColor.GREEN + "Armor:");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather chestplate");
                witherLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather leggings");
                witherLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather boots");
                witherLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                witherLore.add(ChatColor.GREEN + "Buffs:");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Speed II");
                witherLore.add(ChatColor.GREEN + "Accessories:");
                witherLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "5 Splash Potion of Withering (0:05)");

                List<String> ninjaLore = new ArrayList<>();
                ninjaLore.add(ChatColor.GREEN + "Weapons:");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Katana, the stone sword");
                ninjaLore.add(ChatColor.DARK_GRAY + "   \u27A1 Sharpness 2");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Marker, the bow");
                ninjaLore.add(ChatColor.GREEN + "Armor:");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather chestplate");
                ninjaLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 2");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather leggings");
                ninjaLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 2");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather boots");
                ninjaLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 2");
                ninjaLore.add(ChatColor.GREEN + "Buffs:");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Speed II");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Jump Boost II");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Invisibility");
                ninjaLore.add(ChatColor.GREEN + "Accessories:");
                ninjaLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Hit targets have glowing outline for a minute");

                List<String> snowmanLore = new ArrayList<>();
                snowmanLore.add(ChatColor.GREEN + "Weapons:");
                snowmanLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Stick Arm, the stone sword");
                snowmanLore.add(ChatColor.DARK_GRAY + "   \u27A1 Knockback 3");
                snowmanLore.add(ChatColor.GREEN + "Armor:");
                snowmanLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather chestplate");
                snowmanLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                snowmanLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather leggings");
                snowmanLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                snowmanLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Leather boots");
                snowmanLore.add(ChatColor.DARK_GRAY + "   \u27A1 Protection 1");
                snowmanLore.add(ChatColor.GREEN + "Accessories:");
                snowmanLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Snowballs!");
                snowmanLore.add(ChatColor.DARK_GRAY + " \u2022 " + ChatColor.GRAY + "Snowfort Ability");

                setSlot(arenaClass, 0, Material.DIAMOND_AXE, ChatColor.YELLOW + "Brute Class", bruteLore);
                setSlot(arenaClass, 1, Material.BOW, ChatColor.YELLOW + "Archer Class", archerLore);
                setSlot(arenaClass, 2, Material.IRON_PICKAXE, ChatColor.YELLOW + "Miner Class", minerLore);
                setSlot(arenaClass, 3, Material.SADDLE, ChatColor.YELLOW + "Unicorn Class", unicornLore);
                setSlot(arenaClass, 4, Material.DRAGONS_BREATH, ChatColor.YELLOW + "Dragon Class", dragonLore);
                setSlot(arenaClass, 5, Material.QUARTZ, ChatColor.YELLOW + "Scout Class", scoutLore);
                setSlot(arenaClass, 6, Material.COAL, ChatColor.YELLOW + "Wither Class", witherLore);
                setSlot(arenaClass, 7, Material.END_CRYSTAL, ChatColor.YELLOW + "Ninja Class", ninjaLore);
                setSlot(arenaClass, 8, Material.SNOW_BALL, ChatColor.YELLOW + "Snowman Class", snowmanLore);

                Dye greenDyeColor = new Dye(DyeColor.LIME);
                ItemStack greenDye = greenDyeColor.toItemStack();
                ItemMeta greenDyeMeta = greenDye.getItemMeta();
                greenDyeMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GREEN + "Class is Available!");
                greenDye.setItemMeta(greenDyeMeta);
                arenaClass.setItem(9, greenDye);
                arenaClass.setItem(10, greenDye);
                arenaClass.setItem(11, greenDye);
                arenaClass.setItem(12, greenDye);
                arenaClass.setItem(13, greenDye);
                arenaClass.setItem(14, greenDye);
                arenaClass.setItem(15, greenDye);
                arenaClass.setItem(16, greenDye);
                arenaClass.setItem(17, greenDye);

                Dye grayDyeColor = new Dye(DyeColor.GRAY);
                ItemStack grayDye = grayDyeColor.toItemStack();
                ItemMeta grayDyeMeta = grayDye.getItemMeta();
                grayDyeMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Class is not Available!");
                grayDye.setItemMeta(grayDyeMeta);
                for (Map.Entry entry : availableClasses.entrySet()) {
                    if (entry.getKey().equals(BRUTE)) {
                        arenaClass.setItem(9, grayDye);
                    } else if (entry.getKey().equals(ARCHER)) {
                        arenaClass.setItem(10, grayDye);
                    } else if (entry.getKey().equals(MINER)) {
                        arenaClass.setItem(11, grayDye);
                    } else if (entry.getKey().equals(UNICORN)) {
                        arenaClass.setItem(12, grayDye);
                    } else if (entry.getKey().equals(DRAGON)) {
                        arenaClass.setItem(13, grayDye);
                    } else if (entry.getKey().equals(SCOUT)) {
                        arenaClass.setItem(14, grayDye);
                    } else if (entry.getKey().equals(WITHER)) {
                        arenaClass.setItem(15, grayDye);
                    } else if (entry.getKey().equals(NINJA)) {
                        arenaClass.setItem(16, grayDye);
                    } else if (entry.getKey().equals(SNOWMAN)) {
                        arenaClass.setItem(17, grayDye);
                    }
                }
                player.openInventory(arenaClass);
                break;
            case ARENA_GAME_SETTINGS:
                List<String> gameModeLore = new ArrayList<>();
                gameModeLore.add(ChatColor.GRAY + "Click to toggle between");
                gameModeLore.add(ChatColor.GRAY + "FFA and TDM mode.");
                List<String> selectedLore = new ArrayList<>();
                selectedLore.add(ChatColor.GREEN + "SELECTED");

                setSlot(arenaSettings, 0, Material.GOLDEN_APPLE, ChatColor.GOLD + "Begin Game");
                if (ffa) {
                    setSlot(arenaSettings, 2, Material.DIAMOND_SWORD, ChatColor.YELLOW + "FFA", gameModeLore);
                } else if (!ffa) {
                    setSlot(arenaSettings, 2, Material.IRON_SWORD, ChatColor.YELLOW + "TDM", gameModeLore);
                }
                Wood birchWood = new Wood(Material.WOOD, TreeSpecies.BIRCH);
                ItemStack wood = birchWood.toItemStack();
                ItemMeta woodMeta = wood.getItemMeta();
                woodMeta.setDisplayName(ChatColor.YELLOW + "Housing Hostility");
                wood.setItemMeta(woodMeta);
                arenaSettings.setItem(4, wood);
                setSlot(arenaSettings, 5, Material.SMOOTH_BRICK, ChatColor.WHITE + "Crash Castle Clash");
                setSlot(arenaSettings, 6, Material.ICE, ChatColor.AQUA + "City Skyline Skirmish");
                Wood oakLog = new Wood(Material.LOG, TreeSpecies.GENERIC);
                ItemStack log = oakLog.toItemStack();
                ItemMeta logMeta = log.getItemMeta();
                logMeta.setDisplayName(ChatColor.GREEN + "Tree Top Trouble");
                log.setItemMeta(logMeta);
                arenaSettings.setItem(7, log);
                if (map == ArenaMap.HOUSE) {
                    woodMeta.setLore(selectedLore);
                    wood.setItemMeta(woodMeta);
                    arenaSettings.setItem(4, wood);
                } else if (map == ArenaMap.CASTLE) {
                    setSlot(arenaSettings, 5, Material.SMOOTH_BRICK, ChatColor.WHITE + "Crash Castle Clash", selectedLore);
                } else if (map == ArenaMap.CITY) {
                    setSlot(arenaSettings, 6, Material.ICE, ChatColor.AQUA + "City Skyline Skirmish", selectedLore);
                } else if (map == ArenaMap.TREE) {
                    logMeta.setLore(selectedLore);
                    log.setItemMeta(logMeta);
                    arenaSettings.setItem(7, log);
                }
                player.openInventory(arenaSettings);
                break;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryChange(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.CHEST)) {
            if (event.getClickedInventory().getTitle().equals(arenaClass.getTitle())) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();

                switch (event.getCurrentItem().getType()) {
                    case DIAMOND_AXE:
                        Arena.setClass(ArenaClass.BRUTE, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case BOW:
                        Arena.setClass(ArenaClass.ARCHER, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case IRON_PICKAXE:
                        Arena.setClass(ArenaClass.MINER, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case SADDLE:
                        Arena.setClass(ArenaClass.UNICORN, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case DRAGONS_BREATH:
                        Arena.setClass(ArenaClass.DRAGON, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case QUARTZ:
                        Arena.setClass(ArenaClass.SCOUT, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case COAL:
                        Arena.setClass(ArenaClass.WITHER, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case END_CRYSTAL:
                        Arena.setClass(ArenaClass.NINJA, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case SNOW_BALL:
                        Arena.setClass(ArenaClass.SNOWMAN, player);
                        event.getWhoClicked().closeInventory();
                        break;
                    case INK_SACK:
                        player.sendMessage(ChatColor.RED + "Please click above to select the class.");
                        break;
                }
            } else if (event.getClickedInventory().getTitle().equals(gameSelector.getTitle())) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem().getType().equals(Material.COMPASS)) {
                    ThaneWorld.teleportWorld(player, new Location(Bukkit.getServer().getWorld("world"), 172, 133, 197, 90, 0));
                    event.getWhoClicked().closeInventory();
                } else if (event.getCurrentItem().getType().equals(Material.DIAMOND_AXE)) {
                    ThaneWorld.teleportWorld(player, new Location(Bukkit.getServer().getWorld("arena"), 275, 118, 199, 90, 0));
                    event.getWhoClicked().closeInventory();
                } else if (event.getCurrentItem().getType().equals(Material.SNOW_BALL)) {
                    ThaneWorld.teleportWorld(player, new Location(Bukkit.getServer().getWorld("crazyspleef"), 475, 43, -987, 0, 0));
                    event.getWhoClicked().closeInventory();
                } else if (event.getCurrentItem().getType().equals(Material.STONE_BUTTON)) {
                    ThaneWorld.teleportWorld(player, new Location(Bukkit.getServer().getWorld("ftb"), 212, 159, 194, 0, 0));
                    event.getWhoClicked().closeInventory();
                } else if (event.getCurrentItem().getType().equals(Material.BOW)) {
                    ThaneWorld.teleportWorld(player, new Location(Bukkit.getServer().getWorld("pvp"), 200, 3, 199, -90, 0));
                    event.getWhoClicked().closeInventory();
                } else if (event.getCurrentItem().getType().equals(Material.FIREBALL)) {
                    ThaneWorld.teleportWorld(player, new Location(Bukkit.getServer().getWorld("tntcannon"), -542, 60, 454, -180, 0));
                    event.getWhoClicked().closeInventory();
                }
            } else if (event.getClickedInventory().getTitle().equals(arenaSettings.getTitle())) {
                event.setCancelled(true);
                Player player = (Player) event.getWhoClicked();

                if (event.getCurrentItem().getType().equals(Material.DIAMOND_SWORD)) {
                    ffa = false;
                    player.openInventory(arenaSettings);
                } else if (event.getCurrentItem().getType().equals(Material.IRON_SWORD)) {
                    ffa = true;
                    player.openInventory(arenaSettings);
                } else if (event.getCurrentItem().getType().equals(Material.GOLDEN_APPLE)) {
                    Arena.startGame(ffa, map);
                    player.closeInventory();
                } else if (event.getCurrentItem().getType().equals(Material.WOOD)) {
                    map = ArenaMap.HOUSE;
                    player.openInventory(arenaSettings);
                } else if (event.getCurrentItem().getType().equals(Material.SMOOTH_BRICK)) {
                    map = ArenaMap.CASTLE;
                    player.openInventory(arenaSettings);
                } else if (event.getCurrentItem().getType().equals(Material.ICE)) {
                    map = ArenaMap.CITY;
                    player.openInventory(arenaSettings);
                } else if (event.getCurrentItem().getType().equals(Material.LOG)) {
                    map = ArenaMap.TREE;
                    player.openInventory(arenaSettings);
                }
            }
        }
    }

    @EventHandler
    public static void onRightClick(PlayerInteractEvent event) throws InterruptedException {
        if (event.getMaterial().equals(Material.COMPASS)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                ThaneMenu.showMenu(event.getPlayer(), Menu.GAME_SELECTOR);
            }
        }
        if (event.getMaterial().equals(Material.NETHER_STAR)) {
            if (event.getPlayer().getWorld().equals(Bukkit.getServer().getWorld("arena"))) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    ThaneMenu.showMenu(event.getPlayer(), Menu.ARENA_CLASS);
                }
            }
        }
        if (event.getMaterial().equals(Material.BLAZE_ROD)) {
            if (event.getPlayer().getWorld().equals(Bukkit.getServer().getWorld("arena"))) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    ThaneMenu.showMenu(event.getPlayer(), Menu.ARENA_GAME_SETTINGS);
                }
            }
        }
    }

    private static void setSlot(Inventory inventory, int slot, Material material, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        itemMeta.setLore(lore);
        inventory.setItem(slot, itemStack);
    }

    private static void setSlot(Inventory inventory, int slot, Material material, String name) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(slot, itemStack);
    }
}
