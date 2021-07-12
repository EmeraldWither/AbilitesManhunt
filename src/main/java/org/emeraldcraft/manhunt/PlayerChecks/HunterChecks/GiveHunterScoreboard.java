package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.Managers.ManhuntHunterScoreboardManager;
import org.emeraldcraft.manhunt.ManhuntMain;

public class GiveHunterScoreboard implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain main;
    private AbilitesManager abilitesManager;
    public GiveHunterScoreboard(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, AbilitesManager abilitesManager){
        this.manhuntGameManager = manhuntGameManager;
        main = manhuntMain;
        this.abilitesManager = abilitesManager;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        if (main.getConfig().getBoolean("scoreboard.enabled")) {
            if (manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(event.getPlayer().getUniqueId())) {
                if (manhuntGameManager.hasGameStarted()) {
                    Bukkit.getScheduler().cancelTask(manhuntGameManager.hunterScoreboardID.get(event.getPlayer().getUniqueId()));
                    event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                }
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        if (main.getConfig().getBoolean("scoreboard.enabled")) {
            if (manhuntGameManager.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
                if (manhuntGameManager.hasGameStarted()) {
                    ManhuntHunterScoreboardManager manhuntScoreboardManager = new ManhuntHunterScoreboardManager(manhuntGameManager, abilitesManager, main);
                    manhuntScoreboardManager.showHunterScoreboard(event.getPlayer().getUniqueId(), main.getPlugin());
                    manhuntGameManager.hunterScoreboardID.remove(event.getPlayer().getUniqueId());
                    manhuntGameManager.hunterScoreboardID.put(event.getPlayer().getUniqueId(), manhuntScoreboardManager.id);
                }
            }
        }
    }
}
