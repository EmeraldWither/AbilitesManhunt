package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.Managers.ManhuntSpeedrunnerScoreboardManager;
import org.emeraldcraft.manhunt.ManhuntMain;

public class GiveSpeedrunnerScoreboard implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain main;
    private AbilitesManager abilitesManager;
    public GiveSpeedrunnerScoreboard(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, AbilitesManager abilitesManager){
        this.manhuntGameManager = manhuntGameManager;
        main = manhuntMain;
        this.abilitesManager = abilitesManager;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        if(manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).contains(event.getPlayer().getName())){
            if(manhuntGameManager.getGameStatus()){
                Bukkit.getScheduler().cancelTask(manhuntGameManager.speedrunnerScoreboardID.get(event.getPlayer().getName()));
                event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        if(manhuntGameManager.getTeam(event.getPlayer().getName()).equals(ManhuntTeam.SPEEDRUNNER)){
            if(manhuntGameManager.getGameStatus()){
                ManhuntSpeedrunnerScoreboardManager manhuntScoreboardManager = new ManhuntSpeedrunnerScoreboardManager(manhuntGameManager, main);
                manhuntScoreboardManager.showSpeedrunnerScoreboard(event.getPlayer().getUniqueId(), main.getPlugin());
                manhuntGameManager.speedrunnerScoreboardID.remove(event.getPlayer().getName());
                manhuntGameManager.speedrunnerScoreboardID.put(event.getPlayer().getName(), manhuntScoreboardManager.id);
            }
        }
    }
}