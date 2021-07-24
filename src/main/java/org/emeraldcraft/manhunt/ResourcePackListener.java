package org.emeraldcraft.manhunt;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;

public class ResourcePackListener implements Listener {

    private ManhuntGameManager manhuntGameManager;
    public ResourcePackListener(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
    }

    @EventHandler
    public void playerLeave(PlayerJoinEvent event){
        if(manhuntGameManager.getAppliedPack().contains(event.getPlayer().getUniqueId())){
            if(manhuntGameManager.hasGameStarted()){
                manhuntGameManager.getPackManager().loadPack(event.getPlayer());
                event.getPlayer().setAllowFlight(true);
                event.getPlayer().sendMessage(ChatColor.GREEN + "Your resourcepack has been automatically applied!");
            }
        }
    }
}
