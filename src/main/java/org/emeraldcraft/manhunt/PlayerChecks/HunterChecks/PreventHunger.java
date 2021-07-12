package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PreventHunger implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public PreventHunger(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }

    @EventHandler
    public void ItemPickupEvent(FoodLevelChangeEvent event){
        if(manhuntGameManager.hasGameStarted()) {
            UUID uuid = event.getEntity().getUniqueId();
            if (hunter.contains(uuid)) {
                event.setCancelled(true);

            }
        }
    }

}
