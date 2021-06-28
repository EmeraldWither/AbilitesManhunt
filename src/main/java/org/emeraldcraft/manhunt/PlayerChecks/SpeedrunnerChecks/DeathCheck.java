package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class DeathCheck implements Listener {

    // This class is called whenever a speedrunner dies.
    // Also called when the hunters win.

    private ManhuntMain manhuntMain;
    private AbilitesManager AbilitesManager;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    List<String> deadSpeedrunners;

    public DeathCheck(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        this.AbilitesManager = AbilitesManager;
        deadSpeedrunners = manhuntGameManager.getTeam(ManhuntTeam.DEAD);
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
    }

    @EventHandler
    public void SpeedrunnerDeath(PlayerDeathEvent event) {
        if (manhuntGameManager.getGameStatus()) {
            if (speedrunner.contains(event.getEntity().getName())) {

                //Add a death to the player
                int death = 0;
                if(manhuntMain.data.getConfig().contains("players." + event.getEntity().getUniqueId().toString() + ".deaths")){
                    death = manhuntMain.data.getConfig().getInt("players." + event.getEntity().getUniqueId().toString() + ".deaths");
                }
                manhuntMain.data.getConfig().set("players." + event.getEntity().getUniqueId().toString() + ".deaths", (death + 1));
                manhuntMain.data.saveConfig();
                //End Adding death

                speedrunner.remove(event.getEntity().getName());
                deadSpeedrunners.add(event.getEntity().getName());
                event.getEntity().setAllowFlight(true);
                event.getEntity().setFlying(true);
                event.getEntity().setGlowing(false);
                if (speedrunner.size() == 0) {
                    String hunters = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");
                    for (String hunter : hunter) {
                        Player players = Bukkit.getPlayer(hunter);

                        //Add a win to the hunter
                        int wins = 0;
                        if(manhuntMain.data.getConfig().contains("players." + players.getUniqueId().toString() + ".wins")){
                            wins = manhuntMain.data.getConfig().getInt("players." + players.getUniqueId().toString() + ".wins");
                        }
                        manhuntMain.data.getConfig().set("players." + players.getUniqueId().toString() + ".wins", (wins + 1));
                        manhuntMain.data.saveConfig();
                        //End adding win


                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_GREEN + "Congrats to " + hunters + "!", 20, 100, 20);
                        players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                        for (String msg : manhuntMain.getConfig().getStringList("messages.hunter-win-msg.hunters")) {
                            players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                        for (PotionEffect potionEffect : players.getActivePotionEffects()) {
                            players.removePotionEffect(potionEffect.getType());
                        }
                        players.setGlowing(false);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.SURVIVAL);
                        players.setInvulnerable(false);
                        players.closeInventory();
                        players.setFlying(false);
                        players.setAllowFlight(false);
                        players.chat(ChatColor.GOLD + "GG!");
                        for (org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
                            if (team.hasEntry(players.getName())) {
                                team.removeEntry(players.getName());
                            }
                        }
                        players.setScoreboard((Bukkit.getScoreboardManager().getMainScoreboard()));
                    }
                    for (String player : deadSpeedrunners) {
                        Player players = Bukkit.getPlayer(player);


                        //Add a loss
                        int losses = 0;
                        if(manhuntMain.data.getConfig().contains("players." + players.getUniqueId().toString() + ".losses")){
                            losses = manhuntMain.data.getConfig().getInt("players." + players.getUniqueId().toString() + ".losses");
                        }
                        manhuntMain.data.getConfig().set("players." + event.getEntity().getUniqueId().toString() + ".losses", (losses + 1));
                        manhuntMain.data.saveConfig();
                        //End adding loss
                        players.sendTitle(ChatColor.DARK_RED + "DEFEATED", ChatColor.RED + "Congrats to " + hunters + "!", 20, 100, 20);
                        for (String msg : manhuntMain.getConfig().getStringList("messages.hunter-win-msg.speedrunners")) {
                            players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                        for (PotionEffect potionEffect : players.getActivePotionEffects()) {
                            players.removePotionEffect(potionEffect.getType());
                        }
                        players.setGlowing(false);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.SURVIVAL);
                        players.setInvulnerable(false);
                        players.closeInventory();
                        players.setFlying(false);
                        players.setAllowFlight(false);
                        players.chat(ChatColor.GOLD + "GG!");
                        players.setScoreboard((Bukkit.getScoreboardManager().getMainScoreboard()));
                    }
                    manacounter.clearMana();
                    manacounter.cancelMana();
                    speedrunner.clear();
                    deadSpeedrunners.clear();
                    hunter.clear();
                    manhuntGameManager.getTeam(ManhuntTeam.FROZEN).clear();
                    AbilitesManager.clearCooldown();
                    manhuntGameManager.setGameStatus(false);
                    Bukkit.getScheduler().cancelTasks(manhuntMain.plugin);
                    manhuntGameManager.getWaypoints().clear();
                    for (org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
                        if (team.getName().equalsIgnoreCase("hunterTeam") || team.getName().equalsIgnoreCase("speedrunnerTeam")) {
                            team.unregister();
                        }
                    }
                    return;
                }
                Location deathLocation = event.getEntity().getLocation();
                Vector direction = event.getEntity().getLocation().getDirection();
                deathLocation.setDirection(direction);
                Player player = event.getEntity();
                Bukkit.getScheduler().runTaskLater(manhuntMain.plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.spigot().respawn();
                        player.teleport(deathLocation);
                    }
                }, 1L);
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle(ChatColor.DARK_RED + "YOU DIED", ChatColor.RED + "Better luck next time.", 20, 100, 20);
            }
        }
    }
}
