package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.Manhunt;
import org.emeraldcraft.manhunt.Managers.ManhuntSpeedrunnerScoreboardManager;
import org.emeraldcraft.manhunt.ManhuntMain;

public class GiveSpeedrunnerScoreboard implements Listener {
    private Manhunt manhunt;
    private ManhuntMain main;
    public GiveSpeedrunnerScoreboard(Manhunt manhunt, ManhuntMain manhuntMain){
        this.manhunt = manhunt;
        main = manhuntMain;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        if(manhunt.getTeam(ManhuntTeam.SPEEDRUNNER).contains(event.getPlayer().getUniqueId())){
            if(manhunt.hasGameStarted()){
                Bukkit.getScheduler().cancelTask(manhunt.speedrunnerScoreboardID.get(event.getPlayer().getUniqueId()));
                event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        if(manhunt.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.SPEEDRUNNER)){
            if(manhunt.hasGameStarted()){
                ManhuntSpeedrunnerScoreboardManager manhuntScoreboardManager = new ManhuntSpeedrunnerScoreboardManager(manhunt, main);
                manhuntScoreboardManager.showSpeedrunnerScoreboard(event.getPlayer().getUniqueId(), main.getPlugin());
                manhunt.speedrunnerScoreboardID.remove(event.getPlayer().getUniqueId());
                manhunt.speedrunnerScoreboardID.put(event.getPlayer().getUniqueId(), manhuntScoreboardManager.id);
            }
        }
    }
}