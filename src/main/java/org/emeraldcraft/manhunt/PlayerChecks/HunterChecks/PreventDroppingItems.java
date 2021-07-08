package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PreventDroppingItems implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    List<UUID> hunter;
    public PreventDroppingItems(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntGameManager = manhuntGameManager;
        this.manhuntMain = manhuntMain;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);;
    }

    @EventHandler
    public void HunterDropItem(PlayerDropItemEvent event) {
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);

            }
        }

    }
}
