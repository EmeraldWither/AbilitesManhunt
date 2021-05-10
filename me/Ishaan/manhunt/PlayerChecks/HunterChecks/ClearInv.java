package me.Ishaan.manhunt.PlayerChecks.HunterChecks;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ClearInv implements Listener {

    private final Main main;

    public ClearInv(Main main) {
        this.main = main;

    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        ManhuntCommandHandler manhuntCommandHandler = new ManhuntCommandHandler(main);
        if (main.getConfig().getBoolean("clear-items-on-join")) {
            if (!(manhuntCommandHandler.hasGameStarted())) {
                PlayerInventory playerInv = event.getPlayer().getInventory();
                Player player = event.getPlayer();
                ManHuntInventory inv = new ManHuntInventory();
                for (ItemStack item : playerInv.getContents()) {
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

                }
            }
        }
    }
}

