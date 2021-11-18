package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PreventHunger implements Listener {

    private ManhuntMain manhuntMain;
    private Manhunt manhunt;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public PreventHunger(Manhunt manhunt, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }

    @EventHandler
    public void ItemPickupEvent(FoodLevelChangeEvent event){
        if(manhunt.hasGameStarted()) {
            UUID uuid = event.getEntity().getUniqueId();
            if (hunter.contains(uuid)) {
                event.setCancelled(true);

            }
        }
    }

}
