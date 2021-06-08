package org.emeraldcraft.manhunt.Abilties.StrikeLightning;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;
import java.util.Map;

public class LightningGuiListener implements Listener {
    String ability = "Strike Lightning";

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;
    Map<String, Long> lightningCooldown;

    public LightningGuiListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        this.abilitesManager = AbilitesManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        lightningCooldown = abilitesManager.getCooldown(Ability.LIGHTNING);
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.LIGHTNING)) {
                if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLightning())) {
                    SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                    Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                    Integer cooldown = main.getConfig().getInt("abilities.lightning.cooldown");

                    if (lightningCooldown.containsKey(player.getName())) {
                        if (lightningCooldown.get(player.getName()) > System.currentTimeMillis()) {
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((lightningCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                            return;
                        }
                    }

                    manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 10);
                    manacounter.updateActionbar(player);


                    lightningCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                    selectedPlayer.getWorld().strikeLightning(selectedPlayer.getLocation());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.lightning.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                    selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.lightning.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                }
            }
        }
    }
}
