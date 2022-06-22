package org.emeraldcraft.manhunt.listeners.hunters.prevent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.Hunter;

public class PreventAttackListener implements Listener {
    @EventHandler
    public void preventAttack(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player player){
            if(!Manhunt.getAPI().isRunning()) return;
            if(Manhunt.getAPI().getPlayer(player.getUniqueId()) == null) return;
            if(Manhunt.getAPI().getPlayer(player.getUniqueId()) instanceof Hunter){
                event.setCancelled(true);
            }
        }
    }
}
