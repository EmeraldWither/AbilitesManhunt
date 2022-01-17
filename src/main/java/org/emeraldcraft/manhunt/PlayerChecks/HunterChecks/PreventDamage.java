package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

public class PreventDamage implements Listener {

    private final Manhunt manhunt;
    public PreventDamage(Manhunt manhunt){
        this.manhunt = manhunt;
    }

    @EventHandler
    public void damageEvent(EntityDamageEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (manhunt.getTeam(event.getEntity().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
            event.setCancelled(true);
        }
    }
}
