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

    List<String> hunter = HunterList.hunters;

    private SpeedrunList speedrunList;
    SpeedrunList Speedrunners = new SpeedrunList();
    List<String> speedrunner = Speedrunners.getList();

    @EventHandler
    public void playerinteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || equals(Action.RIGHT_CLICK_BLOCK)) {
            if (hunter.contains(event.getPlayer().getName())) {

                event.getPlayer().sendMessage("Got hunter!");

            }
        }
    }
}

