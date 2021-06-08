package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;

public class CheckChest implements Listener{

    private ManhuntGameManager manhuntGameManager;
    private Main main;

    List<String> hunter;
    public CheckChest(ManhuntGameManager manhuntGameManager, Main main){
        this.manhuntGameManager = manhuntGameManager;
        this.main = main;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);;
    }

    @EventHandler
    public void ChestClick(InventoryClickEvent event) {
        if(hunter.contains(event.getView().getPlayer().getName())) {
            if(manhuntGameManager.getGameStatus()) {

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                Inventory getInventory = inv.getInv();

                if (event.getInventory() != getInventory) {
                    event.setCancelled(true);
                }
            }
        }

    }
    @EventHandler
    public void ChestDragEvent(InventoryDragEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getView().getPlayer().getName())) {
                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                Inventory getInventory = inv.getInv();

                if (event.getInventory() != getInventory) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void ChestMoveEvent(InventoryMoveItemEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (event.getSource().getHolder() instanceof Player) {
                if (hunter.contains(((Player) event.getSource().getHolder()).getName())) {
                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                    Inventory getInventory = inv.getInv();

                    if (event.getSource() != getInventory) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void SwitchOffHand(PlayerSwapHandItemsEvent event) {
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            }
        }
    }
}

