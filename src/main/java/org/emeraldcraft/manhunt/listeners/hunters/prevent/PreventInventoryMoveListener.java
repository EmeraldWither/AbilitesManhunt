package org.emeraldcraft.manhunt.listeners.hunters.prevent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.Hunter;

public class PreventInventoryMoveListener implements Listener {
    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (!Manhunt.getAPI().isRunning()) return;
        if (!(event.getSource().getHolder() instanceof Player player)) return;
        if (Manhunt.getAPI().getPlayer(player.getUniqueId()) instanceof Hunter) {
            event.setCancelled(true);
        }
    }
}
