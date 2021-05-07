package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

import java.util.List;

public class PreventPickingUp implements Listener {
    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void ItemPickupEvent(PlayerAttemptPickupItemEvent event){
        if(new ManhuntCommandHandler().hasGameStarted()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

            }
        }
    }
}
