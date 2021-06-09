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
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;

public class LauncherGUIListener  implements Listener {

    String ability = "Launcher";
    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;
    Map<String, Long> launcherCooldown;

    public LauncherGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntMain = manhuntMain;
        this.abilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        launcherCooldown = AbilitesManager.getCooldown(Ability.LAUNCHER);
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER); ;
    }



    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.LAUNCHER)) {
                if (launcherCooldown.containsKey(player.getName())) {
                    if (launcherCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((launcherCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
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

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 20);
                manacounter.updateActionbar(player);


                Integer cooldown = manhuntMain.getConfig().getInt("abilities.launcher.cooldown");
                launcherCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.launcher.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%velocity%", Integer.toString(velocity))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.launcher.speedrunner-msg").replace("%hunter%", player.getName()).replace("%velocity%", Integer.toString(velocity))));

                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
}
