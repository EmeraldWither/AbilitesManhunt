package org.emeraldcraft.manhunt.listeners.hunters.prevent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.Hunter;

public class PreventItemPlacementListener implements Listener {
    @EventHandler
    public void onItemPickup(PlayerAttemptPickupItemEvent event){
        if(!Manhunt.getAPI().isRunning()) return;
        if(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) == null) return;
        if(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) instanceof Hunter){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemPickup(PlayerDropItemEvent event){
        if(!Manhunt.getAPI().isRunning()) return;
        if(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) == null) return;
        if(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) instanceof Hunter){
            event.setCancelled(true);
        }
    }
}
