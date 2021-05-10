package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

import java.util.List;

public class PreventPickingUp implements Listener {
    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    private final Main main;
    public PreventPickingUp(Main main){
        this.main = main;
    }

    @EventHandler
    public void ItemPickupEvent(PlayerAttemptPickupItemEvent event){
        if(new ManhuntCommandHandler(main).hasGameStarted()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);

            }
        }
    }
}
