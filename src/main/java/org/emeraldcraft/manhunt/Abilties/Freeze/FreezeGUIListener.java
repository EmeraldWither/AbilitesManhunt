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
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class FreezeGUIListener  implements Listener {
    private Main main;
    private Manacounter manacounter;

    String ability = "Freeze Player";

    private boolean freezeDelay;

    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    Map<String, Long> freezeCooldown;
    List<String> hunter;
    List<String> speedrunner;
    public FreezeGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.main = main;
        this.abilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
        this.freezeCooldown = AbilitesManager.getCooldown(Ability.FREEZER);
    }



    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.FREEZER)) {
                if (freezeCooldown.containsKey(player.getName())) {
                    if (freezeCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((freezeCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                assert selectedPlayer != null;

                Integer time = main.getConfig().getInt("abilities.freeze.time");
                FreezePlayer(player, selectedPlayer, time);

                Integer cooldown = main.getConfig().getInt("abilities.freeze.cooldown");
                freezeCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 30);
                manacounter.updateActionbar(player);


                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.freeze.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%time%", time.toString())));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }

    public void FreezePlayer(Player hunter, Player speedrunner, Integer time) {
        Integer delay = time * 20;
        speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.speedrunner-freeze-msg").replace("%hunter%", hunter.getName()).replace("%time%", Integer.toString(time))));
        manhuntGameManager.getTeam(Team.FROZEN).add(speedrunner.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
            @Override
            public void run() {
                freezeDelay = true;
                speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.speedrunner-unfreeze-msg").replace("%hunter%", hunter.getName())));
                hunter.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.unfreeze-msg").replace("%hunter%", hunter.getName()).replace("%speedrunner%", speedrunner.getName())));
                manhuntGameManager.getTeam(Team.FROZEN).remove(speedrunner.getName());
                Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        freezeDelay = false;
                    }
                }, 20L);
            }
        }, delay); //20 Tick (1 Second) delay before run() is called

    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        if (manhuntGameManager.getGameStatus()) {
            if (manhuntGameManager.getTeam(Team.FROZEN).contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent event) {
        if (!(Bukkit.getServer().getAllowFlight())) {
            if (main.getConfig().getBoolean("abilities.freeze.prevent-kicking")) {
                if (manhuntGameManager.getGameStatus()) {
                    if (freezeDelay == true) {
                        if (event.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
                            event.setCancelled(true);
                            Bukkit.getServer().getLogger().log(Level.WARNING, main.getConfig().get("plugin-prefix") + event.getPlayer().getName() + " would have been kicked for flying due to them being frozen. We have prevented this by preventing them from getting kicked. You can change this behavior in the, \"config.yml\" file.");
                        }
                    }
                }
            }
        }
    }
}
