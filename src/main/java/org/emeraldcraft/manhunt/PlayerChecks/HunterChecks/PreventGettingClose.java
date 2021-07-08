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
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PreventGettingClose implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain main;
    public PreventGettingClose(ManhuntGameManager manhuntGameManager, ManhuntMain main){
        this.manhuntGameManager = manhuntGameManager;
        this.main = main;
    }
    private HashMap<UUID, Long> msgcooldowns = new HashMap<>();

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (manhuntGameManager.getGameStatus())
            if (manhuntGameManager.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.HUNTER)) {
                int x = main.getConfig().getInt("hunter-range");
                Collection<Entity> entities = event.getPlayer().getNearbyEntities(x, x, x);
                for (Entity entity : entities) {
                    if (entity instanceof Player) {
                        Player player = ((Player) entity).getPlayer();
                        if (manhuntGameManager.getTeam(player.getUniqueId()).equals(ManhuntTeam.SPEEDRUNNER)) {
                            knockBack(event.getPlayer(), player.getLocation());
                            if (msgcooldowns.containsKey(event.getPlayer().getUniqueId())) {
                                if(msgcooldowns.get(event.getPlayer().getUniqueId()) > System.currentTimeMillis()) {
                                    event.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Hey!" + ChatColor.RED + " You cant get that close to the speedrunner!");
                                    msgcooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + 1000);
                                    return;
                                }
                            }
                            msgcooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + (1 * 1000));
                            return;
                        }
                    }
                }
            }
    }
    public static void knockBack(final Player player, final Location loc) {
        // player -> player to knockback
        // loc -> location to knockback the player away from.

        final Vector v = player.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(1).setY(1);
        if(player.isInsideVehicle()) {
            player.getVehicle().setVelocity(v);
            return;
        }
        if(!(v.getX() == 0 && v.getY() == 0 && v.getZ() == 0)) {
            player.setVelocity(v);
        }
    }

}
