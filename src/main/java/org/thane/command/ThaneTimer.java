package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.thane.entities.PlayerTimer;
import org.thane.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Material.WALL_SIGN;

/**
 * Created by ep9630 on 8/24/16.
 */
public class ThaneTimer {

    private static HashMap<String, PlayerTimer> playerTimers = new HashMap<String, PlayerTimer>();

    public boolean handleCommand(CommandSender sender, String[] args) {

        //validation
        if (!(args.length == 2 || args.length == 4)) {
            sender.sendMessage("Wrong number of parameters");
            return false;
        }

        String userName = args[0];
        String action = args[1];

        Player player = Utils.getOnlinePlayer(userName);
        if(player == null) {
            sender.sendMessage("§cUnable to find any players online named " + userName);
            return false;
        }
        switch (action.toLowerCase()) {
            case "start":
                //make sure player doesn't already have a running timer
                if (playerTimers.containsKey(player.getName())) {
                    sender.sendMessage(player.getName() + " already has a timer running!");
                    return false;
                }

                //add new timer for this player
                Timer timer = new Timer();
                Utils.sendTitle(sender, player, "§6GO!");
                timer.scheduleAtFixedRate(new Task(player, sender, playerTimers), 1000, 1000);
                PlayerTimer playerTimer = new PlayerTimer(sender, player, 0, timer);
                playerTimers.put(player.getName(), playerTimer);
                return true;
            case "stop":
                //find the right timer!
                if(!playerTimers.containsKey(player.getName())) {
                    sender.sendMessage("No timer running for " + player.getName());
                    return false;
                }

                //Stop the timer!
                PlayerTimer playerToStop = playerTimers.get(player.getName());
                Timer timerToStop = playerToStop.getTimer();
                timerToStop.cancel();
                Utils.sendTitle(sender, player, "§aTotal Time §2" + Utils.formatTime(playerToStop.getSeconds()), "");
                if (args.length == 4) {
                    String gameName = args[2];
                    String signLocation = args[3];
                    String[] signLocations = signLocation.split(",");
                    int signX = Integer.parseInt(signLocations[0]);
                    int signY = Integer.parseInt(signLocations[1]);
                    int signZ = Integer.parseInt(signLocations[2]);
                    Block block = player.getWorld().getBlockAt(signX, signY, signZ);
                    Bukkit.getServer().getLogger().info("Block is a " + block.getType().toString());
                    if(block.getType().equals(WALL_SIGN)) {
                        Bukkit.getServer().getLogger().info("I Matched a WALL_SIGN");
                        org.bukkit.material.Sign signData = (org.bukkit.material.Sign)block.getState().getData();
                        BlockFace face = signData.getAttachedFace();
                        for(int i=0; i <= 4; i++) {
                            int thisSignX = signX;
                            int thisSignZ = signZ;
                            Bukkit.getServer().getLogger().info("Facing " + face.toString());
                            if(face.equals(BlockFace.NORTH)) {
                                thisSignX = thisSignX + i;
                            } else if (face.equals(BlockFace.SOUTH)) {
                                thisSignX = thisSignX - i;
                            } else if (face.equals(BlockFace.EAST)) {
                                thisSignZ = thisSignZ + i;
                            } else if (face.equals(BlockFace.WEST)) {
                                thisSignZ = thisSignZ - i;
                            }
                            Block myBlock = player.getWorld().getBlockAt(thisSignX, signY, thisSignZ);
                            Sign sign = (Sign)myBlock.getState();
                            sign.setLine(0, "Hello Sign " + i);
                            sign.update();
                        }
                    }

                }
                playerTimers.remove(player.getName());
                return true;
            default:
                sender.sendMessage("Not a timer option!");
                break;
        }
        return false;
    }

    private class Task extends TimerTask {
        private Player player;
        private CommandSender sender;
        private Map<String, PlayerTimer> playerTimers;

        public Task(Player player, CommandSender sender, Map<String, PlayerTimer> playerTimers) {
            this.player = player;
            this.sender = sender;
            this.playerTimers = playerTimers;
        }

        @Override
        public void run() {
            if (!playerTimers.containsKey(player.getName())) {
                this.cancel();
                return;
            }
            PlayerTimer playerTimer = playerTimers.get(player.getName());

            if (player.isOnline() && playerTimer.getSeconds() < 3599) {
                playerTimer.incrementSeconds();
                Utils.sendTitle(sender, player, "", "§a" + Utils.formatTime(playerTimer.getSeconds()));
            } else {
                this.cancel();
                playerTimers.remove(player.getName());
            }
        }
    }
}