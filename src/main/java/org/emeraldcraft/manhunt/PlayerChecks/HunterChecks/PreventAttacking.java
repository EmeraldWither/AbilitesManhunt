package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;

public class PreventAttacking implements Listener {

    private ManhuntGameManager manhuntGameManager;

    List<String> hunter;
    public PreventAttacking(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);;
    }

    @EventHandler
    public void PlayerAttack(EntityDamageByEntityEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (event.getDamager() instanceof Player) {
                if (hunter.contains(((Player) event.getDamager()).getPlayer().getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
