package org.emeraldcraft.manhunt.Abilties.RandomTP;

import org.bukkit.ChatColor;
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
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;

public class RandomTPListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;

    public RandomTPListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        this.abilitesManager = AbilitesManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void getClickedItem(PlayerInteractEvent event) {
        String name = event.getPlayer().getName();
        if (abilitesManager.getHeldAbility(event.getPlayer()).equals(Ability.RANDOMTP)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (manacounter.getManaList().get(name) >= 80) {
                    Player player = event.getPlayer();

                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                    inv.createInventory();
                    Inventory getInventory = inv.getInv();

                    player.openInventory(getInventory);
                    return;
                } else {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.mana-error-msg")));
                }
            }
        }
    }
}


