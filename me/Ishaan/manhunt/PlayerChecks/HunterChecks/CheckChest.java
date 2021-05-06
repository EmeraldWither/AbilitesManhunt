package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CheckChest implements Listener{


    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void ChestClick(InventoryClickEvent event) {
        if(hunter.contains(event.getView().getPlayer().getName())) {
            if(new ManhuntCommandHandler().hasGameStarted()) {

                SpeedrunnerGUI inv = new SpeedrunnerGUI();
                Inventory getInventory = inv.getInv();

                if (event.getInventory() != getInventory) {
                    event.setCancelled(true);
                }
            }
        }

    }
    @EventHandler
    public void ChestDragEvent(InventoryDragEvent event){
        if(new ManhuntCommandHandler().hasGameStarted()) {
            if (hunter.contains(event.getView().getPlayer().getName())) {
                SpeedrunnerGUI inv = new SpeedrunnerGUI();
                Inventory getInventory = inv.getInv();

                if (event.getInventory() != getInventory) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void ChestMoveEvent(InventoryMoveItemEvent event){
        if(new ManhuntCommandHandler().hasGameStarted()) {
            if (event.getSource().getHolder() instanceof Player) {
                if (hunter.contains(((Player) event.getSource().getHolder()).getName())) {
                    SpeedrunnerGUI inv = new SpeedrunnerGUI();
                    Inventory getInventory = inv.getInv();

                    if (event.getSource() != getInventory) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}

