package me.EmeraldWither.manhunt.PlayerChecks.HunterChecks;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class PreventPlacing implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private Main main;
    List<String> hunter;
    List<String> speedrunner;
    public PreventPlacing(ManhuntGameManager manhuntGameManager, Main main){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }
    @EventHandler
    public void PlayerPlace (BlockPlaceEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setBuild(false);
                event.setCancelled(true);

            }
        }
    }

    @EventHandler
    public void PlayerBreak (BlockBreakEvent event){
        if(manhuntGameManager.getGameStatus()) {
                if (hunter.contains(event.getPlayer().getName())) {
                    event.setCancelled(true);
                    event.setDropItems(false);
                    event.setExpToDrop(0);

                }
            }
        }
    }