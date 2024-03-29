package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

import java.util.List;
import java.util.UUID;

public class PreventDroppingItems implements Listener {

    private Manhunt manhunt;
    List<UUID> hunter;
    public PreventDroppingItems(Manhunt manhunt){
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
    }

    @EventHandler
    public void HunterDropItem(PlayerDropItemEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (hunter.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
