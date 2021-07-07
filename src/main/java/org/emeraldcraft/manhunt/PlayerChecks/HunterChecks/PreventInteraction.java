package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;

public class PreventInteraction implements Listener {

    private ManhuntGameManager manhuntGameManager;
    public PreventInteraction(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(manhuntGameManager.getGameStatus()){
            if(manhuntGameManager.getTeam(event.getPlayer().getName()).equals(ManhuntTeam.HUNTER)){
                event.setCancelled(true);
                if(event.getClickedBlock() != null) {
                    event.getClickedBlock().getState();
                    if (event.getClickedBlock().getState() instanceof InventoryHolder) {
                        event.getPlayer().openInventory(((InventoryHolder) event.getClickedBlock().getState()).getInventory());
                    }
                }
            }
        }
    }

}
