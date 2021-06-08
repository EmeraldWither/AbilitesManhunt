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
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;

public class EnderDragonCheck implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private Main main;
    private org.emeraldcraft.manhunt.Abilties.AbilitesManager AbilitesManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    List<String> deadSpeedrunners;

    public EnderDragonCheck(ManhuntGameManager manhuntGameManager, Main main, AbilitesManager AbilitesManager, Manacounter manacounter) {
        this.main = main;
        this.AbilitesManager = AbilitesManager;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        deadSpeedrunners = manhuntGameManager.getTeam(Team.DEAD);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof EnderDragon){
            if (manhuntGameManager.getGameStatus()) {
                if (speedrunner.size() >= 1) {
                    String speedrunners = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");
                    for (String hunter : hunter) {
                        Player players = Bukkit.getPlayer(hunter);
                        players.setAllowFlight(false);
                        players.sendTitle(ChatColor.DARK_RED + "DEFEATED", ChatColor.RED + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                        for (String msg : main.getConfig().getStringList("messages.speedrunner-win-msg.hunters")) {
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
                    for (String player : speedrunner) {
                        Player players = Bukkit.getPlayer(player);
                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_GREEN + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        for (String msg : main.getConfig().getStringList("messages.speedrunner-win-msg.speedrunners")) {
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
                    for (String player : deadSpeedrunners) {
                        Player players = Bukkit.getPlayer(player);
                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_GREEN + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        for (String msg : main.getConfig().getStringList("messages.speedrunner-win-msg.speedrunners")) {
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
                    manhuntGameManager.getTeam(Team.FROZEN).clear();
                    AbilitesManager.clearCooldown();
                    manhuntGameManager.setGameStatus(false);
                    for (org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
                        if (team.getName().equalsIgnoreCase("hunterTeam") || team.getName().equalsIgnoreCase("speedrunnerTeam")) {
                            team.unregister();
                        }
                    }
                    return;
                }
            }
        }
    }
}
