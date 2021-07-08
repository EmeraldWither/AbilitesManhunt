package org.emeraldcraft.manhunt.Abilties.PlayerTP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.UUID;

public class PlayerTPGUIListener implements Listener {

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public PlayerTPGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, AbilitesManager abilitesManager) {
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = abilitesManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        if (abilitesManager.getHeldAbility(player).equals(Ability.PLAYERTP)) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
                SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                int height = manhuntMain.getConfig().getInt("abilities.playertp.height-above-player");

                player.teleport(selectedPlayer.getLocation().add(0, height, 0));
                player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1000, 0);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.playertp.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.playertp.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
}
