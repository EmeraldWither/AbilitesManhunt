package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.Manhunt;

import java.util.List;
import java.util.UUID;

public class PreventPickingUp implements Listener {

    private Manhunt manhunt;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public PreventPickingUp(Manhunt manhunt){
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }


    @EventHandler
    public void ItemPickupEvent(PlayerAttemptPickupItemEvent event){
        if(manhunt.hasGameStarted()) {
            if (hunter.contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);

            }
        }
    }
}
