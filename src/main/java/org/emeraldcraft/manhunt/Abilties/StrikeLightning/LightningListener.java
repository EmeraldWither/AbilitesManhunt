package org.emeraldcraft.manhunt.Abilties.StrikeLightning;

import org.bukkit.ChatColor;
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
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class LightningListener implements Listener {

    private ManhuntMain manhuntMain;
    private Manhunt manhunt;
    private Manacounter manacounter;
    private Abilites Abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public LightningListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites){
        this.manhuntMain = manhuntMain;
        this.Abilites = Abilites;
        this.manacounter = manacounter;
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }



    @EventHandler
    public void getLightningItem(PlayerInteractEvent event) {
        if (Abilites.getHeldAbility(event.getPlayer()).equals(Ability.LIGHTNING)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                UUID uuid = event.getPlayer().getUniqueId();
                if (manacounter.getManaList().get(uuid) >= 10) {
                    Player player = event.getPlayer();

                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
                    inv.createInventory();
                    Inventory getInventory = inv.getInv();

                    player.openInventory(getInventory);
                    return;
                }
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.mana-error-msg")));
            }
        }
    }
}