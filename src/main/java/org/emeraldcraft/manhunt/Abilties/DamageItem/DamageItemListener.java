package org.emeraldcraft.manhunt.Abilties.DamageItem;

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

public class DamageItemListener implements Listener {

    private ManhuntMain manhuntMain;
    private Manhunt manhunt;
    private Manacounter manacounter;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public DamageItemListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites abilites){
        this.manhunt = manhunt;
        this.abilites = abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
    }
    @EventHandler
    public void DetectDamageItem(PlayerInteractEvent event) {
        if(abilites.getHeldAbility(event.getPlayer()).equals(Ability.DAMAGEITEM)){
            UUID uuid = event.getPlayer().getUniqueId();
            if (hunter.contains(event.getPlayer().getUniqueId())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (manacounter.getManaList().get(uuid) >= 40) {
                        Player player = event.getPlayer();
                        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
                        inv.createInventory();
                        Inventory getInventory = inv.getInv();
                        player.openInventory(getInventory);
                    }
                    else{
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.mana-error-msg")));
                    }
                }
            }
        }
    }
}

