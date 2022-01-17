package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

public class PreventEXP implements Listener {
    private final Manhunt manhunt;
    public PreventEXP(Manhunt manhunt){
        this.manhunt = manhunt;
    }

    @EventHandler
    public void onExpEvent(PlayerPickupExperienceEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (manhunt.getTeam(event.getPlayer().getUniqueId()) == ManhuntTeam.HUNTER) {
            event.setCancelled(true);
        }
    }
}
