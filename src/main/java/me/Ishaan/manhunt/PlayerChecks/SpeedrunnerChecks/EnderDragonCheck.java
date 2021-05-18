package me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks;

import me.Ishaan.manhunt.Abilties.CooldownsManager;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.Mana.Manacounter;
import me.Ishaan.manhunt.ManhuntGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class EnderDragonCheck implements Listener {
    private ManhuntGameManager manhuntGameManager;
    private Main main;
    private CooldownsManager cooldownsManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    List<String> deadSpeedrunners;
    public EnderDragonCheck(ManhuntGameManager manhuntGameManager, Main main, CooldownsManager cooldownsManager, Manacounter manacounter){
        this.main = main;
        this.cooldownsManager = cooldownsManager;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        deadSpeedrunners = manhuntGameManager.getTeam(Team.DEAD);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void DragonDeath(EnderDragonChangePhaseEvent event) {
        if (event.getCurrentPhase().equals(EnderDragon.Phase.DYING)) {
            if (manhuntGameManager.getGameStatus()) {
                if (speedrunner.size() >= 1) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        String speedrunners = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");

                        if (hunter.contains(players.getName())) {
                            players.sendTitle(ChatColor.GREEN + "DEFEATED", ChatColor.DARK_RED + "Congrats to " + speedrunners + "!", 20, 100, 20);
                            for (String msg : main.getConfig().getStringList("messages.speedrunner-win-msg.hunters")) {
                                players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        }
                        if(!(hunter.contains(players.getName()))){
                            players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_RED + "Congrats to " + speedrunners + "!", 20, 100, 20);
                            players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                            for (String msg : main.getConfig().getStringList("messages.speedrunner-win-msg.speedrunners")) {
                                players.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            }
                        }
                        speedrunner.clear();
                        hunter.clear();
                        deadSpeedrunners.clear();
                        for(PotionEffect potionEffect: players.getActivePotionEffects()){
                            players.removePotionEffect(potionEffect.getType());
                        }
                        players.setGlowing(false);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.SURVIVAL);
                        players.setInvulnerable(false);
                        players.closeInventory();
                        players.setFlying(false);
                        players.setSaturation(5);
                        players.chat(ChatColor.GOLD + "GG!");
                    }
                    cooldownsManager.clearCooldown();
                    manacounter.cancelMana();
                    manhuntGameManager.setGameStatus(false);
                    for(org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()){
                        if(team.getName().equals("hunterTeam") || team.getName().equals("speedrunnerTeam")){
                            team.unregister();
                        }
                    }
                }
            }
        }
    }
}
