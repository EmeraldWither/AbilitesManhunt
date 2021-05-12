package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import org.bukkit.GameMode;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ClearInv implements Listener {

    private Main main;

    public ClearInv(Main main) {
        this.main = main;

    }

    ManHuntInventory inv = new ManHuntInventory();
    ManhuntCommandHandler manhuntCommandHandler = new ManhuntCommandHandler(this.main);

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        if(!(manhuntCommandHandler.hasGameStarted())) {
            if (manhuntCommandHandler.getTeam(event.getPlayer().getName()).equals(Team.NONE)) {
                if (main.getConfig().getBoolean("clear-items-on-join")) {
                    PlayerInventory playerInv = event.getPlayer().getInventory();
                    Player player = event.getPlayer();
                    for (ItemStack item : playerInv.getContents()) {
                        if (item != null) {
                            if (item.isSimilar(inv.getDamageItem())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getGravity())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getLauncher())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getLightning())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getrandomTP())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getPlayerTP())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getScrambler())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getMobTargeter())) {
                                player.getInventory().remove(item);
                            }
                            player.setGlowing(false);
                            player.getInventory().clear();
                            player.setGameMode(GameMode.SURVIVAL);
                            player.setInvulnerable(false);
                            player.closeInventory();
                            player.setFlying(false);
                            player.setAllowFlight(false);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void Disconnect(PlayerQuitEvent event) {
        if (!(manhuntCommandHandler.hasGameStarted())) {
            if (manhuntCommandHandler.getTeam(event.getPlayer().getName()) == null) {
                if (main.getConfig().getBoolean("clear-items-on-leave")) {
                    PlayerInventory playerInv = event.getPlayer().getInventory();
                    Player player = event.getPlayer();
                    for (ItemStack item : playerInv.getContents()) {
                        if (item != null) {
                            if (item.isSimilar(inv.getDamageItem())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getGravity())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getLauncher())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getLightning())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getrandomTP())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getPlayerTP())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getScrambler())) {
                                player.getInventory().remove(item);
                            }
                            if (item.isSimilar(inv.getMobTargeter())) {
                                player.getInventory().remove(item);
                            }
                        }
                    }

                    player.setGlowing(false);
                    player.getInventory().clear();
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setInvulnerable(false);
                    player.closeInventory();
                    player.setFlying(false);
                    player.setAllowFlight(false);


                }
            }
        }
    }
}


