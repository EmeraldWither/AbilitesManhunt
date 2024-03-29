package org.emeraldcraft.manhunt.PlayerChecks.HunterChecks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class SocialDistance implements Listener {

    private final Manhunt manhunt;
    private final ManhuntMain main;
    private final HashMap<UUID, Long> msgcooldowns = new HashMap<>();
    public SocialDistance(Manhunt manhunt, ManhuntMain main){
        this.manhunt = manhunt;
        this.main = main;
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        if (!manhunt.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
            return;
        }
        int x = main.getConfig().getInt("hunter-range");
        Collection<Entity> entities = event.getPlayer().getNearbyEntities(x, x, x);
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                Player player = ((Player) entity).getPlayer();
                if (manhunt.getTeam(player.getUniqueId()).equals(ManhuntTeam.SPEEDRUNNER)) {
                    knockBack(event.getPlayer(), player.getLocation());
                    if (msgcooldowns.containsKey(event.getPlayer().getUniqueId())) {
                        if(msgcooldowns.get(event.getPlayer().getUniqueId()) > System.currentTimeMillis()) {
                            event.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Hey!" + ChatColor.RED + " You cant get that close to the speedrunner!");
                            msgcooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + 1000);
                            return;
                        }
                    }
                    msgcooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + 1000);
                    return;
                }
            }
        }
    }
    private void knockBack(final Player player, final Location loc) {
        // player -> player to knockback
        // loc -> location to knockback the player away from.

        final Vector v = player.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(1).setY(1);
        if(player.isInsideVehicle()) {
            assert player.getVehicle() != null;
            player.getVehicle().setVelocity(v);
            return;
        }
        if(!(v.getX() == 0 && v.getY() == 0 && v.getZ() == 0)) {
            player.setVelocity(v);
        }
    }

}
