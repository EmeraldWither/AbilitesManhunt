package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.List;

public class PreventHunger implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void ItemPickupEvent(FoodLevelChangeEvent event){
        if(new ManhuntCommandHandler().hasGameStarted()) {
            String name = event.getEntity().getName();
            if (hunter.contains(name)) {
                event.setCancelled(true);

            }
        }
    }

}
