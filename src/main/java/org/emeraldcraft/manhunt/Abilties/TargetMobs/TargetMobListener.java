package org.emeraldcraft.manhunt.Abilties.TargetMobs;

import org.bukkit.ChatColor;
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
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class TargetMobListener implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;

    public TargetMobListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = AbilitesManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void getTargetMobItems(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String name = event.getPlayer().getName();
        if (abilitesManager.getHeldAbility(player).equals(Ability.TARGETMOB)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (manacounter.getManaList().get(name) >= 100) {
                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
                    inv.createInventory();
                    Inventory getInventory = inv.getInv();

                    player.openInventory(getInventory);

                } else {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.mana-error-msg")));
                }
            }
        }
    }
}

