package org.emeraldcraft.manhunt.Abilties.StrikeLightning;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

public class LightningListener implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager AbilitesManager;
    List<String> hunter;
    List<String> speedrunner;
    public LightningListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.manhuntMain = manhuntMain;
        this.AbilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }



    @EventHandler
    public void getLightningItem(PlayerInteractEvent event) {
        if(AbilitesManager.getHeldAbility(event.getPlayer()).equals(Ability.LIGHTNING)){
            String name = event.getPlayer().getName();
            if (manacounter.getManaList().get(name) >= 10) {
                Player player = event.getPlayer();

                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
                inv.createInventory();
                Inventory getInventory = inv.getInv();

                player.openInventory(getInventory);
                return;
            }
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.mana-error-msg")));
        }
    }
}