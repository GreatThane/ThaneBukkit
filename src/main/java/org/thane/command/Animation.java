package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

/**
 * Created by GreatThane on 9/17/16.
 */
public class Animation {

    public boolean handleCommand(CommandSender sender, String[] args, JavaPlugin plugin) {

        //Validation
        if (args.length != 7) {
            sender.sendMessage("§cYou gave the incorrect number of arguments!");
            return false;
        }

        final String direction = args[3];
        if (!(direction.equalsIgnoreCase("north") ||
                direction.equalsIgnoreCase("east") ||
                direction.equalsIgnoreCase("south") ||
                direction.equalsIgnoreCase("west"))) {
            sender.sendMessage("§cNot an acceptable direction!");
            return false;
        }

        final int frames = Integer.parseInt(args[4]);
        final int frameDistance = Integer.parseInt(args[5]);
        final double fps = Double.parseDouble(args[6]);

        String targetCoord = args[0];
        String[] targetCoords = targetCoord.split(",");
        final int originTargetX = Integer.parseInt(targetCoords[0]);
        final int originTargetY = Integer.parseInt(targetCoords[1]);
        final int originTargetZ = Integer.parseInt(targetCoords[2]);

        String referenceCoord1 = args[1];
        String[] referenceCoords1 = referenceCoord1.split(",");
        final int originReferenceX1 = Integer.parseInt(referenceCoords1[0]);
        final int originReferenceY1 = Integer.parseInt(referenceCoords1[1]);
        final int originReferenceZ1 = Integer.parseInt(referenceCoords1[2]);

        String referenceCoord2 = args[2];
        String[] referenceCoords2 = referenceCoord2.split(",");
        final int originReferenceX2 = Integer.parseInt(referenceCoords2[0]);
        final int originReferenceY2 = Integer.parseInt(referenceCoords2[1]);
        final int originReferenceZ2 = Integer.parseInt(referenceCoords2[2]);

        new BukkitRunnable() {

            @Override
            public void run() {

                World world = Bukkit.getWorld("world");

                for (int frame = 0; frame < frames; frame++) {

                    int currentReferenceX1 = originReferenceX1;
                    int currentReferenceY1 = originReferenceY1;
                    int currentReferenceZ1 = originReferenceZ1;
                    int currentReferenceX2 = originReferenceX2;
                    int currentReferenceY2 = originReferenceY2;
                    int currentReferenceZ2 = originReferenceZ2;

                    if (direction.equalsIgnoreCase("east")) {
                        currentReferenceX1 = currentReferenceX1 + (frame * frameDistance);
                        currentReferenceX2 = currentReferenceX2 + (frame * frameDistance);
                    } else if (direction.equalsIgnoreCase("west")) {
                        currentReferenceX1 = currentReferenceX1 - (frame * frameDistance);
                        currentReferenceX2 = currentReferenceX2 - (frame * frameDistance);
                    } else if (direction.equalsIgnoreCase("north")) {
                        currentReferenceZ1 = currentReferenceZ1 - (frame * frameDistance);
                        currentReferenceZ2 = currentReferenceZ2 - (frame * frameDistance);
                    } else if (direction.equalsIgnoreCase("south")) {
                        currentReferenceZ1 = currentReferenceZ1 + (frame * frameDistance);
                        currentReferenceZ2 = currentReferenceZ2 + (frame * frameDistance);
                    }

                    boolean yDone = false;

                    for (int sourceY = currentReferenceY1; yDone == false; sourceY = sourceY + (currentReferenceY1 <= currentReferenceY2 ? 1 : -1)) {

                        boolean zDone = false;

                        for (int sourceZ = currentReferenceZ1; zDone == false; sourceZ = sourceZ + (currentReferenceZ1 <= currentReferenceZ2 ? 1 : -1)) {

                            boolean xDone = false;
                            for (int sourceX = currentReferenceX1; xDone == false; sourceX = sourceX + (currentReferenceX1 <= currentReferenceX2 ? 1 : -1)) {

                                // get source block
                                Block sourceBlock = world.getBlockAt(sourceX, sourceY, sourceZ);

                                // get target block
                                Block targetBlock = world.getBlockAt(
                                        originTargetX + (sourceX - currentReferenceX1),
                                        originTargetY + (sourceY - currentReferenceY1),
                                        originTargetZ + (sourceZ - currentReferenceZ1));

                                // compare target to source, if different then
                                if (!(targetBlock.getType().equals(sourceBlock.getType()) && targetBlock.getData() == sourceBlock.getData())) {
                                    //If target block has inventory, clear it first so it's inventory doesn't spew all over
                                    if(targetBlock.getState() instanceof InventoryHolder) {
                                        ((InventoryHolder)targetBlock.getState()).getInventory().clear();
                                    }

                                    //change target based on source block
                                    targetBlock.setType(sourceBlock.getType());
                                    targetBlock.setData(sourceBlock.getData());
                                    //Handle items that hold inventory
                                    BlockState sourceState = sourceBlock.getState();
                                    if(sourceState instanceof InventoryHolder) {
                                        InventoryHolder sourceInventory = (InventoryHolder) sourceState;
                                        InventoryHolder targetInventory = (InventoryHolder) targetBlock.getState();
                                        targetInventory.getInventory().setContents(sourceInventory.getInventory().getContents());
                                    } else if (sourceState instanceof CommandBlock) {
                                        CommandBlock sourceCommand = (CommandBlock) sourceState;
                                        CommandBlock targetCommand = (CommandBlock) targetBlock.getState();
                                        targetCommand.setCommand(sourceCommand.getCommand());
                                        targetCommand.update();
                                    }


                                }

                                if (sourceX == currentReferenceX2) {
                                    xDone = true;
                                }
                            }

                            if (sourceZ == currentReferenceZ2) {
                                zDone = true;
                            }
                        }

                        if (sourceY == currentReferenceY2) {
                            yDone = true;
                        }
                    }

                    long millis = (long) (1000 / fps);
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException ie) {

                    }

                }
            }
        }.runTaskAsynchronously(plugin);

        return true;
    }


}
