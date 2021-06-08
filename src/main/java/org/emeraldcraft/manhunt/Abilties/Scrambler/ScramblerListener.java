package org.emeraldcraft.manhunt.Abilties.Scrambler;

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

public class ScramblerListener implements Listener {

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;

    public ScramblerListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.main = main;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = AbilitesManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void SetGravity(PlayerInteractEvent event) {
        String name = event.getPlayer().getName();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (abilitesManager.getHeldAbility(event.getPlayer()).equals(Ability.SCRAMBLE)) {
                if (manacounter.getManaList().get(name) >= 50) {

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
