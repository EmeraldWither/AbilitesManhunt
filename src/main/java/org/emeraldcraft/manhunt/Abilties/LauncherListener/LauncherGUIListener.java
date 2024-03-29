package org.emeraldcraft.manhunt.Abilties.LauncherListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LauncherGUIListener  implements Listener {

    String ability = "Launcher";
    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private Manhunt manhunt;
    private Abilites abilites;
    List<UUID> hunter;
    List<UUID> speedrunner;
    Map<UUID, Long> launcherCooldown;

    public LauncherGUIListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhuntMain = manhuntMain;
        this.abilites = Abilites;
        this.manacounter = manacounter;
        this.manhunt = manhunt;
        launcherCooldown = Abilites.getCooldown(Ability.LAUNCHER);
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER); ;
    }



    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilites.getHeldAbility(player).equals(Ability.LAUNCHER)) {
                if (launcherCooldown.containsKey(player.getUniqueId())) {
                    if (launcherCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((launcherCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());

                int velocity = manhuntMain.getConfig().getInt("abilities.launcher.launch-velocity");
                Boolean launchUpwards = manhuntMain.getConfig().getBoolean("abilities.launcher.launch-upwards");

                if (launchUpwards.equals(false)) {
                    selectedPlayer.setVelocity(selectedPlayer.getEyeLocation().getDirection().multiply(3).add(new Vector(0, velocity, 0)));
                } else {
                    selectedPlayer.setVelocity(new Vector(0, velocity, 0));
                }

                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1000, 0);
                Bukkit.getWorld(player.getWorld().getUID()).spawnParticle(Particle.EXPLOSION_HUGE, selectedPlayer.getLocation(), 10);

                manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 20);
                manacounter.updateActionbar(player);


                Integer cooldown = manhuntMain.getConfig().getInt("abilities.launcher.cooldown");
                launcherCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.launcher.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%velocity%", Integer.toString(velocity))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.launcher.speedrunner-msg").replace("%hunter%", player.getName()).replace("%velocity%", Integer.toString(velocity))));

                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
}
