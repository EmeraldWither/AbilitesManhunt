package me.Ishaan.manhunt.LauncherListener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LaunchAbility implements Listener {

    //WIP


    @EventHandler
    public void PlayerInteract (PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = (Player) e.getPlayer();
            if(e.getPlayer().getInventory().getItemInMainHand().getLore().contains("Launches the speedrunner into the air!")){
            }
        }
    }

}
