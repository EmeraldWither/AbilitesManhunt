package org.emeraldcraft.manhunt.Abilties.PlayerTP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PlayerTPListener implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public PlayerTPListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, AbilitesManager abilitesManager) {
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = abilitesManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void getPlayerTPItem(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.PLAYERTP)) {

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
                inv.createInventory();
                Inventory getInventory = inv.getInv();

                player.openInventory(getInventory);
            }
        }
    }
}




