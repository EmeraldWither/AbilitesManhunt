package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

import java.util.List;
import java.util.UUID;

public class PreventProjectileThrowing implements Listener {

    private Manhunt manhunt;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public PreventProjectileThrowing(Manhunt manhunt){
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }
    @EventHandler
    public void ProjectileToss(ProjectileLaunchEvent event){
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (hunter.contains(((Player) event.getEntity().getShooter()).getUniqueId())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void projectileCollide(ProjectileCollideEvent event){
        if (!hunter.contains(event.getCollidedWith().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
}
