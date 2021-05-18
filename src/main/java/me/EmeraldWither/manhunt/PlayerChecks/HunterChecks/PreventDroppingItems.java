package me.EmeraldWither.manhunt.PlayerChecks.HunterChecks;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;

public class PreventDroppingItems implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    public PreventDroppingItems(ManhuntGameManager manhuntGameManager, Main main){
        this.manhuntGameManager = manhuntGameManager;
        this.main = main;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);;
    }

    @EventHandler
    public void HunterDropItem(PlayerDropItemEvent event) {
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

            }
        }

    }
}
