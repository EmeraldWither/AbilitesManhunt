package org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

public class PushAwayHunter implements Listener {
    private Manhunt manhunt;
    private ManhuntMain main;

    public PushAwayHunter(Manhunt manhunt, ManhuntMain manhuntMain) {
        this.manhunt = manhunt;
        this.main = manhuntMain;
    }

    private HashMap<UUID, Long> msgcooldowns = new HashMap<>();

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (manhunt.hasGameStarted())
            if (manhunt.getTeam(event.getPlayer().getUniqueId()).equals(ManhuntTeam.SPEEDRUNNER)) {
                Player player = event.getPlayer();
                int x = main.getConfig().getInt("hunter-range");
                Collection<Entity> entities = event.getPlayer().getNearbyEntities(x, x, x);
                for (Entity entity : entities) {
                    if (entity instanceof Player) {
                        Player player2 = ((Player) entity).getPlayer();
                        if (manhunt.getTeam(player2.getUniqueId()).equals(ManhuntTeam.HUNTER)) {
                            knockBack(player2, event.getPlayer().getLocation());
                            if (msgcooldowns.containsKey(player2.getUniqueId())) {
                                if (msgcooldowns.get(player2.getUniqueId()) > System.currentTimeMillis()) {
                                    player2.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Hey!" + ChatColor.RESET + "" + ChatColor.RED + " You cant get that close to the speedrunner!");
                                    msgcooldowns.put(player2.getUniqueId(), System.currentTimeMillis() + 1000);
                                    return;
                                }
                            }
                            msgcooldowns.put(player2.getUniqueId(), System.currentTimeMillis());
                        }
                    }
                }
            }
    }

    public static void knockBack(final Player player, final Location loc) {
        // player -> player to knockback
        // loc -> location to knockback the player away from.

        final Vector v = player.getLocation().toVector().subtract(loc.toVector()).normalize().multiply(1).setY(1);
        if (player.isInsideVehicle()) {
            player.getVehicle().setVelocity(v);
            return;
        }
        if(!(v.getX() == 0 && v.getY() == 0 && v.getZ() == 0)) {
            try {
                player.setVelocity(v);
            }
            catch (IllegalArgumentException e){
                return;
            }
        }
    }
}

