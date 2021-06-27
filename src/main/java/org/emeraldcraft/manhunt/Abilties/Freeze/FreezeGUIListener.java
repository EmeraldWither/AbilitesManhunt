package org.emeraldcraft.manhunt.Abilties.Freeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class FreezeGUIListener  implements Listener {
    private ManhuntMain manhuntMain;
    private Manacounter manacounter;

    String ability = "Freeze Player";

    private boolean freezeDelay;

    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    Map<String, Long> freezeCooldown;
    List<String> hunter;
    List<String> speedrunner;
    public FreezeGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.manhuntMain = manhuntMain;
        this.abilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
        this.freezeCooldown = AbilitesManager.getCooldown(Ability.FREEZER);
    }



    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.FREEZER)) {
                if (freezeCooldown.containsKey(player.getName())) {
                    if (freezeCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((freezeCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                assert selectedPlayer != null;

                Integer time = manhuntMain.getConfig().getInt("abilities.freeze.time");
                FreezePlayer(player, selectedPlayer, time);

                Integer cooldown = manhuntMain.getConfig().getInt("abilities.freeze.cooldown");
                freezeCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 30);
                manacounter.updateActionbar(player);


                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.freeze.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%time%", time.toString())));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }

    public void FreezePlayer(Player hunter, Player speedrunner, Integer time) {

        Integer delay = time * 20;
        speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' , manhuntMain.getConfig().getString("abilities.freeze.speedrunner-freeze-msg").replace("%hunter%", hunter.getName()).replace("%time%", Integer.toString(time))));
        manhuntGameManager.getTeam(ManhuntTeam.FROZEN).add(speedrunner.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(manhuntMain.plugin, new Runnable() {
            @Override
            public void run() {
                manhuntGameManager.getTeam(ManhuntTeam.FROZEN).remove(speedrunner.getName());
                freezeDelay = true;
                speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' , manhuntMain.getConfig().getString("abilities.freeze.speedrunner-unfreeze-msg").replace("%hunter%", hunter.getName())));
                hunter.sendMessage(ChatColor.translateAlternateColorCodes('&' , manhuntMain.getConfig().getString("abilities.freeze.unfreeze-msg").replace("%hunter%", hunter.getName()).replace("%speedrunner%", speedrunner.getName())));

                Bukkit.getScheduler().scheduleSyncDelayedTask(manhuntMain.plugin, new Runnable() {
                    @Override
                    public void run() {
                        freezeDelay = false;
                    }
                }, 20L);
            }
        }, delay); //20 Tick (1 Second) delay before run() is called

    }


    @EventHandler
    public void FreezePlayer(PlayerMoveEvent event) {
        if (manhuntGameManager.getGameStatus()) {
            if (manhuntGameManager.getTeam(ManhuntTeam.FROZEN).contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent event) {
        //This prevents the player to be kicked for flying if they are floating in midair.

        if (!(Bukkit.getServer().getAllowFlight())) {
            if (manhuntMain.getConfig().getBoolean("abilities.freeze.prevent-kicking")) {
                if (manhuntGameManager.getGameStatus()) {
                    if (freezeDelay == true) {
                        if (event.getReason().contains("flying")) {
                            event.setCancelled(true);
                            Bukkit.getServer().getLogger().log(Level.WARNING, manhuntMain.getConfig().get("plugin-prefix") + event.getPlayer().getName() + " would have been kicked for flying due to them being frozen. We have prevented this by preventing them from getting kicked. You can change this behavior in the, \"config.yml\" file.");
                        }
                    }
                }
            }
        }
    }
}
