package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

public class PreventAdvancements implements Listener {
    private Manhunt manhunt;
    private ManhuntMain manhuntMain;
    public PreventAdvancements(Manhunt manhunt, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;

    }
    @EventHandler
    public void advancementEvent(PlayerAdvancementCriterionGrantEvent event){
        if(manhuntMain.getConfig().getBoolean("prevent-advancements")){
            if(manhunt.hasGameStarted()){
                if(manhunt.getTeam(event.getPlayer().getUniqueId()) == ManhuntTeam.HUNTER){
                    event.setCancelled(true);
                }
            }
        }
    }
}
