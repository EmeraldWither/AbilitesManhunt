package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.ManhuntGameManager;

public class PreventAdvancements implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private Main main;
    public PreventAdvancements(ManhuntGameManager manhuntGameManager, Main main){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;

    }
    @EventHandler
    public void advancementEvent(PlayerAdvancementCriterionGrantEvent event){
        if(main.getConfig().getBoolean("prevent-advancements")){
            if(manhuntGameManager.getGameStatus()){
                if(manhuntGameManager.getTeam(Team.HUNTER).contains(event.getPlayer().getName())){
                    event.setCancelled(true);
                }
            }
        }
    }
}
