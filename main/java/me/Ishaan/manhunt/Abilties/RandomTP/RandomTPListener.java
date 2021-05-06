package me.Ishaan.manhunt.Abilties.RandomTP;

import me.Ishaan.manhunt.Enums.ManhuntTeam;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.ManhuntCommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class  RandomTPListener implements Listener {



    @EventHandler
    public void getClickedItem(PlayerInteractEvent event) {
        if(new ManhuntCommandHandler().hasGameStarted()) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_PEARL)) {
                if (event.getPlayer().getInventory().getItemInMainHand().getLore().contains((ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Teleports the speedrunner within a 50 block radius!"))) {
                    String name = event.getPlayer().getName();
                    if (new ManhuntCommandHandler().getTeam(name).equals(ManhuntTeam.HUNTER)) {
                        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

                            Player player = event.getPlayer();

                            SpeedrunnerGUI inv = new SpeedrunnerGUI();
                            inv.createInventory();
                            Inventory getInventory = inv.getInv();

                            player.openInventory(getInventory);
                        }
                    }
                }
            }
        }
    }

}
