package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;

public class PreventDroppingItems implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    public PreventDroppingItems(ManhuntGameManager manhuntGameManager, Main main){
        this.manhuntGameManager = manhuntGameManager;
        this.main = main;
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
