package me.EmeraldWither.manhunt.PlayerChecks.HunterChecks;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.List;

public class PreventHunger implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public PreventHunger(ManhuntGameManager manhuntGameManager, Main main){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void ItemPickupEvent(FoodLevelChangeEvent event){
        if(manhuntGameManager.getGameStatus()) {
            String name = event.getEntity().getName();
            if (hunter.contains(name)) {
                event.setCancelled(true);

            }
        }
    }

}
