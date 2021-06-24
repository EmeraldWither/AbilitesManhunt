package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class PreventPlacing implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;
    List<String> hunter;
    List<String> speedrunner;
    public PreventPlacing(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
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