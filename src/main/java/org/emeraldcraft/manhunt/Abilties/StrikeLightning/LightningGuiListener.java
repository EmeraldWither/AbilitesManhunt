package org.emeraldcraft.manhunt.Abilties.StrikeLightning;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LightningGuiListener implements Listener {
    String ability = "Strike Lightning";

    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private Manhunt manhunt;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;
    Map<UUID, Long> lightningCooldown;

    public LightningGuiListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhuntMain = manhuntMain;
        this.manhunt = manhunt;
        this.manacounter = manacounter;
        this.abilites = Abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        lightningCooldown = abilites.getCooldown(Ability.LIGHTNING);
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilites.getHeldAbility(player).equals(Ability.LIGHTNING)) {
                if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLightning())) {
                    SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                    Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                    Integer cooldown = manhuntMain.getConfig().getInt("abilities.lightning.cooldown");

                    if (lightningCooldown.containsKey(player.getUniqueId())) {
                        if (lightningCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((lightningCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                            return;
                        }
                    }

                    manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 10);
                    manacounter.updateActionbar(player);


                    lightningCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));
                    selectedPlayer.getWorld().strikeLightning(selectedPlayer.getLocation());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.lightning.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                    selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.lightning.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                }
            }
        }
    }
}
