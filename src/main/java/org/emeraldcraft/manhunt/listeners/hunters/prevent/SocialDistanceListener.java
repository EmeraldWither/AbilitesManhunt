package org.emeraldcraft.manhunt.listeners.hunters.prevent;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;

public class SocialDistanceListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player hunter = event.getPlayer();
        int distance = Manhunt.getAPI().getConfig().getSocialDistanceRadius();
        if (!ManhuntUtils.isHunter(event.getPlayer())) return;
        for(ManhuntPlayer player : Manhunt.getAPI().getTeam(ManhuntTeam.SPEEDRUNNER)){
            if(player.getAsBukkitPlayer().getLocation().distance(event.getPlayer().getLocation()) < distance){
                String msg = Manhunt.getAPI().getConfig().getSocialDistanceMessage();
                event.getPlayer().sendMessage(msg);
                //Push the player away
                Location loc = hunter.getLocation();
                Vector launchDirection = hunter.getLocation().toVector().add(loc.toVector().multiply(-1));
                launchDirection.setY(1.5);
                hunter.setVelocity(launchDirection);
            }
        }

    }
}
