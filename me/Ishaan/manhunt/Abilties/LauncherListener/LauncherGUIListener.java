package me.Ishaan.manhunt.Abilties.LauncherListener;

import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.util.List;

public class LauncherGUIListener implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(hunter.contains(event.getView().getPlayer().getName())) {
                Player player = (Player) event.getView().getPlayer();
                if (player.getInventory().getItemInMainHand().getItemMeta().getLore().contains(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Launches the speedrunner into the air!")) {

                    SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                    Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());

                    selectedPlayer.setVelocity(new Vector(0,5,0));
                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);

                }
            }
        }
    }

}
