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
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CheckChest implements Listener{

    private Manhunt manhunt;
    private ManhuntMain manhuntMain;

    List<UUID> hunter;
    public CheckChest(Manhunt manhunt, ManhuntMain manhuntMain){
        this.manhunt = manhunt;
        this.manhuntMain = manhuntMain;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
    }

    @EventHandler
    public void ChestClick(InventoryClickEvent event) {
        if (!hunter.contains(event.getView().getPlayer().getUniqueId())) {
            return;
        }
        if (!manhunt.hasGameStarted()) {
            return;
        }

        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
        Inventory getInventory = inv.getInv();

        if (event.getInventory() != getInventory) {
            event.setCancelled(true);
        }
        ((Player) Objects.requireNonNull(event.getView().getBottomInventory().getHolder())).updateInventory();

    }
    @EventHandler
    public void ChestDragEvent(InventoryDragEvent event){
        if (!(event.getView().getBottomInventory().getHolder() instanceof Player)) {
            return;
        }
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!hunter.contains(event.getView().getPlayer().getUniqueId())) {
            return;
        }
        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
        Inventory getInventory = inv.getInv();
        if (event.getInventory() != getInventory) {
            event.setCancelled(true);
        }
        ((Player) event.getView().getBottomInventory().getHolder()).updateInventory();
    }
    @EventHandler
    public void ChestMoveEvent(InventoryMoveItemEvent event){
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!(event.getSource().getHolder() instanceof Player)) {
            return;
        }
        if (!hunter.contains(((Player) event.getSource().getHolder()).getUniqueId())) {
            return;
        }
        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
        Inventory getInventory = inv.getInv();

        if (event.getSource() != getInventory) {
            event.setCancelled(true);
        }
        ((Player) Objects.requireNonNull(event.getSource().getHolder())).updateInventory();

    }
    @EventHandler
    public void SwitchOffHand(PlayerSwapHandItemsEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!hunter.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
}

