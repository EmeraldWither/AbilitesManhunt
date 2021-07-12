package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

public class PreventAdvancements implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;
    public PreventAdvancements(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;

    }
    @EventHandler
    public void advancementEvent(PlayerAdvancementCriterionGrantEvent event){
        if(manhuntMain.getConfig().getBoolean("prevent-advancements")){
            if(manhuntGameManager.hasGameStarted()){
                if(manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(event.getPlayer().getUniqueId())){
                    event.setCancelled(true);
                }
            }
        }
    }
}
