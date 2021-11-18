package org.emeraldcraft.manhunt.Abilties.RandomTP;

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

public class RandomTPListener implements Listener {

    private ManhuntMain manhuntMain;
    private Manhunt manhunt;
    private Manacounter manacounter;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public RandomTPListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;
        this.manacounter = manacounter;
        this.abilites = Abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }

    @EventHandler
    public void getRandomTPItem(PlayerInteractEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (abilites.getHeldAbility(event.getPlayer()).equals(Ability.RANDOMTP)) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (manacounter.getManaList().get(uuid) >= 80) {
                    Player player = event.getPlayer();

                    SpeedrunnerGUI inv = new SpeedrunnerGUI(manhunt);
                    inv.createInventory();
                    Inventory getInventory = inv.getInv();

                    player.openInventory(getInventory);
                    return;
                } else {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.mana-error-msg")));
                }
            }
        }
    }
}


