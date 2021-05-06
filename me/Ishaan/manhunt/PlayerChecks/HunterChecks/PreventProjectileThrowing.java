package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.List;

public class PreventProjectileThrowing implements Listener {

    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void ProjectileToss(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() instanceof Player){
            if(new ManhuntCommandHandler().hasGameStarted()) {
                if (hunter.contains(((Player) event.getEntity().getShooter()).getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
