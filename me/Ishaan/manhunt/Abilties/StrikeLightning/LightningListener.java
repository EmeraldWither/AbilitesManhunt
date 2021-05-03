package me.Ishaan.manhunt.Abilties.StrikeLightning;

import me.Ishaan.manhunt.Enums.ManhuntTeam;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class LightningListener implements Listener {
    //WIP

    //Speedrunners
    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void StrikeLightning(PlayerInteractEvent event) {
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            String name = event.getPlayer().getName();
            if (new ManhuntCommandHandler().getTeam(name).equals(ManhuntTeam.HUNTER)) {
                if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.STICK)){
                    if(event.getPlayer().getInventory().getItemInMainHand().getLore().contains(ChatColor.DARK_GRAY  + "" + ChatColor.BOLD + "Strike lightning down onto the speedrunner.")){
                        Player player  = event.getPlayer();

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
