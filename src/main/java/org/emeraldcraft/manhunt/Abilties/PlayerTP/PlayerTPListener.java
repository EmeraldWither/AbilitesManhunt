package org.emeraldcraft.manhunt.Abilties.PlayerTP;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PlayerTPListener implements Listener {

    private ManhuntMain manhuntMain;
    private Manhunt manhunt;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public PlayerTPListener(Manhunt manhunt, ManhuntMain manhuntMain, Abilites abilites) {
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;
        this.abilites = abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void getPlayerTPItem(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (abilites.getHeldAbility(player).equals(Ability.PLAYERTP)) {

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
                inv.createInventory();
                Inventory getInventory = inv.getInv();

                player.openInventory(getInventory);
            }
        }
    }
}




