package org.emeraldcraft.manhunt.listeners.hunters.prevent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;

public class PreventAttackListener implements Listener {
    @EventHandler
    public void preventAttack(EntityDamageByEntityEvent event){
        if(ManhuntUtils.isHunter(event.getDamager())) event.setCancelled(true);
    }
}
