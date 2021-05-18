package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManhuntGameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class PreventAttacking implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private Main main;

    List<String> hunter;
    public PreventAttacking(ManhuntGameManager manhuntGameManager, Main main){
        this.manhuntGameManager = manhuntGameManager;
        this.main = main;
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
