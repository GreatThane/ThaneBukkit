package org.thane.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.thane.Utils;
import static org.bukkit.Bukkit.getServer;

/**
 * Created by GreatThane on 10/29/16.
 */
public class ZombieBuy {

    public static boolean handleCommand(CommandSender sender,String[] args) {

        //Validation
        if(args.length !=2) {
            sender.sendMessage("§cIncorrect number of parameters!");
            return false;
        }

        String item = args[0];
        String userName = args[1];
        Player player = (Player) sender;

        if(player.getGameMode() == GameMode.ADVENTURE) {
//            ItemStack sword = player.getInventory()
//            ItemStack axe"iron_boots"

            int currency = Utils.getCurrency(player);
            switch(item) {
                //Swords
                case "wooden_sword":
                    if(currency >= 5) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 wooden_sword 1 0 {HideFlags:6,display:{Name:\"§6Wooden Sword\",Lore:[\"§7The go-to\",\"§7starter weapon.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "stone_sword":
                    if(currency >= 20 && slotEquals(player, 0, Material.STONE_SWORD)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 stone_sword 1 0 {HideFlags:6,display:{Name:\"§7Stone Sword\",Lore:[\"§7Picking up\",\"§7the pace!\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "wooden_sword 0 1");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "iron_sword":
                    if(currency >= 35 && slotEquals(player, 0, Material.IRON_SWORD)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 iron_sword 1 0 {HideFlags:6,display:{Name:\"§fIron Sword\",Lore:[\"§7Now we\'re\",\"§7talking!\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "stone_sword 0 1");
                        Utils.setCurrency(player, currency - 35);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "diamond_sword":
                    //Validation
                    if(!slotEquals(player, 0, Material.IRON_SWORD)) {
                        sender.sendMessage(ChatColor.RED + "Oops, you don't have an iron sword in your first slot");
                        break;
                    }
                    if(currency < 50) {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                        break;
                    }
                    getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.0 diamond_sword 1 0 {HideFlags:6,display:{Name:\"§bDiamond Sword\",Lore:[\"§7A force to\",\"§7be reckoned with!\"]},Unbreakable:1}");
                    getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_sword 0 1");
                    Utils.setCurrency(player, currency - 50);
                    break;

                case "sharpness":
                    ItemStack sword = player.getInventory().getItem(0);
                    if(sword.getEnchantments().isEmpty()) {
                        if(currency >= 10) {
                            sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
                            Utils.setCurrency(player, currency - 10);
                        }
                    } else {
                        int sharpLevel = player.getInventory().getItem(0).getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                        if(currency >= 5 * sharpLevel + 10) {
                            Utils.setCurrency(player, currency - 5 * sharpLevel + 10);
                            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, sharpLevel + 1);
                        } else {
                            sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                        }
                    }
                    break;

                //Axes
                case "wooden_axe":
                    if(currency >= 10) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 wooden_axe 1 0 {HideFlags:6,display:{Name:\"§6Wooden Axe\",Lore:[\"§7Power over\",\"§7speed!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "stone_axe":
                    if(currency >= 25 && slotEquals(player, 1, Material.STONE_AXE)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 stone_axe 1 0 {HideFlags:6,display:{Name:\"§7Stone Axe\",Lore:[\"§7Ramping up\",\"§7the strength.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "wooden_axe 0 1");
                        Utils.setCurrency(player, currency - 25);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "iron_axe":
                    if(currency >= 45 && slotEquals(player, 1, Material.IRON_AXE)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 iron_axe 1 0 {HideFlags:6,display:{Name:\"§fIron Axe\",Lore:[\"§7Bringing\",\"§7the bite.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "stone_axe 0 1");
                        Utils.setCurrency(player, currency - 45);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "diamond_axe":
                    if(currency >= 70 && slotEquals(player, 1, Material.DIAMOND_AXE)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.1 diamond_axe 1 0 {HideFlags:6,display:{Name:\"§bDiamond Sword\",Lore:[\"§7The definition of\",\"§7of the one hit K.O.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_axe 0 1");
                        Utils.setCurrency(player, currency - 70);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "knockback":
                    break;

                //Extras
                case "bow":
                    if(currency >= 40) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.8 bow 1 0 {HideFlags:6,display:{Name:\"§9Bow\",Lore:[\"§7Fighting with\",\"§7strategy.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 40);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "flame":
                    break;

                case "shield":
                    if(currency >= 35) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.weapon.offhand shield 1 0 {HideFlags:6,display:{Name:\"§9Shield\",Lore:[\"§7Going on\",\"§7the defensive.\"]}}");
                        Utils.setCurrency(player, currency - 35);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "unbreaking":
                    break;

                //Leather Armor
                case "leather_helmet":
                    if(currency >= 5) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head leather_helmet 1 0 {HideFlags:6,display:{Name:\"§6Leather Helm\",Lore:[\"§7Head\",\"§7protection.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "leather_chestplate":
                    if(currency >= 10) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest leather_chestplate 1 0 {HideFlags:6,display:{Name:\"§6Leather Chest\",Lore:[\"§7Bullet\",\"§7proof.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "leather_leggings":
                    if(currency >= 10) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs leather_leggings 1 0 {HideFlags:6,display:{Name:\"§6Leather Legs\",Lore:[\"§7Ready\",\"§7to run.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "leather_boots":
                    if(currency >= 5) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet leather_boots 1 0 {HideFlags:6,display:{Name:\"§6Leather Boots\",Lore:[\"§7Fully\",\"§7gripped.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                //Chainmail Armor
                case "chainmail_helmet":
                    if(currency >= 15 && player.getInventory().getHelmet().getType().equals(Material.CHAINMAIL_HELMET)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head chainmail_helmet 1 0 {HideFlags:6,display:{Name:\"§7Chain Helm\",Lore:[\"§7Bite\",\"§7proof\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_helmet 0 1");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "chainmail_chestplate":
                    if(currency >= 20 && player.getInventory().getChestplate().getType().equals(Material.CHAINMAIL_CHESTPLATE)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest chainmail_chestplate 1 0 {HideFlags:6,display:{Name:\"§7Chain Chest\",Lore:[\"§7Like a\",\"§7shining knight.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_chestplate 0 1");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "chainmail_leggings":
                    if(currency >= 20 && player.getInventory().getLeggings().getType().equals(Material.CHAINMAIL_LEGGINGS)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs chainmail_leggings 1 0 {HideFlags:6,display:{Name:\"§7Chain Legs\",Lore:[\"§7No more\",\"§7skinned knees.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_leggings 0 1");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "chainmail_boots":
                    if(currency >= 15 && player.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet chainmail_boots 1 0 {HideFlags:6,display:{Name:\"§7Chain Boots\",Lore:[\"§7New\",\"§7stockings.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "leather_chestplate 0 1");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                //Iron Armor
                case "iron_helmet":
                    if(currency >= 30 && player.getInventory().getHelmet().getType().equals(Material.IRON_HELMET)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head iron_helmet 1 0 {HideFlags:6,display:{Name:\"§fIron Helm\",Lore:[\"§7Construction\",\"§7hard hat.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_helmet 0 1");
                        Utils.setCurrency(player, currency - 30);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "iron_chestplate":
                    if(currency >= 35 && player.getInventory().getChestplate().getType().equals(Material.IRON_CHESTPLATE)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest iron_chestplate 1 0 {HideFlags:6,display:{Name:\"§fIron Chest\",Lore:[\"§7Kevlar\",\"§7Vest.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_chestplate 0 1");
                        Utils.setCurrency(player, currency - 35);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "iron_leggings":
                    if(currency >= 35 && player.getInventory().getLeggings().getType().equals(Material.IRON_LEGGINGS)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs iron_leggings 1 0 {HideFlags:6,display:{Name:\"§fIron Legs\",Lore:[\"§7Shin\",\"§7guards.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_leggings 0 1");
                        Utils.setCurrency(player, currency - 35);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "iron_boots":
                    if(currency >= 30 && player.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet iron_boots 1 0 {HideFlags:6,display:{Name:\"§fIron Boots\",Lore:[\"§7Just\",\"§7Do It.™\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "chain_boots 0 1");
                        Utils.setCurrency(player, currency - 30);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                //Diamond Armor
                case "diamond_helmet":
                    if(currency >= 40 && player.getInventory().getHelmet().getType().equals(Material.DIAMOND_HELMET)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.head diamond_helmet 1 0 {HideFlags:6,display:{Name:\"§bDiamond Helm\",Lore:[\"§7Thick\",\"§7Headed.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_helmet 0 1");
                        Utils.setCurrency(player, currency - 40);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "diamond_chestplate":
                    if(currency >= 45 && player.getInventory().getChestplate().getType().equals(Material.DIAMOND_CHESTPLATE)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.chest diamond_chestplate 1 0 {HideFlags:6,display:{Name:\"§bDiamond Chest\",Lore:[\"§7Pecs of\",\"§7Steel.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_chestplate 0 1");
                        Utils.setCurrency(player, currency - 45);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "diamond_leggings":
                    if(currency >= 45 && player.getInventory().getLeggings().getType().equals(Material.DIAMOND_LEGGINGS)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.legs diamond_leggings 1 0 {HideFlags:6,display:{Name:\"§bDiamond Legs\",Lore:[\"§7Look glorious\",\"§7while running away.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_leggings 0 1");
                        Utils.setCurrency(player, currency - 45);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "diamond_boots":
                    if(currency >= 40 && player.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.armor.feet diamond_boots 1 0 {HideFlags:6,display:{Name:\"§bDiamond Boots\",Lore:[\"§7Punt\",\"§7to your pleasure.\"]},Unbreakable:1}");
                        getServer().dispatchCommand(getServer().getConsoleSender(), "clear" + userName + "iron_boots 0 1");
                        Utils.setCurrency(player, currency - 40);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                //Enchants
                case "protection":
                    break;

                case "thorns":
                    break;

                case "feather_falling":
                    break;

                //Potions
                case "speed1":
                    int slotNumberSpeed1 = -1;
                    if (currency >= 5 && slotEquals(player, 2, Material.AIR)) {
                        slotNumberSpeed1 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed1 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed1 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:0,Duration:1200}],HideFlags:6,display:{Name:\"§bSpeed I\",Lore:[\"§7Make your\",\"§7gettaway.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "speed2":
                    int slotNumberSpeed2 = -1;
                    if (currency >= 10 && slotEquals(player, 2, Material.AIR)) {
                        slotNumberSpeed2 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed2 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed2 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:1,Duration:1200}],HideFlags:6,display:{Name:\"§bSpeed II\",Lore:[\"§7Book it\",\"§7before you're eaten\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "speed3":
                    int slotNumberSpeed3 = -1;
                    if (currency >= 15 && slotEquals(player, 2, Material.AIR)) {
                        slotNumberSpeed3 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed3 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed3 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:2,Duration:1200}],HideFlags:6,display:{Name:\"§bSpeed III\",Lore:[\"§7Last minute\",\"§7sugar boost\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "speed4":
                    int slotNumberSpeed4 = -1;
                    if (currency >= 20 && slotEquals(player, 2, Material.AIR)) {
                        slotNumberSpeed4 = 2;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.AQUA + " §lSpeed" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberSpeed4 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberSpeed4 + " potion 1 16394 {CustomPotionEffects:[{Id:1,Amplifier:3,Duration:1200}],HideFlags:6,display:{Name:\"§bSpeed IV\",Lore:[\"§7Sonic\",\"§7boom!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "regen1":
                    int slotNumberRegen1 = -1;
                    if (currency >= 5 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberRegen1 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen1 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen1 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:0,Duration:1200}],HideFlags:6,display:{Name:\"§cRegen I\",Lore:[\"§7Add a\",\"§7Band-Aid.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "regen2":
                    int slotNumberRegen2 = -1;
                    if (currency >= 10 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberRegen2 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen2 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen2 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:1,Duration:1200}],HideFlags:6,display:{Name:\"§cRegen II\",Lore:[\"§7You should get some\",\"§7hydrogen peroxide on that.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "regen3":
                    int slotNumberRegen3 = -1;
                    if (currency >= 15 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberRegen3 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen3 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen3 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:2,Duration:1200}],HideFlags:6,display:{Name:\"§cRegen III\",Lore:[\"§7No Pain,\",\"§7No Gain.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "regen4":
                    int slotNumberRegen4 = -1;
                    if (currency >= 20 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberRegen4 = 3;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.RED + " §lRegen" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberRegen4 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberRegen4 + " potion 1 16385 {CustomPotionEffects:[{Id:10,Amplifier:3,Duration:1200}],HideFlags:6,display:{Name:\"§cRegen IV\",Lore:[\"§7Bring on\",\"§7the damage!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "health1":
                    int slotNumberHealth1 = -1;
                    if (currency >= 5 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberHealth1 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth1 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth1 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:0,Duration:1200}],HideFlags:6,display:{Name:\"§4Health I\",Lore:[\"§7Adrenaline\",\"§7rush.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 5);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "health2":
                    int slotNumberHealth2 = -1;
                    if (currency >= 10 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberHealth2 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth2 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth2 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:1,Duration:1200}],HideFlags:6,display:{Name:\"§4Health II\",Lore:[\"§7Take a\",\"§7painkiller.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 10);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "health3":
                    int slotNumberHealth3 = -1;
                    if (currency >= 15 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberHealth3 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth3 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth3 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:2,Duration:1200}],HideFlags:6,display:{Name:\"§4Health III\",Lore:[\"§7Shoot some\",\"§7morphine.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 15);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "health4":
                    int slotNumberHealth4 = -1;
                    if (currency >= 20 && slotEquals(player, 3, Material.AIR)) {
                        slotNumberHealth4 = 4;
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "You don't have your" + ChatColor.DARK_RED + " §lHealth" + ChatColor.YELLOW + " slot open!");
                    }
                    if (slotNumberHealth4 > -1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar." + slotNumberHealth4 + " potion 1 16389 {CustomPotionEffects:[{Id:6,Amplifier:3,Duration:1200}],HideFlags:6,display:{Name:\"§4Health IV\",Lore:[\"§7Super Soldier\",\"§7Serum!\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                //Consumables
                case "arrow":
                    if(currency >= 1) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.6 arrow 1 0 {HideFlags:6,display:{Name:\"§6Arrow\",Lore:[\"§7Check your\",\"§7ammunition.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 1);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "ender_pearl":
                    if(currency >= 25) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.7 ender_pearl 1 0 {HideFlags:6,display:{Name:\"§2Ender Pearl\",Lore:[\"§7Get yourself\",\"§7out of a pinch.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 20);
                } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;

                case "apple":
                    if(currency >= 75) {
                        getServer().dispatchCommand(getServer().getConsoleSender(), "replaceitem entity " + userName + " slot.hotbar.7 golden_apple 1 1 {HideFlags:6,display:{Name:\"§dGolden Apple\",Lore:[\"§7Get a little\",\"§7boost.\"]},Unbreakable:1}");
                        Utils.setCurrency(player, currency - 50);
                    } else {
                        sender.sendMessage(ChatColor.RED + "You don't have enough" + ChatColor.YELLOW + " §lMoney" + ChatColor.RED + "!");
                    }
                    break;
                
                default:
                    sender.sendMessage(ChatColor.RED + "You can't buy this!");
                    break;
            }

            return true;

        }

        return false;
    }

    private static boolean slotEquals(Player player, int slot, Material material) {

        return player.getInventory().getItem(slot).getType().equals(material);
    }

}
