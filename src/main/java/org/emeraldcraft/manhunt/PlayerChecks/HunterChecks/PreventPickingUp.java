package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class PreventPickingUp implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;
    List<String> hunter;
    List<String> speedrunner;
    public PreventPickingUp(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }


    @EventHandler
    public void ItemPickupEvent(PlayerAttemptPickupItemEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

            }
        }
    }
}
