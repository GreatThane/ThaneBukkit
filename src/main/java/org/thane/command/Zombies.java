package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.thane.Utils;
import org.thane.enums.Direction;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GreatThane on 9/17/16.
 */
public class Zombies  {

    public static Scoreboard board;
    private static String currency = "currency";

    public boolean handleCommand(CommandSender sender, String[] args, JavaPlugin plugin) {

        //Validation

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Incorrect number of arguments!");
            return false;
        }


        String command = args[0];
        switch (command) {
            case "start":
                startGame(sender, plugin);
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

    private void startGame(CommandSender sender, JavaPlugin plugin) {

        World world = Utils.getWorld(sender);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        List<Player> gamePlayers = new ArrayList<Player>();
        Team zombie = board.registerNewTeam("survivors");
        zombie.setAllowFriendlyFire(false);
        board.registerNewObjective("gold", "dummy");
        board.getObjective("gold").setDisplayName(ChatColor.GOLD + "§lGold");
        board.getObjective("gold").setDisplaySlot(DisplaySlot.SIDEBAR);
        int x1 = -821; int y1 = 41; int z1 = -2388;
        int x2 = -874; int y2 = 68; int z2 = -2435;
        for (Player onlinePlayer : world.getPlayers()) {
            if (onlinePlayer.getLocation().getX() <= x1 &&
                    onlinePlayer.getLocation().getX() >= x2 &&
                    onlinePlayer.getLocation().getY() >= y1 &&
                    onlinePlayer.getLocation().getY() <= y2 &&
                    onlinePlayer.getLocation().getZ() <= z1 &&
                    onlinePlayer.getLocation().getZ() >= z2) {
                gamePlayers.add(onlinePlayer);
                zombie.addEntry(onlinePlayer.getDisplayName());
                Utils.clearCurrency(onlinePlayer);
            }
        }
        World animationWorld = Bukkit.getWorld("zombie");
        Location target = new Location(animationWorld, -843, 43, -2434);
        Location reference1 = new Location(animationWorld, -806, 42, -2423);
        Location reference2 = new Location(animationWorld, -814, 48, -2426);
        Animation.doAnimation(sender, target, reference1, reference2, Direction.SOUTH, 7, 8 ,3, plugin);

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
