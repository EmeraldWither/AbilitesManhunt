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
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

public class FreezeGUIListener  implements Listener {
    private ManhuntMain manhuntMain;
    private Manacounter manacounter;

    String ability = "Freeze Player";

    int showFrozenID = 0;

    private boolean freezeDelay;
    private Manhunt manhunt;
    private Abilites abilites;
    Map<UUID, Long> freezeCooldown;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public FreezeGUIListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites){
        this.manhuntMain = manhuntMain;
        this.abilites = Abilites;
        this.manacounter = manacounter;
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);;
        this.freezeCooldown = Abilites.getCooldown(Ability.FREEZER);
    }



    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilites.getHeldAbility(player).equals(Ability.FREEZER)) {
                if (freezeCooldown.containsKey(player.getUniqueId())) {
                    if (freezeCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((freezeCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                assert selectedPlayer != null;

                Integer time = manhuntMain.getConfig().getInt("abilities.freeze.time");
                FreezePlayer(player, selectedPlayer, time);

                Integer cooldown = manhuntMain.getConfig().getInt("abilities.freeze.cooldown");
                freezeCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));

                manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 30);
                manacounter.updateActionbar(player);


                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.freeze.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%time%", time.toString())));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }

    public void FreezePlayer(Player hunter, Player speedrunner, Integer time) {

        Integer delay = time * 20;
        speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' , manhuntMain.getConfig().getString("abilities.freeze.speedrunner-freeze-msg").replace("%hunter%", hunter.getName()).replace("%time%", Integer.toString(time))));
        manhunt.getTeam(ManhuntTeam.FROZEN).add(speedrunner.getUniqueId());
        //speedrunner.sendMessage("MANHUNT [DEBUG] Max freeze ticks: " + speedrunner.getMaxFreezeTicks());

        showFreezeEffect(speedrunner);
        Bukkit.getScheduler().scheduleSyncDelayedTask(manhuntMain, new Runnable() {
            @Override
            public void run() {
                stopShowFreezeEffect(speedrunner);
                manhunt.getTeam(ManhuntTeam.FROZEN).remove(speedrunner.getUniqueId());
                speedrunner.setFreezeTicks(1);
                freezeDelay = true;
                speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' , manhuntMain.getConfig().getString("abilities.freeze.speedrunner-unfreeze-msg").replace("%hunter%", hunter.getName())));
                hunter.sendMessage(ChatColor.translateAlternateColorCodes('&' , manhuntMain.getConfig().getString("abilities.freeze.unfreeze-msg").replace("%hunter%", hunter.getName()).replace("%speedrunner%", speedrunner.getName())));
                Bukkit.getScheduler().scheduleSyncDelayedTask(manhuntMain, new Runnable() {
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
        if (manhunt.hasGameStarted()) {
            if (manhunt.getTeam(ManhuntTeam.FROZEN).contains(event.getPlayer().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent event) {
        //This prevents the player to be kicked for flying if they are floating in midair.
        if (!(Bukkit.getServer().getAllowFlight())) {
            if (manhuntMain.getConfig().getBoolean("abilities.freeze.prevent-kicking")) {
                if (manhunt.hasGameStarted()) {
                    if (freezeDelay) {
                        if (event.getCause() == PlayerKickEvent.Cause.FLYING_PLAYER || event.getCause() == PlayerKickEvent.Cause.FLYING_VEHICLE) {
                            event.setCancelled(true);
                            Bukkit.getServer().getLogger().log(Level.WARNING, manhuntMain.getConfig().get("plugin-prefix") + event.getPlayer().getName() + " would have been kicked for flying due to them being frozen. We have prevented this by preventing them from getting kicked. You can change this behavior in the, \"config.yml\" file.");
                        }
                    }
                }
            }
        }
    }
    public void showFreezeEffect(Player player){
        showFrozenID = Bukkit.getScheduler().scheduleSyncRepeatingTask(manhuntMain, new Runnable() {
            @Override
            public void run() {
                player.setFreezeTicks(139);
            }
        },0, 1);
    }
    public void stopShowFreezeEffect(Player player){
        player.setFreezeTicks(0);
        Bukkit.getScheduler().cancelTask(showFrozenID);
    }
}
