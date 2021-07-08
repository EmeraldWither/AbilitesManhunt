package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class CheckChest implements Listener{

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;

    List<UUID> hunter;
    public CheckChest(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntGameManager = manhuntGameManager;
        this.manhuntMain = manhuntMain;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);;
    }

    @EventHandler
    public void ChestClick(InventoryClickEvent event) {
        if(hunter.contains(event.getView().getPlayer().getUniqueId())) {
            if(manhuntGameManager.getGameStatus()) {

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
                Inventory getInventory = inv.getInv();

                if (event.getInventory() != getInventory) {
                    event.setCancelled(true);
                }
                ((Player) event.getView().getBottomInventory().getHolder()).updateInventory();
            }
        }

    }
    @EventHandler
    public void ChestDragEvent(InventoryDragEvent event){
        if(event.getView().getBottomInventory().getHolder() instanceof Player) {
            if (manhuntGameManager.getGameStatus()) {
                if (hunter.contains(event.getView().getPlayer().getUniqueId())) {
                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
                    Inventory getInventory = inv.getInv();
                    if (event.getInventory() != getInventory) {
                        event.setCancelled(true);
                    }
                    ((Player) event.getView().getBottomInventory().getHolder()).updateInventory();
                }
            }
        }
    }
    @EventHandler
    public void ChestMoveEvent(InventoryMoveItemEvent event){
        if(manhuntGameManager.getGameStatus()) {
            if (event.getSource().getHolder() instanceof Player) {
                if (hunter.contains(((Player) event.getSource().getHolder()).getName())) {
                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
                    Inventory getInventory = inv.getInv();

                    if (event.getSource() != getInventory) {
                        event.setCancelled(true);
                    }
                    ((Player) event.getSource().getHolder()).updateInventory();

                }
            }
        }
    }
    @EventHandler
    public void SwitchOffHand(PlayerSwapHandItemsEvent event) {
        if(manhuntGameManager.getGameStatus()) {
            if (hunter.contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}

