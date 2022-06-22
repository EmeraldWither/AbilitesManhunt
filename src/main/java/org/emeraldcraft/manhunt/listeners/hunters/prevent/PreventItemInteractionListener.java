package org.emeraldcraft.manhunt.listeners.hunters.prevent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;

public class PreventItemInteractionListener implements Listener {
    @EventHandler
    public void onItemInteract(PlayerInteractEvent e) {
        ManhuntPlayer player = Manhunt.getAPI().getPlayer(e.getPlayer().getUniqueId());
        if(player == null) return;
        if(!Manhunt.getAPI().getTeam(ManhuntTeam.HUNTER).contains(player)) return;
        if (!Manhunt.getAPI().isRunning()) return;

        e.setCancelled(true);
    }
}
