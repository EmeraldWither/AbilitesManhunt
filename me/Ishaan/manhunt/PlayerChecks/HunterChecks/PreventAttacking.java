package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.List;

public class PreventAttacking implements Listener {

    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void PlayerAttack(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            if(hunter.contains(((Player) event.getDamager()).getPlayer().getName())){
                event.setCancelled(true);
            }
        }
    }
}
