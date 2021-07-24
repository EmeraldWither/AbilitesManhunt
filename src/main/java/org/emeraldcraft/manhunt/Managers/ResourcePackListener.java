package org.emeraldcraft.manhunt.Managers;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;

public class ResourcePackListener implements Listener {

    private ManhuntGameManager manhuntGameManager;
    public ResourcePackListener(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
    }

    @EventHandler
    public void onResourcePack(PlayerResourcePackStatusEvent event){
        if(event.getStatus() == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED){
            if(manhuntGameManager.getTeam(event.getPlayer().getUniqueId()) == ManhuntTeam.HUNTER){
                if(!manhuntGameManager.getAppliedPack().contains(event.getPlayer().getUniqueId())){
                    manhuntGameManager.getAppliedPack().add(event.getPlayer().getUniqueId());
                    event.getPlayer().sendMessage(ChatColor.GREEN + "Your resourcepack has loaded!");
                }
            }
        }
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
