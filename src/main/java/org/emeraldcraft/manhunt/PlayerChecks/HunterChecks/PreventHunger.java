package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

import java.util.List;
import java.util.UUID;

public class PreventHunger implements Listener {

    private final Manhunt manhunt;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public PreventHunger(Manhunt manhunt){
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
    }

    @EventHandler
    public void ItemPickupEvent(FoodLevelChangeEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        UUID uuid = event.getEntity().getUniqueId();
        if (hunter.contains(uuid)) {
            event.setCancelled(true);

        }
    }

}
