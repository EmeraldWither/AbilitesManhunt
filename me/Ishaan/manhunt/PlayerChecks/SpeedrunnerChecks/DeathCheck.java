package me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class DeathCheck implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void SpeedrunnerDeath(PlayerDeathEvent event){
        if(speedrunner.contains(event.getEntity().getName())){
            speedrunner.remove(event.getEntity().getName());
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            event.getEntity().setGlowing(false);
            if(speedrunner.size() == 0) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6---------------------------------------------------------\n" +
                        "&6| &cThe speedrunners have died. As such the hunters win the game! &6|\n" +
                        "&6---------------------------------------------------------"));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    String hunters = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");
                    players.sendTitle(ChatColor.GREEN + "The Hunters Have Won the Game", ChatColor.DARK_RED + "Congrats to " + hunters + "!", 20, 100, 20);
                    speedrunner.clear();
                    hunter.clear();
                    players.setGlowing(false);
                    players.getInventory().clear();
                    return;
                }

            }
        }
    }

}
