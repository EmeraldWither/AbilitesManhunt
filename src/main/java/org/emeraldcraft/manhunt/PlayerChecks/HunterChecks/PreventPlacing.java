package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PreventPlacing implements Listener {

    private Manhunt manhunt;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public PreventPlacing(Manhunt manhunt) {
        this.manhunt = manhunt;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
    }

    @EventHandler
    public void PlayerPlace(BlockPlaceEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (hunter.contains(event.getPlayer().getUniqueId())) {
            event.setBuild(false);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerAllowPlace(BlockCanBuildEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        final Collection<Entity> entities = event.getBlock().getWorld().getNearbyEntities(event.getBlock().getLocation(), 1.0, 1, 1.0);
        for (final Entity entity : entities) {
            if (!(entity instanceof Player)) {
                continue;
            }
            if (hunter.contains(Objects.requireNonNull(((Player) entity).getPlayer()).getUniqueId())) {
                event.setBuildable(true);
            }
        }
    }

    @EventHandler
    public void PlayerBreak(BlockBreakEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (hunter.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            event.setDropItems(false);
            event.setExpToDrop(0);

        }
    }
}