package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.logging.Level.INFO;

public class DeathCheck implements Listener {

    // This class is called whenever a speedrunner dies.
    // Also called when the hunters win.

    private ManhuntMain manhuntMain;
    private Abilites Abilites;
    private Manhunt manhunt;
    private Manacounter manacounter;
    List<UUID> hunter;
    List<UUID> speedrunner;
    List<UUID> deadSpeedrunners;

    public DeathCheck(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;
        this.manacounter = manacounter;
        this.Abilites = Abilites;
        deadSpeedrunners = manhunt.getTeam(ManhuntTeam.DEAD);
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
    }

    @EventHandler
    public void SpeedrunnerDeath(PlayerDeathEvent event) {
        if (manhunt.hasGameStarted()) {
            if (speedrunner.contains(event.getEntity().getUniqueId())) {
                //Add a death to the player
                int death = 0;
                if(manhuntMain.getDataConfig().getConfig().contains("players." + event.getEntity().getUniqueId().toString() + ".deaths")){
                    death = manhuntMain.getDataConfig().getConfig().getInt("players." + event.getEntity().getUniqueId().toString() + ".deaths");
                }
                manhuntMain.getDataConfig().getConfig().set("players." + event.getEntity().getUniqueId().toString() + ".deaths", (death + 1));
                manhuntMain.getDataConfig().saveConfig();
                //End Adding death

                speedrunner.remove(event.getEntity().getUniqueId());
                deadSpeedrunners.add(event.getEntity().getUniqueId());
                event.getEntity().setAllowFlight(true);
                event.getEntity().setFlying(true);
                event.getEntity().setGlowing(false);

                if (speedrunner.size() == 0) {
                    // If the game was lost

                    Bukkit.getLogger().log(INFO, "[MANHUNT] The game has ended.");
                    List<String> hunterList = new ArrayList<>();
                    for(UUID uuid : hunter){
                        hunterList.add(Bukkit.getPlayer(uuid).getName());
                    }

                    String hunters = hunterList.toString().replaceAll("]", "").replaceAll("\\[", "");

                    for (UUID hunter : hunter) {
                        Player players = Bukkit.getPlayer(hunter);

                        //Add a win to the hunter
                        int wins = 0;
                        if(manhuntMain.getDataConfig().getConfig().contains("players." + players.getUniqueId().toString() + ".wins")){
                            wins = manhuntMain.getDataConfig().getConfig().getInt("players." + players.getUniqueId().toString() + ".wins");
                        }
                        manhuntMain.getDataConfig().getConfig().set("players." + players.getUniqueId().toString() + ".wins", (wins + 1));
                        manhuntMain.getDataConfig().saveConfig();
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
                        players.setCollidable(true);
                        players.chat(ChatColor.GOLD + "GG!");
                        for (org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
                            if (team.hasEntry(players.getName())) {
                                team.removeEntry(players.getName());
                            }
                        }
                        if(manhunt.getAppliedPack().contains(players.getUniqueId())) {
                            manhunt.getPackManager().unloadPack(players);
                        }
                        manhunt.getAppliedPack().clear();
                        players.setScoreboard((Bukkit.getScoreboardManager().getMainScoreboard()));
                    }
                    for (UUID player : deadSpeedrunners) {
                        Player players = Bukkit.getPlayer(player);
                        //Add a loss
                        int losses = 0;
                        if(manhuntMain.getDataConfig().getConfig().contains("players." + players.getUniqueId().toString() + ".losses")){
                            losses = manhuntMain.getDataConfig().getConfig().getInt("players." + players.getUniqueId().toString() + ".losses");
                        }
                        manhuntMain.getDataConfig().getConfig().set("players." + event.getEntity().getUniqueId().toString() + ".losses", (losses + 1));
                        manhuntMain.getDataConfig().saveConfig();
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
                    manhunt.getAppliedPack().clear();
                    speedrunner.clear();
                    deadSpeedrunners.clear();
                    hunter.clear();
                    manhunt.getTeam(ManhuntTeam.FROZEN).clear();
                    Abilites.clearCooldown();
                    manhunt.setGameStatus(false);
                    Bukkit.getScheduler().cancelTasks(manhuntMain.getPlugin());
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
                Bukkit.getScheduler().runTaskLater(manhuntMain.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        player.spigot().respawn();
                        player.teleport(deathLocation);
                    }
                }, 1L);
                player.setGameMode(GameMode.SPECTATOR);
                player.setGlowing(false);
                player.sendTitle(ChatColor.DARK_RED + "YOU DIED", ChatColor.RED + "Better luck next time.", 20, 100, 20);
            }
        }
    }
}
