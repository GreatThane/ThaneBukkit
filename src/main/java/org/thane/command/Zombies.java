package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.*;
import org.thane.Utils;

import java.util.Collection;

import static sun.audio.AudioPlayer.player;

/**
 * Created by GreatThane on 9/17/16.
 */
public class Zombies  {

    public static Scoreboard board;
    private static String currency = "currency";

    public boolean handleCommand(CommandSender sender, String[] args) {

        //Validation

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Incorrect number of arguments!");
            return false;
        }


        String command = args[0];
        switch (command) {
            case "start":
                startGame();
                break;
            case "stop":
                stopGame();
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown Command");
                return false;
        }

        return true;
    }

    private void startGame() {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        Team zombie = board.registerNewTeam("survivors");
        zombie.setAllowFriendlyFire(false);
        board.registerNewObjective("gold", "dummy");
        board.getObjective("gold").setDisplayName(ChatColor.GOLD + "§lGold");
        board.getObjective("gold").setDisplaySlot(DisplaySlot.SIDEBAR);
        int x1 = -821; int y1 = 41; int z1 = -2388;
        int x2 = -874; int y2 = 68; int z2 = -2435;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getLocation().getX() <= x1 &&
                    onlinePlayer.getLocation().getX() >= x2 &&
                    onlinePlayer.getLocation().getY() >= y1 &&
                    onlinePlayer.getLocation().getY() <= y2 &&
                    onlinePlayer.getLocation().getZ() <= z1 &&
                    onlinePlayer.getLocation().getZ() >= z2) {
                zombie.addEntry(onlinePlayer.getDisplayName());
                Utils.clearCurrency(onlinePlayer);
            }
        }

        //        Objective zombieKills = board.registerNewObjective("zombies", "stat.killEntity.Zombie");
        //        Objective skeleKills = board.registerNewObjective("skeles", "stat.killEntity.Skeleton");
//        Objective currencyObjective = board.registerNewObjective(currency, "dummy");
        for(Player player : Bukkit.getOnlinePlayers()) {
            Utils.clearCurrency(player);
        }
    }

    private void stopGame() {

        // Do the stop stuff.
    }

    @EventHandler
    public void onDeath(final EntityDeathEvent event) {

        Entity entity = event.getEntity();
        if(entity.getType().equals(EntityType.ZOMBIE) || entity.getType().equals(EntityType.SKELETON)) {
            if(entity.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent damagedEvent = (EntityDamageByEntityEvent)entity.getLastDamageCause();
                if(damagedEvent.getDamager() instanceof Player) {
                    Player player = (Player)damagedEvent.getDamager();
                    Utils.incrementCurrency(player);
                }
            }
        }
    }




}