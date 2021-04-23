package me.Ishaan.manhunt.Abilties.LauncherListener;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.List;

public class LaunchAbility implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

@EventHandler
    public void DetectLauncher(PlayerInteractEvent event){
    if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.FEATHER)){
        if(event.getPlayer().getInventory().getItemInMainHand().getLore().contains((ChatColor.DARK_RED + "" + ChatColor.BOLD + "Launches the speedrunner into the air!"))){
            if(hunter.contains(event.getPlayer().getName())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (speedrunner.toString() != null) {
                        Player player = Bukkit.getPlayer(speedrunner.toString().replaceAll("]","").replaceAll("\\[",""));
                        player.setVelocity(new Vector(0, 10, 0));
                    }
                }
            }
        }
    }
}

}


