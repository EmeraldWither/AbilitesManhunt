package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.ManhuntGameManager;

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