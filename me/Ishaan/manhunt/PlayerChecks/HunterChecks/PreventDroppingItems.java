package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class PreventDroppingItems implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void HunterDropItem(PlayerDropItemEvent event) {
        if(hunter.contains(event.getPlayer().getName())) {
            event.setCancelled(true);

        }

    }
}
