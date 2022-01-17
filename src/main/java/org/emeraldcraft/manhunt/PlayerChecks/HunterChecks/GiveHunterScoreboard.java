package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntHunterScoreboardManager;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

public class GiveHunterScoreboard implements Listener {
    private Manhunt manhunt;
    private ManhuntMain main;
    private Abilites abilites;
    public GiveHunterScoreboard(Manhunt manhunt, ManhuntMain manhuntMain, Abilites abilites){
        this.manhunt = manhunt;
        main = manhuntMain;
        this.abilites = abilites;
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent event){
        if (!main.getConfig().getBoolean("scoreboard.enabled")) {
            return;
        }
        if (!manhunt.getTeam(ManhuntTeam.HUNTER).contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (!manhunt.hasGameStarted()) {
            return;
        }
        Bukkit.getScheduler().cancelTask(manhunt.hunterScoreboardID.get(event.getPlayer().getUniqueId()));
        event.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!main.getConfig().getBoolean("scoreboard.enabled")) {
            return;
        }
        if (!manhunt.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
            return;
        }
        ManhuntHunterScoreboardManager manhuntScoreboardManager = new ManhuntHunterScoreboardManager(manhunt, abilites, main);
        manhuntScoreboardManager.showHunterScoreboard(event.getPlayer().getUniqueId(), main);
        manhunt.hunterScoreboardID.remove(event.getPlayer().getUniqueId());
        manhunt.hunterScoreboardID.put(event.getPlayer().getUniqueId(), manhuntScoreboardManager.id);
    }
}
