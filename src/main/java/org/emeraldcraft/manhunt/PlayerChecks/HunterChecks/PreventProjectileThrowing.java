package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PreventProjectileThrowing implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public PreventProjectileThrowing(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }

    @EventHandler
    public void ProjectileToss(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() instanceof Player){
            if(manhuntGameManager.hasGameStarted()) {
                if (hunter.contains(((Player) event.getEntity().getShooter()).getUniqueId())) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void projectileCollide(ProjectileCollideEvent event){
        if(hunter.contains(event.getCollidedWith().getUniqueId())){
            event.setCancelled(true);
        }
    }
}
