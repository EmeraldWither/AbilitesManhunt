package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;

import java.util.List;
import java.util.UUID;

public class PreventAttacking implements Listener {

    private ManhuntGameManager manhuntGameManager;

    List<UUID> hunter;
    public PreventAttacking(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);;
    }

    @EventHandler
    public void PlayerAttack(EntityDamageByEntityEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (event.getDamager() instanceof Player) {
                if (hunter.contains(((Player) event.getDamager()).getPlayer().getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
