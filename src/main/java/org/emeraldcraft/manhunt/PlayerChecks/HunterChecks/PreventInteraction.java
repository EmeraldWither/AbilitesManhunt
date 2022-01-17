package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

public class PreventInteraction implements Listener {

    private Manhunt manhunt;
    public PreventInteraction(Manhunt manhunt){
        this.manhunt = manhunt;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!manhunt.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
            return;
        }
        event.setCancelled(true);
        if(event.getClickedBlock() != null) {
            event.getClickedBlock().getState();
            if (!(event.getClickedBlock().getState() instanceof InventoryHolder)) {
                return;
            }
            event.getPlayer().openInventory(((InventoryHolder) event.getClickedBlock().getState()).getInventory());
        }
    }

}
