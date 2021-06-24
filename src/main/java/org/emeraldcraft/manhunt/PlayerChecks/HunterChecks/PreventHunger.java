package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class PreventHunger implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public PreventHunger(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }

    @EventHandler
    public void ItemPickupEvent(FoodLevelChangeEvent event){
        if(manhuntGameManager.getGameStatus()) {
            String name = event.getEntity().getName();
            if (hunter.contains(name)) {
                event.setCancelled(true);

            }
        }
    }

}
