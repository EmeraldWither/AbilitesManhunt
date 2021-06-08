package org.emeraldcraft.manhunt.Abilties.DamageItem;

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

public class DamageItemListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;
    public DamageItemListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager abilitesManager){
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = abilitesManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        this.main = main;
        this.manacounter = manacounter;
    }
    @EventHandler
    public void DetectDamageItem(PlayerInteractEvent event) {
        if(abilitesManager.getHeldAbility(event.getPlayer()).equals(Ability.DAMAGEITEM)){
            String name = event.getPlayer().getName();
            if (hunter.contains(event.getPlayer().getName())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (manacounter.getManaList().get(name) >= 40) {
                        Player player = event.getPlayer();
                        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                        inv.createInventory();
                        Inventory getInventory = inv.getInv();
                        player.openInventory(getInventory);
                    }
                    else{
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.mana-error-msg")));
                    }
                }
            }
        }
    }
}

