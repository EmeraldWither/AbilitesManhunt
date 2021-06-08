package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeathCheck implements Listener {

    private Main main;
    private AbilitesManager AbilitesManager;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    List<String> deadSpeedrunners;
    Map<String, Location> deathLoc = new HashMap<>();
    public DeathCheck(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        this.AbilitesManager = AbilitesManager;
        deadSpeedrunners = manhuntGameManager.getTeam(Team.DEAD);
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void SpeedrunnerDeath(PlayerDeathEvent event) {
        if (manhuntGameManager.getGameStatus()) {
            if (speedrunner.contains(event.getEntity().getName())) {
                speedrunner.remove(event.getEntity().getName());
                deadSpeedrunners.add(event.getEntity().getName());
                event.getEntity().setAllowFlight(true);
                event.getEntity().setFlying(true);
                event.getEntity().setGlowing(false);
                event.setShouldDropExperience(false);
                if (speedrunner.size() == 0) {
                    String hunters = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");
                    for (String hunter : hunter) {
                        Player players = Bukkit.getPlayer(hunter);
                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_GREEN + "Congrats to " + hunters + "!", 20, 100, 20);
                        players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                        for (String msg : main.getConfig().getStringList("messages.hunter-win-msg.hunters")) {
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
                        players.sendTitle(ChatColor.DARK_RED + "DEFEATED", ChatColor.RED + "Congrats to " + hunters + "!", 20, 100, 20);
                        for (String msg : main.getConfig().getStringList("messages.hunter-win-msg.speedrunners")) {
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
                Location deathLocation = event.getEntity().getLocation();
                Player player = event.getEntity();
                manhuntGameManager.deadSpeedrunnerTeam.addEntry(player.getName());
                player.setScoreboard(manhuntGameManager.deadSpeedrunnerTeam.getScoreboard());
                Bukkit.getScheduler().runTaskLater(main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.spigot().respawn();
                    }
                }, 1L);
                player.setGameMode(GameMode.SPECTATOR);
                Bukkit.getScheduler().runTaskLater(main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        player.teleport(deathLocation);
                    }
                }, 1L);
                player.sendTitle(ChatColor.DARK_RED + "YOU DIED", ChatColor.DARK_RED + "Better luck next time.", 20, 100, 20);
            }
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event){
            if(event.getEntity() instanceof Player){
                if(manhuntGameManager.getTeam(Team.SPEEDRUNNER).contains(((Player) event.getEntity()).getPlayer().getName())){
                    if(event.getEntity().isDead()){
                        deathLoc.put(((Player) event.getEntity()).getPlayer().getName(), event.getEntity().getLocation());
                    }
            }
        }
    }
}
