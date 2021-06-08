package org.emeraldcraft.manhunt.Abilties.StrikeLightning;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

public class LightningListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager AbilitesManager;
    List<String> hunter;
    List<String> speedrunner;
    public LightningListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.main = main;
        this.AbilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }



    @EventHandler
    public void StrikeLightning(PlayerInteractEvent event) {
        if(AbilitesManager.getHeldAbility(event.getPlayer()).equals(Ability.LIGHTNING)){
            String name = event.getPlayer().getName();
            if (manacounter.getManaList().get(name) >= 10) {
                Player player = event.getPlayer();

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                inv.createInventory();
                Inventory getInventory = inv.getInv();

                player.openInventory(getInventory);
                return;
            }
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.mana-error-msg")));
        }
    }
}