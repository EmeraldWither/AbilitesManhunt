package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

import java.util.List;
import java.util.UUID;

public class PreventAttacking implements Listener {

    private final Manhunt manhunt;

    List<UUID> hunter;
    public PreventAttacking(Manhunt manhunt){
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
    }

    @EventHandler
    public void PlayerAttack(EntityDamageByEntityEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (hunter.contains(((Player) event.getDamager()).getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
