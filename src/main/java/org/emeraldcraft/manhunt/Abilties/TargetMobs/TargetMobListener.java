package org.emeraldcraft.manhunt.Abilties.TargetMobs;

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
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class TargetMobListener implements Listener {

    private ManhuntMain manhuntMain;
    private Manhunt manhunt;
    private Manacounter manacounter;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public TargetMobListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
        this.manhunt = manhunt;
        this.abilites = Abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void getTargetMobItems(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID uuid = event.getPlayer().getUniqueId();
        if (abilites.getHeldAbility(player).equals(Ability.TARGETMOB)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (manacounter.getManaList().get(uuid) >= 100) {
                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
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

