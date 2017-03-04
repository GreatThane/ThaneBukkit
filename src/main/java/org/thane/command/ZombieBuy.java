package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.thane.Utils;

import static org.bukkit.Bukkit.getServer;

/**
 * Created by GreatThane on 10/29/16.
 */
public class ZombieBuy {

    public static boolean handleCommand(CommandSender sender, String[] args) {

        //Validation
        if (args.length != 2) {
            sender.sendMessage("§cIncorrect number of parameters!");
            return false;
        }

        String item = args[0];
        String userName = args[1];
        Player player = (Player) sender;

        if (player.getGameMode() == GameMode.ADVENTURE) {

            int currency = Utils.getCurrency(player);
            int price = 0;
            int maxQuantity = 0;
            switch (item) {
                //Swords
                case "wooden_sword":
                    if (!(player.getInventory().getItem(0) == null)) {
                        sender.sendMessage(ChatColor.RED + "Your first slot isn't empty!");
                        break;
                    }
                    if (currency < 5) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 wooden_sword 1 0 {HideFlags:6,display:{Name:\"§6Wooden Sword\",Lore:[\"§7The go-to\",\"§7starter weapon.\"]},Unbreakable:1}");
                    Utils.setCurrency(player, currency - 5);
                    break;

                case "stone_sword":
                    if (!slotEquals(player, 0, Material.WOOD_SWORD)) {
                        sender.sendMessage(ChatColor.RED + "You don't have a wooden sword in your first slot");
                        break;
                    }
                    if (currency < 20) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 20 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 stone_sword 1 0 {HideFlags:6,display:{Name:\"§7Stone Sword\",Lore:[\"§7Picking up\",\"§7the pace!\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "wooden_sword 0 1");
                    Utils.setCurrency(player, currency - 20);
                    break;

                case "iron_sword":
                    if (!slotEquals(player, 0, Material.STONE_SWORD)) {
                        sender.sendMessage(ChatColor.RED + "You don't have a stone sword in your first slot");
                        break;
                    }
                    if (currency < 35) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 35 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 iron_sword 1 0 {HideFlags:6,display:{Name:\"§fIron Sword\",Lore:[\"§7Now we\'re\",\"§7talking!\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "stone_sword 0 1");
                    Utils.setCurrency(player, currency - 35);
                    break;

                case "diamond_sword":
                    //Validation
                    if (!slotEquals(player, 0, Material.IRON_SWORD)) {
                        sender.sendMessage(ChatColor.RED + "You don't have an iron sword in your first slot");
                        break;
                    }
                    if (currency < 50) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 50 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 diamond_sword 1 0 {HideFlags:6,display:{Name:\"§bDiamond Sword\",Lore:[\"§7A force to\",\"§7be reckoned with!\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_sword 0 1");
                    Utils.setCurrency(player, currency - 50);
                    break;

                case "sharpness":
                    ItemStack sword = player.getInventory().getItem(0);
                    if (sword != null) {
                        if (sword.getEnchantments().isEmpty()) {
                            if (currency >= 10) {
                                sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                                Utils.setCurrency(player, currency - 10);
                            }
                        } else {
                            int sharpLevel = player.getInventory().getItem(0).getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                            if (currency >= 5 * sharpLevel + 10) {
                                Utils.setCurrency(player, currency - 5 * sharpLevel + 10);
                                sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, sharpLevel + 1);
                            } else {
                                int cost = 5 * sharpLevel + 10;
                                sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.YELLOW + ChatColor.BOLD + cost + " Gold" + ChatColor.RED + "!");
                            }
                        }
                    }
                    break;

                //Axes
                case "wooden_axe":
                    if (!(player.getInventory().getItem(1) == null)) {
                        sender.sendMessage(ChatColor.RED + "Your first slot isn't open");
                        break;
                    }
                    if (currency < 10) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 10 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 wooden_axe 1 0 {HideFlags:6,display:{Name:\"§6Wooden Axe\",Lore:[\"§7Power over\",\"§7speed!\"]},Unbreakable:1}");
                    Utils.setCurrency(player, currency - 10);
                    break;

                case "stone_axe":
                    if (!slotEquals(player, 1, Material.WOOD_AXE)) {
                        sender.sendMessage(ChatColor.RED + "You don't have a wooden axe in your first slot");
                        break;
                    }
                    if (currency < 25) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 25 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 stone_axe 1 0 {HideFlags:6,display:{Name:\"§7Stone Axe\",Lore:[\"§7Ramping up\",\"§7the strength.\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "wooden_axe 0 1");
                    Utils.setCurrency(player, currency - 25);
                    break;

                case "iron_axe":
                    if (!slotEquals(player, 1, Material.STONE_AXE)) {
                        sender.sendMessage(ChatColor.RED + "You don't have a stone axe in your first slot");
                        break;
                    }
                    if (currency < 45) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 45 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 iron_axe 1 0 {HideFlags:6,display:{Name:\"§fIron Axe\",Lore:[\"§7Bringing\",\"§7the bite.\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "stone_axe 0 1");
                    Utils.setCurrency(player, currency - 45);
                    break;

                case "diamond_axe":
                    if (!slotEquals(player, 1, Material.IRON_AXE)) {
                        sender.sendMessage(ChatColor.RED + "You don't have an iron axe in your first slot");
                        break;
                    }
                    if (currency < 70) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 70 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 diamond_axe 1 0 {HideFlags:6,display:{Name:\"§bDiamond Axe\",Lore:[\"§7The definition of\",\"§7of the one hit K.O.\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_axe 0 1");
                    Utils.setCurrency(player, currency - 70);
                    break;

                case "knockback":
                    ItemStack axe = player.getInventory().getItem(1);
                    if (axe != null) {
                        if (axe.getEnchantments().isEmpty()) {
                            if (currency >= 5) {
                                axe.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
                                Utils.setCurrency(player, currency - 5);
                            }
                        } else {
                            int knockLevel = player.getInventory().getItem(1).getEnchantmentLevel(Enchantment.KNOCKBACK);
                            if (currency >= 5 * knockLevel + 5) {
                                Utils.setCurrency(player, currency - 5 * knockLevel + 5);
                                axe.addUnsafeEnchantment(Enchantment.KNOCKBACK, knockLevel + 1);
                            } else {
                                int cost = 5 * knockLevel + 5;
                                sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.YELLOW + ChatColor.BOLD + cost + " Gold" + ChatColor.RED + "!");
                            }
                        }
                    }
                    break;

                //Extras
                case "bow":
                    if (!(player.getInventory().getItem(8) == null)) {
                        sender.sendMessage(ChatColor.RED + "Your Bow slot isn't empty");
                        break;
                    }
                    if (currency < 40) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 40 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.8 bow 1 0 {HideFlags:6,display:{Name:\"§9Bow\",Lore:[\"§7Fighting with\",\"§7strategy.\"]},Unbreakable:1}");
                    Utils.setCurrency(player, currency - 40);
                    break;

                case "flame":
                    ItemStack bow = player.getInventory().getItem(8);
                    if (bow != null) {
                        if (bow.getEnchantments().isEmpty()) {
                            if (currency >= 10) {
                                bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
                                Utils.setCurrency(player, currency - 10);
                            }
                        } else {
                            int flameLevel = player.getInventory().getItem(8).getEnchantmentLevel(Enchantment.ARROW_FIRE);
                            if (currency >= 2 * flameLevel + 10) {
                                Utils.setCurrency(player, currency - 2 * flameLevel + 10);
                                bow.addUnsafeEnchantment(Enchantment.ARROW_FIRE, flameLevel + 1);
                            } else {
                                int cost = 2 * flameLevel + 10;
                                sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.YELLOW + ChatColor.BOLD + cost + " Gold" + ChatColor.RED + "!");
                            }
                        }
                    }
                    break;

                case "shield":
                    if (currency < 35 && !(player.getInventory().getItemInOffHand() == null)) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 35 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.weapon.offhand shield 1 0 {HideFlags:6,display:{Name:\"§9Shield\",Lore:[\"§7Going on\",\"§7the defensive.\"]}}");
                    ItemStack Ishield = new ItemStack(Material.SHIELD, 1);
                    ItemMeta IshieldMeta = Ishield.getItemMeta();
                    IshieldMeta.setDisplayName("§9Shield");
                    Ishield.setItemMeta(IshieldMeta);
                    player.getInventory().setItemInOffHand(Ishield);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 35);
                    break;

                case "unbreaking":
                    ItemStack shield = player.getInventory().getItemInOffHand();
                    if (shield != null) {
                        if (shield.getEnchantments().isEmpty()) {
                            if (currency >= 10) {
                                shield.addEnchantment(Enchantment.DURABILITY, 1);
                                Utils.setCurrency(player, currency - 10);
                            }
                        } else {
                            int breakLevel = player.getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.DURABILITY);
                            if (currency >= 2 * breakLevel + 10) {
                                Utils.setCurrency(player, currency - 2 * breakLevel + 10);
                                shield.addUnsafeEnchantment(Enchantment.DURABILITY, breakLevel + 1);
                            } else {
                                int cost = 2 * breakLevel + 10;
                                sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.YELLOW + ChatColor.BOLD + cost + " Gold" + ChatColor.RED + "!");
                            }
                        }
                    }
                    break;

                //Leather Armor
                case "leather_helmet":
                    if (player.getInventory().getHelmet() != null) {
                        sender.sendMessage(ChatColor.RED + "Your head slot is filled");
                        break;
                    }
                    if (currency < 5) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    ItemStack Lhelm = new ItemStack(Material.LEATHER_HELMET, 1);
                    ItemMeta LhelmMeta = Lhelm.getItemMeta();
                    LhelmMeta.setUnbreakable(true);
                    LhelmMeta.setDisplayName("§6Leather Helm");
                    LhelmMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Lhelm.setItemMeta(LhelmMeta);
                    player.getInventory().setHelmet(Lhelm);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 5);
                    break;

                case "leather_chestplate":
                    if (player.getInventory().getChestplate() != null) {
                        sender.sendMessage(ChatColor.RED + "Your chest slot is filled");
                        break;
                    }
                    if (currency < 10) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest leather_chestplate 1 0 {HideFlags:6,display:{Name:\"§6Leather Chest\",Lore:[\"§7Bullet\",\"§7proof.\"]},Unbreakable:1}");

                    ItemStack Lchest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                    ItemMeta LChestMeta = Lchest.getItemMeta();
//                    if (!player.getInventory().getChestplate().getEnchantments().isEmpty()) {
//                        if (player.getInventory().getChestplate().getEnchantments().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {
//                            int enchantmentLevel = player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
//                            Lchest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, enchantmentLevel);
//                        }
//                        if (player.getInventory().getChestplate().getEnchantments().containsKey(Enchantment.THORNS)) {
//                            int enchantmentLevel = player.getInventory().getChestplate().getEnchantmentLevel(Enchantment.THORNS);
//                            Lchest.addUnsafeEnchantment(Enchantment.THORNS, enchantmentLevel);
//                        }
//                    }
                    LChestMeta.setUnbreakable(true);
                    LChestMeta.setDisplayName("§6Leather Chest");
                    LChestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Lchest.setItemMeta(LChestMeta);
                    player.getInventory().setChestplate(Lchest);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 10);
                    break;

                case "leather_leggings":
                    if (player.getInventory().getLeggings() != null) {
                        sender.sendMessage(ChatColor.RED + "Your leg slot is filled");
                        break;
                    }
                    if (currency < 10) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 10 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs leather_leggings 1 0 {HideFlags:6,display:{Name:\"§6Leather Legs\",Lore:[\"§7Ready\",\"§7to run.\"]},Unbreakable:1}");
                    ItemStack Lpants = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                    ItemMeta LpantsMeta = Lpants.getItemMeta();
                    LpantsMeta.setUnbreakable(true);
                    LpantsMeta.setDisplayName("§6Leather Legs");
                    LpantsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Lpants.setItemMeta(LpantsMeta);
                    player.getInventory().setLeggings(Lpants);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 10);
                    break;

                case "leather_boots":
                    if (player.getInventory().getBoots() != null) {
                        sender.sendMessage(ChatColor.RED + "Your foot slot is filled");
                        break;
                    }
                    if (currency < 5) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet leather_boots 1 0 {HideFlags:6,display:{Name:\"§6Leather Boots\",Lore:[\"§7Fully\",\"§7gripped.\"]},Unbreakable:1}");
                    ItemStack Lboots = new ItemStack(Material.LEATHER_BOOTS, 1);
                    ItemMeta LbootsMeta = Lboots.getItemMeta();
                    LbootsMeta.setUnbreakable(true);
                    LbootsMeta.setDisplayName("§6Leather Boots");
                    LbootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Lboots.setItemMeta(LbootsMeta);
                    player.getInventory().setBoots(Lboots);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 5);
                    break;

                //Chainmail Armor
                case "chainmail_helmet":
                    if (!itemIsMaterial(player.getInventory().getHelmet(), Material.LEATHER_HELMET)) {
                        sender.sendMessage(ChatColor.RED + "Your head slot doesn't have a leather helmet");
                        break;
                    }
                    if (currency < 15) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 15 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head chainmail_helmet 1 0 {HideFlags:6,display:{Name:\"§7Chain Helm\",Lore:[\"§7Bite\",\"§7proof\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_helmet 0 1");
                    int PChelm = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TChelm = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Chelm = new ItemStack(Material.CHAINMAIL_HELMET, 1);
                    if (PChelm > 0) {
                        Chelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PChelm);
                    }
                    if (TChelm > 0) {
                        Chelm.addUnsafeEnchantment(Enchantment.THORNS, TChelm);
                    }
                    ItemMeta ChelmMeta = Chelm.getItemMeta();
                    ChelmMeta.setUnbreakable(true);
                    ChelmMeta.setDisplayName("§7Chain Helm");
                    ChelmMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Chelm.setItemMeta(ChelmMeta);
                    player.getInventory().setHelmet(Chelm);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 15);
                    break;

                case "chainmail_chestplate":
                    if (!itemIsMaterial(player.getInventory().getChestplate(), Material.LEATHER_CHESTPLATE)) {
                        sender.sendMessage(ChatColor.RED + "Your chest slot doesn't have a leather chestplate");
                        break;
                    }
                    if (currency < 20) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 20 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest chainmail_chestplate 1 0 {HideFlags:6,display:{Name:\"§7Chain Chest\",Lore:[\"§7Like a\",\"§7shining knight.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_chestplate 0 1");
                    int PCchest = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TCchest = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Cchest = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
                    if (PCchest > 0) {
                        Cchest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PCchest);
                    }
                    if (TCchest > 0) {
                        Cchest.addUnsafeEnchantment(Enchantment.THORNS, TCchest);
                    }
                    ItemMeta CchestMeta = Cchest.getItemMeta();
                    CchestMeta.setUnbreakable(true);
                    CchestMeta.setDisplayName("§7Chain Chest");
                    CchestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Cchest.setItemMeta(CchestMeta);
                    player.getInventory().setChestplate(Cchest);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 20);
                    break;

                case "chainmail_leggings":
                    if (!itemIsMaterial(player.getInventory().getLeggings(), Material.LEATHER_LEGGINGS)) {
                        sender.sendMessage(ChatColor.RED + "Your leg slot doesn't have a leather leggings");
                        break;
                    }
                    if (currency < 20) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 20 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs chainmail_leggings 1 0 {HideFlags:6,display:{Name:\"§7Chain Legs\",Lore:[\"§7No more\",\"§7skinned knees.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_leggings 0 1");
                    int PClegs = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TClegs = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Clegs = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
                    if (PClegs > 0) {
                        Clegs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PClegs);
                    }
                    if (TClegs > 0) {
                        Clegs.addUnsafeEnchantment(Enchantment.THORNS, TClegs);
                    }
                    ItemMeta ClegsMeta = Clegs.getItemMeta();
                    ClegsMeta.setUnbreakable(true);
                    ClegsMeta.setDisplayName("§7Chain Legs");
                    ClegsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Clegs.setItemMeta(ClegsMeta);
                    player.getInventory().setLeggings(Clegs);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 20);
                    break;

                case "chainmail_boots":
                    if (!itemIsMaterial(player.getInventory().getBoots(), Material.LEATHER_BOOTS)) {
                        sender.sendMessage(ChatColor.RED + "Your foot slot doesn't have a leather boots");
                        break;
                    }
                    if (currency < 15) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 15 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet chainmail_boots 1 0 {HideFlags:6,display:{Name:\"§7Chain Boots\",Lore:[\"§7New\",\"§7stockings.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_chestplate 0 1");
                    int PCboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TCboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    int FCboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_FALL);
                    ItemStack Cboots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
                    if (PCboots > 0) {
                        Cboots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PCboots);
                    }
                    if (TCboots > 0) {
                        Cboots.addUnsafeEnchantment(Enchantment.THORNS, TCboots);
                    }
                    if (FCboots > 0) {
                        Cboots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, FCboots);
                    }
                    ItemMeta CbootsMeta = Cboots.getItemMeta();
                    CbootsMeta.setUnbreakable(true);
                    CbootsMeta.setDisplayName("§7Chain Boots");
                    CbootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Cboots.setItemMeta(CbootsMeta);
                    player.getInventory().setBoots(Cboots);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 15);
                    break;

                //Iron Armor
                case "iron_helmet":
                    if (!itemIsMaterial(player.getInventory().getHelmet(), Material.CHAINMAIL_HELMET)) {
                        sender.sendMessage(ChatColor.RED + "Your head slot doesn't have a chainmail helmet");
                        break;
                    }
                    if (currency < 30) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 30 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head iron_helmet 1 0 {HideFlags:6,display:{Name:\"§fIron Helm\",Lore:[\"§7Construction\",\"§7hard hat.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_helmet 0 1");
                    int PIhelm = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TIhelm = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Ihelm = new ItemStack(Material.IRON_HELMET, 1);
                    if (PIhelm > 0) {
                        Ihelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PIhelm);
                    }
                    if (PIhelm > 0) {
                        Ihelm.addUnsafeEnchantment(Enchantment.THORNS, TIhelm);
                    }
                    ItemMeta IhelmMeta = Ihelm.getItemMeta();
                    IhelmMeta.setUnbreakable(true);
                    IhelmMeta.setDisplayName("§fIron Helm");
                    IhelmMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Ihelm.setItemMeta(IhelmMeta);
                    player.getInventory().setHelmet(Ihelm);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 30);
                    break;

                case "iron_chestplate":
                    if (!itemIsMaterial(player.getInventory().getChestplate(), Material.CHAINMAIL_CHESTPLATE)) {
                        sender.sendMessage(ChatColor.RED + "Your chest slot doesn't have a chainmail chestplate");
                        break;
                    }
                    if (currency < 35) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 35 Gold" + ChatColor.RED + "!");
                        break;
                    }
                    int PIchest = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TIchest = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Ichest = new ItemStack(Material.IRON_CHESTPLATE, 1);
                    if (PIchest > 0) {
                        Ichest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PIchest);
                    }
                    if (TIchest > 0) {
                        Ichest.addUnsafeEnchantment(Enchantment.THORNS, TIchest);
                    }
                    ItemMeta IchestMeta = Ichest.getItemMeta();
                    IchestMeta.setUnbreakable(true);
                    IchestMeta.setDisplayName("§fIron Chest");
                    IchestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Ichest.setItemMeta(IchestMeta);
                    player.getInventory().setChestplate(Ichest);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 35);
                    break;

                case "iron_leggings":
                    if (!itemIsMaterial(player.getInventory().getLeggings(), Material.CHAINMAIL_LEGGINGS)) {
                        sender.sendMessage(ChatColor.RED + "Your legs slot doesn't have chainmail leggings");
                        break;
                    }
                    if (currency < 35) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 35 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs iron_leggings 1 0 {HideFlags:6,display:{Name:\"§fIron Legs\",Lore:[\"§7Shin\",\"§7guards.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_leggings 0 1");
                    int PIlegs = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TIlegs = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Ilegs = new ItemStack(Material.IRON_LEGGINGS, 1);
                    if (PIlegs > 0) {
                        Ilegs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PIlegs);
                    }
                    if (TIlegs > 0) {
                        Ilegs.addUnsafeEnchantment(Enchantment.THORNS, TIlegs);
                    }
                    ItemMeta IlegsMeta = Ilegs.getItemMeta();
                    IlegsMeta.setUnbreakable(true);
                    IlegsMeta.setDisplayName("§fIron Legs");
                    IlegsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Ilegs.setItemMeta(IlegsMeta);
                    player.getInventory().setLeggings(Ilegs);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 35);
                    break;

                case "iron_boots":
                    if (!itemIsMaterial(player.getInventory().getBoots(), Material.CHAINMAIL_BOOTS)) {
                        sender.sendMessage(ChatColor.RED + "Your foot slot doesn't have chainmail boots");
                        break;
                    }
                    if (currency < 30) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 30 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet iron_boots 1 0 {HideFlags:6,display:{Name:\"§fIron Boots\",Lore:[\"§7Just\",\"§7Do It.™\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_boots 0 1");
                    int PIboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TIboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    int FIboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_FALL);
                    ItemStack Iboots = new ItemStack(Material.IRON_BOOTS, 1);
                    if (PIboots > 0) {
                        Iboots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PIboots);
                    }
                    if (TIboots > 0) {
                        Iboots.addUnsafeEnchantment(Enchantment.THORNS, TIboots);
                    }
                    if (FIboots > 0) {
                        Iboots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, FIboots);
                    }
                    ItemMeta IbootsMeta = Iboots.getItemMeta();
                    IbootsMeta.setUnbreakable(true);
                    IbootsMeta.setDisplayName("§fIron Boots");
                    IbootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Iboots.setItemMeta(IbootsMeta);
                    player.getInventory().setBoots(Iboots);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 30);
                    break;

                //Diamond Armor
                case "diamond_helmet":
                    if (!itemIsMaterial(player.getInventory().getHelmet(), Material.IRON_HELMET)) {
                        sender.sendMessage(ChatColor.RED + "Your head slot doesn't have an iron helmet");
                        break;
                    }
                    if (currency < 40) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 40 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head diamond_helmet 1 0 {HideFlags:6,display:{Name:\"§bDiamond Helm\",Lore:[\"§7Thick\",\"§7Headed.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_helmet 0 1");
                    int PDhelm = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TDhelm = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Dhelm = new ItemStack(Material.DIAMOND_HELMET, 1);
                    if (PDhelm > 0) {
                        Dhelm.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PDhelm);
                    }
                    if (TDhelm > 0) {
                        Dhelm.addUnsafeEnchantment(Enchantment.THORNS, TDhelm);
                    }
                    ItemMeta DhelmMeta = Dhelm.getItemMeta();
                    DhelmMeta.setUnbreakable(true);
                    DhelmMeta.setDisplayName("§bDiamond Helm");
                    DhelmMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Dhelm.setItemMeta(DhelmMeta);
                    player.getInventory().setHelmet(Dhelm);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 40);
                    break;

                case "diamond_chestplate":
                    if (!itemIsMaterial(player.getInventory().getChestplate(), Material.IRON_CHESTPLATE)) {
                        sender.sendMessage(ChatColor.RED + "Your chest slot doesn't have an iron chestplate");
                        break;
                    }
                    if (currency < 45) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 45 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest diamond_chestplate 1 0 {HideFlags:6,display:{Name:\"§bDiamond Chest\",Lore:[\"§7Pecs of\",\"§7Steel.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_chestplate 0 1");
                    int PDchest = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TDchest = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Dchest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
                    if (PDchest > 0) {
                        Dchest.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PDchest);
                    }
                    if (TDchest > 0) {
                        Dchest.addUnsafeEnchantment(Enchantment.THORNS, TDchest);
                    }
                    ItemMeta DchestMeta = Dchest.getItemMeta();
                    DchestMeta.setUnbreakable(true);
                    DchestMeta.setDisplayName("§bDiamond Chest");
                    DchestMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Dchest.setItemMeta(DchestMeta);
                    player.getInventory().setChestplate(Dchest);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 45);
                    break;

                case "diamond_leggings":
                    if (!itemIsMaterial(player.getInventory().getLeggings(), Material.IRON_LEGGINGS)) {
                        sender.sendMessage(ChatColor.RED + "Your leg slot doesn't have an iron leggings");
                        break;
                    }
                    if (currency < 45) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 45 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs diamond_leggings 1 0 {HideFlags:6,display:{Name:\"§bDiamond Legs\",Lore:[\"§7Look glorious\",\"§7while running away.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_leggings 0 1");
                    int PDlegs = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TDlegs = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    ItemStack Dlegs = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
                    if (PDlegs > 0) {
                        Dlegs.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PDlegs);
                    }
                    if (TDlegs > 0) {
                    Dlegs.addUnsafeEnchantment(Enchantment.THORNS, TDlegs);
                    }
                    ItemMeta DlegsMeta = Dlegs.getItemMeta();
                    DlegsMeta.setUnbreakable(true);
                    DlegsMeta.setDisplayName("§bDiamond Legs");
                    DlegsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Dlegs.setItemMeta(DlegsMeta);
                    player.getInventory().setLeggings(Dlegs);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 45);
                    break;

                case "diamond_boots":
                    if (!itemIsMaterial(player.getInventory().getBoots(), Material.IRON_BOOTS)) {
                        sender.sendMessage(ChatColor.RED + "Your foot slot doesn't have an iron boots");
                        break;
                    }
                    if (currency < 40) {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 40 Gold" + ChatColor.RED + "!");
                        break;
                    }
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet diamond_boots 1 0 {HideFlags:6,display:{Name:\"§bDiamond Boots\",Lore:[\"§7Punt\",\"§7to your pleasure.\"]},Unbreakable:1}");
//                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_boots 0 1");
                    int PDboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                    int TDboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.THORNS);
                    int FDboots = player.getInventory().getHelmet().getEnchantmentLevel(Enchantment.PROTECTION_FALL);
                    ItemStack Dboots = new ItemStack(Material.DIAMOND_BOOTS, 1);
                    if (PDboots > 0) {
                        Dboots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, PDboots);
                    }
                    if (TDboots > 0) {
                        Dboots.addUnsafeEnchantment(Enchantment.THORNS, TDboots);
                    }
                    if (FDboots > 0) {
                    Dboots.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, FDboots);
                    }
                    ItemMeta DbootsMeta = Dboots.getItemMeta();
                    DbootsMeta.setUnbreakable(true);
                    DbootsMeta.setDisplayName("§bDiamond Boots");
                    DbootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    Dboots.setItemMeta(DbootsMeta);
                    player.getInventory().setBoots(Dboots);
                    player.updateInventory();
                    Utils.setCurrency(player, currency - 40);
                    break;

                //Enchants
                case "protection":
                    ItemStack Phelm = player.getInventory().getHelmet();
                    ItemStack Pchest = player.getInventory().getChestplate();
                    ItemStack Plegs = player.getInventory().getLeggings();
                    ItemStack Pboots = player.getInventory().getBoots();

                    //get protection levels from items

                    int highestLevel = 0;

                    if (getEnchantmentLevel(Phelm, Enchantment.PROTECTION_ENVIRONMENTAL) > highestLevel) {
                        highestLevel = getEnchantmentLevel(Phelm, Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                    if (getEnchantmentLevel(Pchest, Enchantment.PROTECTION_ENVIRONMENTAL) > highestLevel) {
                        highestLevel = getEnchantmentLevel(Pchest, Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                    if (getEnchantmentLevel(Plegs, Enchantment.PROTECTION_ENVIRONMENTAL) > highestLevel) {
                        highestLevel = getEnchantmentLevel(Plegs, Enchantment.PROTECTION_ENVIRONMENTAL);
                    }
                    if (getEnchantmentLevel(Pboots, Enchantment.PROTECTION_ENVIRONMENTAL) > highestLevel) {
                        highestLevel = getEnchantmentLevel(Pboots, Enchantment.PROTECTION_ENVIRONMENTAL);
                    }

                    //increase highest level by 1 and apply to all items
                    int newLevel = highestLevel + 1;
                    if (currency >= 5 * highestLevel + 10) {
                        setEnchantmentLevel(Phelm, Enchantment.PROTECTION_ENVIRONMENTAL, newLevel);
                        setEnchantmentLevel(Pchest, Enchantment.PROTECTION_ENVIRONMENTAL, newLevel);
                        setEnchantmentLevel(Plegs, Enchantment.PROTECTION_ENVIRONMENTAL, newLevel);
                        setEnchantmentLevel(Pboots, Enchantment.PROTECTION_ENVIRONMENTAL, newLevel);

                        //set ItemStacks to player
                        player.getInventory().setHelmet(Phelm);
                        player.getInventory().setChestplate(Pchest);
                        player.getInventory().setLeggings(Plegs);
                        player.getInventory().setBoots(Pboots);
                        player.updateInventory();
                        Utils.setCurrency(player, currency - (5 * highestLevel + 10));
                    } else {
                        int cost = 5 * highestLevel + 10;
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " " + cost + "Gold" + ChatColor.RED + "!");
                    }

                    break;

                case "thorns":
                    ItemStack Thelm = player.getInventory().getHelmet();
                    ItemStack Tchest = player.getInventory().getChestplate();
                    ItemStack Tlegs = player.getInventory().getLeggings();
                    ItemStack Tboots = player.getInventory().getBoots();

                    //get thorns levels from items

                    int highestLevel2 = 0;

                    if (getEnchantmentLevel(Thelm, Enchantment.THORNS) > highestLevel2) {
                        highestLevel2 = getEnchantmentLevel(Thelm, Enchantment.THORNS);
                    }
                    if (getEnchantmentLevel(Tchest, Enchantment.THORNS) > highestLevel2) {
                        highestLevel2 = getEnchantmentLevel(Tchest, Enchantment.THORNS);
                    }
                    if (getEnchantmentLevel(Tlegs, Enchantment.THORNS) > highestLevel2) {
                        highestLevel2 = getEnchantmentLevel(Tlegs, Enchantment.THORNS);
                    }
                    if (getEnchantmentLevel(Tboots, Enchantment.THORNS) > highestLevel2) {
                        highestLevel2 = getEnchantmentLevel(Tboots, Enchantment.THORNS);
                    }

                    //increase highest level by 1 and apply to all items
                    int newLevel2 = highestLevel2 + 1;
                    if (currency >= 5 * highestLevel2 + 10) {
                        setEnchantmentLevel(Thelm, Enchantment.THORNS, newLevel2);
                        setEnchantmentLevel(Tchest, Enchantment.THORNS, newLevel2);
                        setEnchantmentLevel(Tlegs, Enchantment.THORNS, newLevel2);
                        setEnchantmentLevel(Tboots, Enchantment.THORNS, newLevel2);

                        //set ItemStacks to player
                        player.getInventory().setHelmet(Thelm);
                        player.getInventory().setChestplate(Tchest);
                        player.getInventory().setLeggings(Tlegs);
                        player.getInventory().setBoots(Tboots);
                        player.updateInventory();
                        Utils.setCurrency(player, currency - (5 * highestLevel2 + 10));
                    } else {
                        int cost = 5 * highestLevel2 + 10;
                        sender.sendMessage(ChatColor.RED + "You don't have " + ChatColor.GOLD + ChatColor.BOLD + cost + " Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "feather_falling":
                    ItemStack boots = player.getInventory().getBoots();

                    if (player.getInventory().getBoots() != null) {
                        int level = boots.getEnchantmentLevel(Enchantment.PROTECTION_FALL);
                        if (currency >= 2 * level + 5) {
                            setEnchantmentLevel(boots, Enchantment.PROTECTION_FALL, level + 1);
                        } else {
                            int cost = 2 * level + 5;
                            sender.sendMessage(ChatColor.RED + "You don't have " + ChatColor.GOLD + ChatColor.BOLD + cost + " Gold" + ChatColor.RED + "!");
                        }
                    }
                    break;

                //Potions
                case "speed1":
                    int slotNumberSpeed1 = -1;
                    if (currency >= 5 && player.getInventory().getItem(2) == null) {
                        slotNumberSpeed1 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed1 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed1 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:0,Duration:400}],HideFlags:6,display:{Name:\"§bSpeed I\",Lore:[\"§7Make your\",\"§7gettaway.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "speed2":
                    int slotNumberSpeed2 = -1;
                    if (currency >= 10 && player.getInventory().getItem(2) == null) {
                        slotNumberSpeed2 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed2 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed2 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:1,Duration:400}],HideFlags:6,display:{Name:\"§bSpeed II\",Lore:[\"§7Book it\",\"§7before you're eaten\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 10 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "speed3":
                    int slotNumberSpeed3 = -1;
                    if (currency >= 15 && player.getInventory().getItem(2) == null) {
                        slotNumberSpeed3 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed3 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed3 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:2,Duration:400}],HideFlags:6,display:{Name:\"§bSpeed III\",Lore:[\"§7Last minute\",\"§7sugar boost\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 15 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "speed4":
                    int slotNumberSpeed4 = -1;
                    if (currency >= 20 && player.getInventory().getItem(2) == null) {
                        slotNumberSpeed4 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed4 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed4 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:3,Duration:400}],HideFlags:6,display:{Name:\"§bSpeed IV\",Lore:[\"§7Sonic\",\"§7boom!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 20 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "regen1":
                    int slotNumberRegen1 = -1;
                    if (currency >= 5 && player.getInventory().getItem(3) == null) {
                        slotNumberRegen1 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen1 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen1 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:0,Duration:200}],HideFlags:6,display:{Name:\"§cRegen I\",Lore:[\"§7Add a\",\"§7Band-Aid.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "regen2":
                    int slotNumberRegen2 = -1;
                    if (currency >= 10 && player.getInventory().getItem(3) == null) {
                        slotNumberRegen2 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen2 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen2 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:1,Duration:200}],HideFlags:6,display:{Name:\"§cRegen II\",Lore:[\"§7You should get some\",\"§7hydrogen peroxide on that.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 10 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "regen3":
                    int slotNumberRegen3 = -1;
                    if (currency >= 15 && player.getInventory().getItem(3) == null) {
                        slotNumberRegen3 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen3 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen3 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:2,Duration:200}],HideFlags:6,display:{Name:\"§cRegen III\",Lore:[\"§7No Pain,\",\"§7No Gain.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 15 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "regen4":
                    int slotNumberRegen4 = -1;
                    if (currency >= 20 && player.getInventory().getItem(3) == null) {
                        slotNumberRegen4 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen4 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen4 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:3,Duration:200}],HideFlags:6,display:{Name:\"§cRegen IV\",Lore:[\"§7Bring on\",\"§7the damage!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 20 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "health1":
                    int slotNumberHealth1 = -1;
                    if (currency >= 5 && player.getInventory().getItem(4) == null) {
                        slotNumberHealth1 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth1 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth1 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:0,Duration:1200}],HideFlags:6,display:{Name:\"§4Health I\",Lore:[\"§7Adrenaline\",\"§7rush.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 5 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "health2":
                    int slotNumberHealth2 = -1;
                    if (currency >= 10 && player.getInventory().getItem(4) == null) {
                        slotNumberHealth2 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth2 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth2 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:1,Duration:1200}],HideFlags:6,display:{Name:\"§4Health II\",Lore:[\"§7Take a\",\"§7painkiller.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 10 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "health3":
                    int slotNumberHealth3 = -1;
                    if (currency >= 15 && player.getInventory().getItem(4) == null) {
                        slotNumberHealth3 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth3 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth3 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:2,Duration:1200}],HideFlags:6,display:{Name:\"§4Health III\",Lore:[\"§7Shoot some\",\"§7morphine.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 15 Gold" + ChatColor.RED + "!");
                    }
                    break;

                case "health4":
                    int slotNumberHealth4 = -1;
                    if (currency >= 20 && player.getInventory().getItem(4) == null) {
                        slotNumberHealth4 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth4 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth4 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:3,Duration:1200}],HideFlags:6,display:{Name:\"§4Health IV\",Lore:[\"§7Super Soldier\",\"§7Serum!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have" + ChatColor.GOLD + ChatColor.BOLD + " 20 Gold" + ChatColor.RED + "!");
                    }
                    break;

                //Consumables
                case "arrow":
                    price = 1;
                    maxQuantity = 64;
                    if (currency < price) {
                        sender.sendMessage(ChatColor.RED + "You don't have " + ChatColor.GOLD + ChatColor.BOLD + price + " Gold" + ChatColor.RED + "!");
                    } else {
                        int amount = 0;
                        if (player.getInventory().getItem(5) != null) {
                            amount = player.getInventory().getItem(5).getAmount();
                        }
                        if (amount >= maxQuantity) {
                            sender.sendMessage(ChatColor.RED + "You can only have at most" + ChatColor.DARK_RED + ChatColor.ITALIC + " " + maxQuantity + ChatColor.RED + " arrows!");
                        } else {
                            amount++;
                            getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.5 arrow " + amount + " 0 {HideFlags:6,display:{Name:\"§6Arrow\",Lore:[\"§7Check your\",\"§7ammunition.\"]},Unbreakable:1}");

                        }
                    }
                    break;

                case "ender_pearl":
                    price = 20;
                    maxQuantity = 8;
                    if (currency < price) {
                        sender.sendMessage(ChatColor.RED + "You don't have " + ChatColor.GOLD + ChatColor.BOLD + price + " Gold" + ChatColor.RED + "!");
                    } else {
                        int amount = 0;
                        if (player.getInventory().getItem(6) != null) {
                            amount = player.getInventory().getItem(6).getAmount();
                        } else if (player.getInventory().getItem(6) == null) {
                            amount = 0;
                        }
                        if (amount >= maxQuantity) {
                            sender.sendMessage(ChatColor.RED + "You can only have at most" + ChatColor.DARK_RED + ChatColor.ITALIC + " " + maxQuantity + ChatColor.RED + " ender pearls!");

                        } else {
                            amount++;
                            getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.6 ender_pearl " + amount + " 0 {HideFlags:6,display:{Name:\"§2Ender Pearl\",Lore:[\"§7Get yourself\",\"§7out of a pinch.\"]},Unbreakable:1}");
                            Utils.setCurrency(player, currency - price);
                        }
                    }
                    break;

                case "apple":
                    price = 75;
                    maxQuantity = 4;
                    if (currency < price) {
                        sender.sendMessage(ChatColor.RED + "You don't have " + ChatColor.GOLD + ChatColor.BOLD + price + " Gold" + ChatColor.RED + "!");
                    } else {
                        int amount = 0;
                        if (player.getInventory().getItem(7) != null) {
                            amount = player.getInventory().getItem(7).getAmount();
                        } else if (player.getInventory().getItem(7) == null) {
                            amount = 0;
                        }
                        if (amount >= maxQuantity) {
                            sender.sendMessage(ChatColor.RED + "You can only have at most" + ChatColor.DARK_RED + ChatColor.ITALIC + " " + maxQuantity + ChatColor.RED + " Notch apples!");
                        } else {
                            amount++;
                            getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.7 golden_apple " + amount + " 1 {HideFlags:6,display:{Name:\"§dGolden Apple\",Lore:[\"§7Get a little\",\"§7boost.\"]},Unbreakable:1}");
                            Utils.setCurrency(player, currency - price);
                        }
                    }
                    break;

                default:
                    sender.sendMessage(ChatColor.RED + "You can't buy this!");
                    break;
            }

            return true;

        } else {
            sender.sendMessage(ChatColor.RED + "You must be in Adventure Mode to buy!");
        }

        return true;
    }

    private static boolean slotEquals(Player player, int slot, Material material) {

        if (player.getInventory().getItem(slot) != null) {

            return player.getInventory().getItem(slot).getType().equals(material);
        } else {
            return false;
        }
    }

    private static int getEnchantmentLevel(ItemStack item, Enchantment enchantment) {

        if (item != null) {
            return item.getEnchantmentLevel(enchantment);
        }
        return 0;
    }

    private static void setEnchantmentLevel(ItemStack item, Enchantment enchantment, int level) {

        if (item != null) {
            item.addUnsafeEnchantment(enchantment, level);
        }
    }

    private static boolean itemIsMaterial (ItemStack item, Material material) {

        if (item != null) {
            return item.getType().equals(material);
        }
        return false;
    }


}