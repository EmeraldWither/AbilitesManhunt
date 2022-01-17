package org.emeraldcraft.manhunt.PlayerChecks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.emeraldcraft.manhunt.Manhunt;

public class ResourcePackListener implements Listener {

    private final Manhunt manhunt;
    public ResourcePackListener(Manhunt manhunt){
        this.manhunt = manhunt;
    }

    @EventHandler
    public void playerLeave(PlayerJoinEvent event){
        if (!manhunt.getAppliedPack().contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (!manhunt.hasGameStarted()) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(manhunt.getJavaPlugin(), () -> {
            manhunt.getPackManager().loadPack(event.getPlayer());
            event.getPlayer().setAllowFlight(true);
            event.getPlayer().sendMessage(ChatColor.GREEN + "Your resourcepack has been automatically applied!");
        }, 15);
    }
}
