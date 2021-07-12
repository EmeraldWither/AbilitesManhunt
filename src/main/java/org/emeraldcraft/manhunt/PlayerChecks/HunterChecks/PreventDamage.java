package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;

public class PreventDamage implements Listener {

    private ManhuntGameManager manhuntGameManager;
    public PreventDamage(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
    }

    @EventHandler
    public void damageEvent(EntityDamageEvent event){
        if(manhuntGameManager.hasGameStarted()) {
            if (event.getEntity() instanceof Player) {
                if (manhuntGameManager.getTeam(event.getEntity().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
