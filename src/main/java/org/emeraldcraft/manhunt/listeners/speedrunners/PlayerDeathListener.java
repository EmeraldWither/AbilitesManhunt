package org.emeraldcraft.manhunt.listeners.speedrunners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.events.speedrunner.SpeedrunnerDeathEvent;

import static org.emeraldcraft.manhunt.enums.ManhuntTeam.SPEEDRUNNER;
import static org.emeraldcraft.manhunt.utils.ManhuntUtils.debug;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(Manhunt.getAPI().isRunning()) {
            //Check to see if the player is in the game
            if(Manhunt.getAPI().getPlayer(event.getEntity().getUniqueId()) == null) return;
            debug("Player that died is in the game >> " + event.getPlayer().getName());
            //Check to see if the player is a speedrunner
            if(!(Manhunt.getAPI().getPlayer(event.getEntity().getUniqueId()) instanceof Speedrunner speedrunner)) return;
            debug("Player that died is a speedrunner");
            //Call our death event, and cancel the event if our event is cancelled
            SpeedrunnerDeathEvent speedrunnerDeathEvent = new SpeedrunnerDeathEvent(speedrunner, event);
            Bukkit.getPluginManager().callEvent(speedrunnerDeathEvent);
            if(speedrunnerDeathEvent.isCancelled()){
                debug("Speedrunner death event was cancelled. Player will not die!");
                event.setCancelled(true);
                return;
            }
            //Eliminate the player from the game
            speedrunner.eliminate();
            //Check to see if the game is over
            boolean hasEnded = true;
            for (ManhuntPlayer manhuntPlayer : Manhunt.getAPI().getTeam(SPEEDRUNNER)) {
                if (!((Speedrunner) manhuntPlayer).isEliminated()) {
                    hasEnded = false;
                    break;
                }
            }
            debug("Detected game end because of all speed runner deaths. ");
            if(hasEnded) Manhunt.getAPI().end(ManhuntTeam.HUNTER);
        }
    }

}
