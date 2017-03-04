package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.thane.Utils;
import org.thane.enums.Direction;

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

        final Direction direction;

        final String directionString = args[3];
        try {
            direction = Direction.valueOf(directionString.toUpperCase());
        } catch(IllegalArgumentException iae) {
            sender.sendMessage("§cNot an acceptable direction!");
            return false;
        }

        final int frames = Integer.parseInt(args[4]);
        final int frameDistance = Integer.parseInt(args[5]);
        final double fps = Double.parseDouble(args[6]);

        World world = Utils.getWorld(sender);

        String targetCoord = args[0];
        String[] targetCoords = targetCoord.split(",");
        Location target = new Location(world,
                Double.parseDouble(targetCoords[0]),
                Double.parseDouble(targetCoords[1]),
                Double.parseDouble(targetCoords[2]));

        String referenceCoord1 = args[1];
        String[] referenceCoords1 = referenceCoord1.split(",");
        Location reference1 = new Location(world,
                Double.parseDouble(referenceCoords1[0]),
                Double.parseDouble(referenceCoords1[1]),
                Double.parseDouble(referenceCoords1[2]));

        String referenceCoord2 = args[2];
        String[] referenceCoords2 = referenceCoord2.split(",");
        Location reference2 = new Location(world,
                Double.parseDouble(referenceCoords2[0]),
                Double.parseDouble(referenceCoords2[1]),
                Double.parseDouble(referenceCoords2[2]));

        doAnimation(sender, target, reference1, reference2, direction, frames, frameDistance, fps, plugin);
        return true;
    }

    public static void doAnimation(CommandSender sender, Location target, Location reference1,
            Location reference2, Direction direction, int frames, int frameDistance, double fps, JavaPlugin plugin) {

        final World world = Utils.getWorld(sender);

        new BukkitRunnable() {

            @Override
            public void run() {

                for (int frame = 0; frame < frames; frame++) {


                    double currentReferenceX1 = reference1.getX();
                    double currentReferenceY1 = reference1.getY();
                    double currentReferenceZ1 = reference1.getZ();
                    double currentReferenceX2 = reference2.getX();
                    double currentReferenceY2 = reference2.getY();
                    double currentReferenceZ2 = reference2.getZ();

                    if (direction == Direction.EAST) {
                        currentReferenceX1 = currentReferenceX1 + (frame * frameDistance);
                        currentReferenceX2 = currentReferenceX2 + (frame * frameDistance);
                    } else if (direction == Direction.WEST) {
                        currentReferenceX1 = currentReferenceX1 - (frame * frameDistance);
                        currentReferenceX2 = currentReferenceX2 - (frame * frameDistance);
                    } else if (direction == Direction.NORTH) {
                        currentReferenceZ1 = currentReferenceZ1 - (frame * frameDistance);
                        currentReferenceZ2 = currentReferenceZ2 - (frame * frameDistance);
                    } else if (direction == Direction.SOUTH) {
                        currentReferenceZ1 = currentReferenceZ1 + (frame * frameDistance);
                        currentReferenceZ2 = currentReferenceZ2 + (frame * frameDistance);
                    }

                    boolean yDone = false;

                    for (double sourceY = currentReferenceY1; yDone == false; sourceY = sourceY + (currentReferenceY1 <= currentReferenceY2 ? 1 : -1)) {

                        boolean zDone = false;

                        for (double sourceZ = currentReferenceZ1; zDone == false; sourceZ = sourceZ + (currentReferenceZ1 <= currentReferenceZ2 ? 1 : -1)) {

                            boolean xDone = false;
                            for (double sourceX = currentReferenceX1; xDone == false; sourceX = sourceX + (currentReferenceX1 <= currentReferenceX2 ? 1 : -1)) {

                                // get source block
                                Block sourceBlock = world.getBlockAt((int)sourceX, (int)sourceY, (int)sourceZ);

                                // get target block
                                Block targetBlock = world.getBlockAt(
                                        (int)target.getX() + ((int)sourceX - (int)currentReferenceX1),
                                        (int)target.getY() + ((int)sourceY - (int)currentReferenceY1),
                                        (int)target.getZ() + ((int)sourceZ - (int)currentReferenceZ1));

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
        }.runTask(plugin);
                //runTaskAsynchronously(plugin);
    }

}
