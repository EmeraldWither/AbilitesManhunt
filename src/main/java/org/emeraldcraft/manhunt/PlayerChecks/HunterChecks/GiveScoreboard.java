package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.Managers.ManhuntScoreboardManager;
import org.emeraldcraft.manhunt.ManhuntMain;

public class GiveScoreboard implements Listener {
    private ManhuntScoreboardManager manhuntScoreboardManager;
    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain main;
    private AbilitesManager abilitesManager;
    public GiveScoreboard(ManhuntGameManager manhuntGameManager, ManhuntScoreboardManager manhuntScoreboardManager, ManhuntMain manhuntMain, AbilitesManager abilitesManager){
        this.manhuntGameManager = manhuntGameManager;
        this.manhuntScoreboardManager = manhuntScoreboardManager;
        main = manhuntMain;
        this.abilitesManager = abilitesManager;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        if(manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(event.getPlayer().getName())){
            if(manhuntGameManager.getGameStatus()){
                Bukkit.getScheduler().cancelTask(manhuntGameManager.hunterScoreboardID.get(event.getPlayer().getName()));
                event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event){
        if(manhuntGameManager.getTeam(event.getPlayer().getName()).equals(ManhuntTeam.HUNTER)){
            if(manhuntGameManager.getGameStatus()){
                ManhuntScoreboardManager manhuntScoreboardManager = new ManhuntScoreboardManager(manhuntGameManager, abilitesManager);
                manhuntScoreboardManager.showHunterScoreboard(event.getPlayer().getUniqueId(), main.plugin);
                manhuntGameManager.hunterScoreboardID.remove(event.getPlayer().getName());
                manhuntGameManager.hunterScoreboardID.put(event.getPlayer().getName(), manhuntScoreboardManager.id);
            }
        }
    }
}
