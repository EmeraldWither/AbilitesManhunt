package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;

public class ClearInv implements Listener {
    private ManhuntGameManager manhuntGameManager;
    List<String> speedrunner;
    List<String> hunter;

    private ManhuntMain manhuntMain;

    public ClearInv(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain) {
        this.manhuntGameManager = manhuntGameManager;
        this.speedrunner = this.manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        this.hunter = this.manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        this.manhuntMain = manhuntMain;
    }

    private final ManHuntInventory inv = new ManHuntInventory();

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        if(!manhuntGameManager.getGameStatus()) {
            if (manhuntMain.getConfig().getBoolean("clear-items-on-join")) {
                PlayerInventory playerInv = event.getPlayer().getInventory();
                Player player = event.getPlayer();
                for (ItemStack item : playerInv.getContents()) {
                    if (item != null) {
                        if (item.isSimilar(inv.getDamageItem())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getGravity())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getLauncher())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getLightning())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getrandomTP())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getPlayerTP())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getScrambler())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getMobTargeter())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void Disconnect(PlayerQuitEvent event) {
        if(!manhuntGameManager.getGameStatus()) {
            if (manhuntMain.getConfig().getBoolean("clear-items-on-leave")) {
                PlayerInventory playerInv = event.getPlayer().getInventory();
                Player player = event.getPlayer();
                for (ItemStack item : playerInv.getContents()) {
                    if (item != null) {
                        if (item.isSimilar(inv.getDamageItem())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getGravity())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getLauncher())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getLightning())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getrandomTP())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getPlayerTP())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getScrambler())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                        if (item.isSimilar(inv.getMobTargeter())) {
                            player.getInventory().remove(item);
                            removeEffects(player);
                        }
                    }
                }
            }
        }
    }

    public void removeEffects(Player player){
        player.setGlowing(false);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setInvulnerable(false);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setSaturation(5);
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        for (PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }
}



