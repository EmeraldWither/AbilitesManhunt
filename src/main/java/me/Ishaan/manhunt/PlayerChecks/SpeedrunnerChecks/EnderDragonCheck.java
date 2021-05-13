package me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;

import java.util.List;

public class EnderDragonCheck implements Listener {
    //Speedrunners
    List<String> speedrunner = SpeedrunList.getSpeedruners();

    //Hunters

    List<String> hunter = HunterList.getHunters();

    private final Main main;

    public EnderDragonCheck(Main main) {
        this.main = main;
    }

    @EventHandler
    public void DragonDeath(EnderDragonChangePhaseEvent event) {
        if (event.getCurrentPhase().equals(EnderDragon.Phase.DYING)) {
            if (new ManhuntCommandHandler(main).hasGameStarted()) {
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
                        DeathCheck.deadSpeedrunners.clear();
                        players.setGlowing(false);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.SURVIVAL);
                        players.setInvulnerable(false);
                        players.closeInventory();
                        players.setFlying(false);
                        players.setSaturation(5);
                    }
                    new ManhuntCommandHandler(main).setGameStarted(false);
                }
            }
        }
    }
}
