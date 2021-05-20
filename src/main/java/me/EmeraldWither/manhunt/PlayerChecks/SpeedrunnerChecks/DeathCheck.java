package me.EmeraldWither.manhunt.PlayerChecks.SpeedrunnerChecks;

import me.EmeraldWither.manhunt.Abilties.CooldownsManager;
import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.Mana.Manacounter;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class DeathCheck implements Listener {

    private Main main;
    private CooldownsManager cooldownsManager;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    List<String> deadSpeedrunners;
    public DeathCheck(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, CooldownsManager cooldownsManager){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        this.cooldownsManager = cooldownsManager;
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
                            Player players =Bukkit.getPlayer(hunter);
                            players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_RED + "Congrats to " + hunters + "!", 20, 100, 20);
                            players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                            for (String msg : main.getConfig().getStringList("messages.hunter-win-msg.hunters")) {
                                players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                            for(PotionEffect potionEffect: players.getActivePotionEffects()){
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
                        }
                        for (String player : deadSpeedrunners) {
                            Player players = Bukkit.getPlayer(player);
                            players.sendTitle(ChatColor.GREEN + "DEFEATED", ChatColor.DARK_RED + "Congrats to " + hunters + "!", 20, 100, 20);
                            deadSpeedrunners.remove(players.getName());
                            for (String msg : main.getConfig().getStringList("messages.hunter-win-msg.speedrunners")) {
                                players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                            for(PotionEffect potionEffect: players.getActivePotionEffects()){
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
                        }

                    manacounter.cancelMana();
                    speedrunner.clear();
                    deadSpeedrunners.clear();
                    hunter.clear();
                    manhuntGameManager.getTeam(Team.FROZEN).clear();
                    cooldownsManager.clearCooldown();
                    manhuntGameManager.setGameStatus(false);


                    for(org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()){
                        if(team.getName().equals("hunterTeam") || team.getName().equals("speedrunnerTeam")){
                            team.unregister();
                            return;
                        }
                    }
                    return;
                }
                event.getEntity().setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    public Boolean getDeadSpeedrunner(String name) {
        if (deadSpeedrunners.contains(name)) {
            return true;
        } else {
            return false;
        }
    }
}
