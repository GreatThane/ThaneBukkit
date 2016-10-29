package org.thane.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import static org.bukkit.Bukkit.getServer;


/**
 * Created by GreatThane on 10/22/16.
 */
public class ArmorStandClick implements Listener {

    @EventHandler
    public void standRightClick(final PlayerInteractAtEntityEvent event) {

        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStand stand = (ArmorStand)event.getRightClicked();

            Player player = event.getPlayer();
//            player.sendMessage(stand.getCustomName());

            //player.sendMessage("§cRight Click Recieved on ArmorStand");
            String shopTag = stand.getCustomName();
            if(shopTag != null) {
                getServer().dispatchCommand(getServer().getConsoleSender(), "cc open " + shopTag + " " + player.getName());
            }

        }
    }
}
