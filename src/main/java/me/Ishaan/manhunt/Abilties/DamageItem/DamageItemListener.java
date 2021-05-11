package me.Ishaan.manhunt.Abilties.DamageItem;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class DamageItemListener implements Listener {
    List<String> speedrunner;
    List<String> hunter;

    private final Main main;
    public DamageItemListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void DetectLauncher(PlayerInteractEvent event) {
        if ((new ManhuntCommandHandler(main)).hasGameStarted() && event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getGravity())){
            String name = event.getPlayer().getName();
            if ((new ManhuntCommandHandler(main)).getTeam(name).equals(Team.HUNTER) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && this.speedrunner.toString() != null) {
                Player player = event.getPlayer();
                SpeedrunnerGUI inv = new SpeedrunnerGUI();
                inv.createInventory();
                Inventory getInventory = inv.getInv();
                player.openInventory(getInventory);
            }
        }

    }
}
