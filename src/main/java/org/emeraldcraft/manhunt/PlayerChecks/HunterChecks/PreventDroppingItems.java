package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class PreventDroppingItems implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    public PreventDroppingItems(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntGameManager = manhuntGameManager;
        this.manhuntMain = manhuntMain;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);;
    }

    @EventHandler
    public void HunterDropItem(PlayerDropItemEvent event) {
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

            }
        }

    }
}
