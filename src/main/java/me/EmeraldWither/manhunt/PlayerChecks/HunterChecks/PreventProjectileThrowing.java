package me.EmeraldWither.manhunt.PlayerChecks.HunterChecks;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.List;

public class PreventProjectileThrowing implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private Main main;
    List<String> hunter;
    List<String> speedrunner;
    public PreventProjectileThrowing(ManhuntGameManager manhuntGameManager, Main main){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void ProjectileToss(ProjectileLaunchEvent event){
        if(event.getEntity().getShooter() instanceof Player){
            if(manhuntGameManager.getGameStatus() == true) {
                if (hunter.contains(((Player) event.getEntity().getShooter()).getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}