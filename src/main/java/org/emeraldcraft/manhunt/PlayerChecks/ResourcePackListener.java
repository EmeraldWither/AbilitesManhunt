package org.emeraldcraft.manhunt.PlayerChecks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.emeraldcraft.manhunt.Manhunt;

public class ResourcePackListener implements Listener {

    private Manhunt manhunt;
    public ResourcePackListener(Manhunt manhunt){
        this.manhunt = manhunt;
    }

    @EventHandler
    public void playerLeave(PlayerJoinEvent event){
        if(manhunt.getAppliedPack().contains(event.getPlayer().getUniqueId())){
            if(manhunt.hasGameStarted()){
                manhunt.getPackManager().loadPack(event.getPlayer());
                event.getPlayer().setAllowFlight(true);
                event.getPlayer().sendMessage(ChatColor.GREEN + "Your resourcepack has been automatically applied!");
            }
        }
    }
}
