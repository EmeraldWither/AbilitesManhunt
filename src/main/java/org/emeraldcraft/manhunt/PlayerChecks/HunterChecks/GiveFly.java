package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

public class GiveFly implements Listener {

    private Manhunt manhunt;
    public GiveFly(Manhunt manhunt){
        this.manhunt = manhunt;
    }
    @EventHandler
    public void giveFly(PlayerChangedWorldEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (manhunt.getTeam(event.getPlayer().getUniqueId()) != ManhuntTeam.HUNTER) {
            return;
        }
        event.getPlayer().setAllowFlight(true);
        event.getPlayer().setFlying(true);
    }
}
