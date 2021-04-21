package me.Ishaan.manhunt.LauncherListener;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class LaunchAbility implements Listener {

    //WIP

    private HunterList hunterlist;
    HunterList hunters = new HunterList();
    List<String> hunter = hunters.getList();

    private SpeedrunList speedrunList;
    SpeedrunList Speedrunners = new SpeedrunList();
    List<String> speedrunner = Speedrunners.getList();

    @EventHandler
    public void playerinteract(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || equals(Action.RIGHT_CLICK_BLOCK)){
            if(hunter.contains(event.getPlayer().getName())){
                if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)){
                    if(event.getPlayer().getInventory().getItemInMainHand().getLore().contains("Strike lightning down onto the speedrunner.")){
                        Bukkit.getServer().getWorld(Bukkit.getPlayer(speedrunner.toString()).getWorld().getName()).strikeLightning(Bukkit.getPlayer(speedrunner.toString()).getLocation());

                    }
                }
            }
        }
    }

}
