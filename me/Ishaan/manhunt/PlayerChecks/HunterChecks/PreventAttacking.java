package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class PreventAttacking implements Listener {

    List<String> hunter = HunterList.hunters;
    private final Main main;
    public PreventAttacking(Main main){
        this.main = main;
    }
    @EventHandler
    public void PlayerAttack(EntityDamageByEntityEvent event){
        if(new ManhuntCommandHandler(main).hasGameStarted()) {

            if (event.getDamager() instanceof Player) {
                if (hunter.contains(((Player) event.getDamager()).getPlayer().getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
