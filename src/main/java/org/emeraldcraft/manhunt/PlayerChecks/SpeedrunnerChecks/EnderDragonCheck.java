package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.logging.Level.INFO;

public class EnderDragonCheck implements Listener {
    private Manhunt manhunt;
    private ManhuntMain manhuntMain;
    private Abilites Abilites;
    private Manacounter manacounter;
    List<UUID> hunter;
    List<UUID> speedrunner;
    List<UUID> deadSpeedrunners;

    public EnderDragonCheck(Manhunt manhunt, ManhuntMain manhuntMain, Abilites Abilites, Manacounter manacounter) {
        this.manhuntMain = manhuntMain;
        this.Abilites = Abilites;
        this.manhunt = manhunt;
        this.manacounter = manacounter;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        deadSpeedrunners = manhunt.getTeam(ManhuntTeam.DEAD);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof EnderDragon){
            if (manhunt.hasGameStarted()) {
                if (speedrunner.size() >= 1) {
                    Bukkit.getScheduler().cancelTasks(manhuntMain);


                    Bukkit.getLogger().log(INFO, "[MANHUNT] The game has ended.");
                    List<String> speedrunnerList = new ArrayList<>();
                    for(UUID uuid : hunter){
                        speedrunnerList.add(Bukkit.getPlayer(uuid).getName());
                    }
                    String speedrunners = speedrunnerList.toString().replaceAll("]", "").replaceAll("\\[", "");
                    for (UUID hunter : hunter) {
                        Player players = Bukkit.getPlayer(hunter);

                        if(manhunt.isDatabaseEnabled()){
                            Bukkit.getScheduler().runTaskAsynchronously(manhuntMain, new Runnable() {
                                @Override
                                public void run() {
                                    manhunt.getDatabase().addManhuntLoss(players.getUniqueId());
                                }
                            });
                        }
                        else {
                            int losses = 0;
                            if(manhuntMain.getDataConfig().getConfig().contains("players." + players.getUniqueId().toString() + ".losses")){
                                losses = manhuntMain.getDataConfig().getConfig().getInt("players." + players.getUniqueId().toString() + ".losses");
                            }
                            manhuntMain.getDataConfig().getConfig().set("players." + players.getUniqueId().toString() + ".losses", (losses + 1));
                            manhuntMain.getDataConfig().saveConfig();
                        }


                        players.setAllowFlight(false);
                        players.sendTitle(ChatColor.DARK_RED + "DEFEATED", ChatColor.RED + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                        for (String msg : manhuntMain.getConfig().getStringList("messages.speedrunner-win-msg.hunters")) {
                            players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                        for (PotionEffect potionEffect : players.getActivePotionEffects()) {
                            players.removePotionEffect(potionEffect.getType());
                        }
                        players.setGlowing(false);
                        ;
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
                        players.setScoreboard((Bukkit.getScoreboardManager().getMainScoreboard()));
                        if(manhunt.getAppliedPack().contains(players.getUniqueId())) {
                            manhunt.getPackManager().unloadPack(players);
                        }
                    }
                    for (UUID player : speedrunner) {
                        Player players = Bukkit.getPlayer(player);

                        if(manhunt.isDatabaseEnabled()){
                            Bukkit.getScheduler().runTaskAsynchronously(manhuntMain, new Runnable() {
                                @Override
                                public void run() {
                                    manhunt.getDatabase().addManhuntWin(players.getUniqueId());
                                }
                            });
                        }
                        else {
                            int wins = 0;
                            if(manhuntMain.getDataConfig().getConfig().contains("players." + players.getUniqueId().toString() + ".wins")){
                                wins = manhuntMain.getDataConfig().getConfig().getInt("players." + players.getUniqueId().toString() + ".wins");
                            }
                            manhuntMain.getDataConfig().getConfig().set("players." + players.getUniqueId().toString() + ".wins", (wins + 1));
                            manhuntMain.getDataConfig().saveConfig();
                        }


                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_GREEN + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        for (String msg : manhuntMain.getConfig().getStringList("messages.speedrunner-win-msg.speedrunners")) {
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
                    for (UUID player : deadSpeedrunners) {
                        Player players = Bukkit.getPlayer(player);

                        if(manhunt.isDatabaseEnabled()){
                            Bukkit.getScheduler().runTaskAsynchronously(manhuntMain, new Runnable() {
                                @Override
                                public void run() {
                                    manhunt.getDatabase().addManhuntWin(players.getUniqueId());
                                }
                            });                        }
                        else {
                            int wins = 0;
                            if(manhuntMain.getDataConfig().getConfig().contains("players." + players.getUniqueId().toString() + ".wins")){
                                wins = manhuntMain.getDataConfig().getConfig().getInt("players." + players.getUniqueId().toString() + ".wins");
                            }
                            manhuntMain.getDataConfig().getConfig().set("players." + players.getUniqueId().toString() + ".wins", (wins + 1));
                            manhuntMain.getDataConfig().saveConfig();
                        }


                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_GREEN + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        for (String msg : manhuntMain.getConfig().getStringList("messages.speedrunner-win-msg.speedrunners")) {
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
                    speedrunner.clear();
                    deadSpeedrunners.clear();
                    hunter.clear();
                    manhunt.getTeam(ManhuntTeam.FROZEN).clear();
                    Abilites.clearCooldown();
                    manhunt.getWaypoints().clear();
                    manhunt.setGameStatus(false);
                    manhunt.getAppliedPack().clear();
                    for (org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
                        if (team.getName().equalsIgnoreCase("hunterTeam") || team.getName().equalsIgnoreCase("speedrunnerTeam")) {
                            team.unregister();
                        }
                    }
                    Bukkit.getLogger().log(INFO, "[MANHUNT] The game has ended.");
                    return;
                }
            }
        }
    }
}
