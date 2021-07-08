package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.Managers.ManhuntSpeedrunnerScoreboardManager;
import org.emeraldcraft.manhunt.ManhuntMain;

public class GiveSpeedrunnerScoreboard implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain main;
    public GiveSpeedrunnerScoreboard(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntGameManager = manhuntGameManager;
        main = manhuntMain;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        if(manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).contains(event.getPlayer().getUniqueId())){
            if(manhuntGameManager.getGameStatus()){
                Bukkit.getScheduler().cancelTask(manhuntGameManager.speedrunnerScoreboardID.get(event.getPlayer().getUniqueId()));
                event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        if(manhuntGameManager.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.SPEEDRUNNER)){
            if(manhuntGameManager.getGameStatus()){
                ManhuntSpeedrunnerScoreboardManager manhuntScoreboardManager = new ManhuntSpeedrunnerScoreboardManager(manhuntGameManager, main);
                manhuntScoreboardManager.showSpeedrunnerScoreboard(event.getPlayer().getUniqueId(), main.getPlugin());
                manhuntGameManager.speedrunnerScoreboardID.remove(event.getPlayer().getUniqueId());
                manhuntGameManager.speedrunnerScoreboardID.put(event.getPlayer().getUniqueId(), manhuntScoreboardManager.id);
            }
        }
    }
}