package org.emeraldcraft.manhunt.Abilties.PlayerTP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;

public class PlayerTPListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;

    public PlayerTPListener(ManhuntGameManager manhuntGameManager, Main main, AbilitesManager abilitesManager) {
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = abilitesManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void DetectLauncher(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.PLAYERTP)) {

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                inv.createInventory();
                Inventory getInventory = inv.getInv();

                player.openInventory(getInventory);
            }
        }
    }
}




