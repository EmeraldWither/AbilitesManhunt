package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;

public class PreventDroppingItems implements Listener {


    //Hunters
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void HunterDropItem(PlayerDropItemEvent event) {
        if(new ManhuntCommandHandler().hasGameStarted()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

            }
        }

    }
}
