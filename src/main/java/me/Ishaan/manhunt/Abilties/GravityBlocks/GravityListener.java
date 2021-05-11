package me.Ishaan.manhunt.Abilties.GravityBlocks;

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

public class GravityListener implements Listener {

    private final Main main;

    public GravityListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void SetGravity(PlayerInteractEvent event) {
        if (new ManhuntCommandHandler(main).hasGameStarted()) {
            if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getGravity())) {
                String name = event.getPlayer().getName();
                if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
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
