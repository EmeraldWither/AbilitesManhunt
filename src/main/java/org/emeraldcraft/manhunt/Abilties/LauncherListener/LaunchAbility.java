package org.emeraldcraft.manhunt.Abilties.LauncherListener;

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

public class LaunchAbility implements Listener {

    private Manhunt manhunt;
    private Manacounter manacounter;
    private ManhuntMain manhuntMain;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public LaunchAbility(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;
        this.manacounter = manacounter;
        this.abilites = Abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
    }


    @EventHandler
    public void getLauncherItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (abilites.getHeldAbility(player).equals(Ability.LAUNCHER)) {
                if (speedrunner.toString() != null) {
                    if (manacounter.getManaList().get(player.getUniqueId()) >= 20) {
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
}



