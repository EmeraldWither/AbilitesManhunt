package me.Ishaan.manhunt.SpeedrunnerChecks;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&6---------------------------------------------------------\n" +
                    "&6| &cThe speedrunner has died. As such the hunters win the game! &6|\n" +
                    "&6---------------------------------------------------------"));
            for(Player player : Bukkit.getOnlinePlayers()){
                String hunters = hunter.toString().replaceAll("]","").replaceAll("\\[","");
                player.sendTitle(ChatColor.GREEN + "The Hunters Have Won the Game", ChatColor.DARK_RED + "Congrats to " + hunters + "!", 20, 100, 20);
            }
        }
    }

}
