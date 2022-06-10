package org.emeraldcraft.manhunt.listeners.speedrunners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.events.speedrunner.SpeedrunnerDeathEvent;

import static org.emeraldcraft.manhunt.enums.ManhuntTeam.SPEEDRUNNER;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(Manhunt.getAPI().isRunning()) {
            //Check to see if the player is in the game
            if(Manhunt.getAPI().getPlayer(event.getEntity().getUniqueId()) == null) return;
            //Check to see if the player is a speedrunner
            if(!(Manhunt.getAPI().getPlayer(event.getEntity().getUniqueId()) instanceof Speedrunner speedrunner)) return;
            //Call our death event, and cancel the event if our event is cancelled
            SpeedrunnerDeathEvent speedrunnerDeathEvent = new SpeedrunnerDeathEvent(speedrunner, event);
            Bukkit.getPluginManager().callEvent(speedrunnerDeathEvent);
            if(speedrunnerDeathEvent.isCancelled()){
                event.setCancelled(true);
                return;
            }
            //Eliminate the player from the game
            speedrunner.eliminate();
            if(Manhunt.getAPI().getTeam(SPEEDRUNNER).size() == 0){
                Manhunt.getAPI().end();
            }
        }
    }

}
