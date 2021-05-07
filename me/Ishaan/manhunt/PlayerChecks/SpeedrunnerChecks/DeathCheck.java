package me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class DeathCheck implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    private final List<String> deadSpeedrunners = new ArrayList<String>();

    @EventHandler
    public void SpeedrunnerDeath(PlayerDeathEvent event) {
        if (new ManhuntCommandHandler().hasGameStarted()) {
            if (speedrunner.contains(event.getEntity().getName())) {
                speedrunner.remove(event.getEntity().getName());
                deadSpeedrunners.add(event.getEntity().getName());
                event.getEntity().setAllowFlight(true);
                event.getEntity().setFlying(true);
                event.getEntity().setGlowing(false);
                if (speedrunner.size() == 0) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6---------------------------------------------------------\n" +
                            "&6| &cThe speedrunners have died. As such the hunters win the game! &6|\n" +
                            "&6---------------------------------------------------------"));
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        String hunters = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");
                        if (hunter.contains(players.getName())) {
                            players.sendTitle(ChatColor.GREEN + "VICTORY", ChatColor.DARK_RED + "Congrats to " + hunters + "!", 20, 100, 20);
                            players.playSound(players.getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 0, 100);
                            hunter.remove(players.getName());
                        }
                        if(deadSpeedrunners.contains(players.getName())){
                            players.sendTitle(ChatColor.GREEN + "DEFEATED", ChatColor.DARK_RED + "Congrats to " + hunters + "!", 20, 100, 20);
                            deadSpeedrunners.remove(players.getName());
                        }
                        players.setGlowing(false);
                        players.getInventory().clear();
                        players.setGameMode(GameMode.SURVIVAL);
                        players.setInvulnerable(false);
                        players.closeInventory();
                        players.setFlying(false);
                        players.setAllowFlight(false);
                    }
                    new ManhuntCommandHandler().setGameStarted(false);

                }
            }
        }
    }

    public Boolean getDeadSpeedrunner(String name){
        if(deadSpeedrunners.contains(name)){
            return true;
        }
        return false;
    }

}
