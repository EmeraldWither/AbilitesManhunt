package me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks;

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

    @EventHandler
    public void DragonDeath(EnderDragonChangePhaseEvent event) {
        if (event.getCurrentPhase().equals(EnderDragon.Phase.DYING)) {
            if (speedrunner.size() >= 1) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6---------------------------------------------\n" +
                        "&6| &aThe speedrunners have killed the Ender Dragon! &6|\n" +
                        "&6---------------------------------------------"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    String speedrunners = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");
                    if (speedrunners.contains(players.getName())) {
                        players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_RED + "Congrats to " + speedrunners + "!", 20, 100, 20);
                        players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                    }
                    if (hunter.contains(players.getName())) {
                        players.sendTitle(ChatColor.GREEN + "DEFEATED", ChatColor.DARK_RED + "Congrats to " + speedrunners + "!", 20, 100, 20);
                    }
                    speedrunner.clear();
                    hunter.clear();
                    players.setGlowing(false);
                    players.getInventory().clear();
                    players.setGameMode(GameMode.SURVIVAL);
                    players.setInvulnerable(false);
                    players.closeInventory();
                    players.setFlying(false);
                    return;
                }
            }
        }
    }
}
