package me.Ishaan.manhunt.HunterChecks;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CheckChest implements Listener{


    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void ChestClick(InventoryClickEvent event) {
        if(hunter.contains(event.getView().getPlayer().getName())){
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void ChestDragEvent(InventoryDragEvent event){
        if(hunter.contains(event.getView().getPlayer().getName())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void ChestMoveEvent(InventoryMoveItemEvent event){
        if(event.getSource().getHolder() instanceof Player){
            if(hunter.contains(((Player) event.getSource().getHolder()).getName())){
                event.setCancelled(true);
            }
        }
    }
}

