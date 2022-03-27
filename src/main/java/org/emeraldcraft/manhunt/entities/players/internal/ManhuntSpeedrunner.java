package org.emeraldcraft.manhunt.entities.players.internal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.Waypoint;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManhuntSpeedrunner implements Speedrunner {
    private final UUID uuid;
    private final List<Waypoint> waypoints = new ArrayList<>();
    public ManhuntSpeedrunner(Player player){
        if (player == null || !player.isOnline())
            throw new IllegalArgumentException("Bukkit player is invalid (null or offline)");
        uuid = player.getUniqueId();
    }
    @Override
    public int getStats() {
        return 0;
    }

    @Override
    public ManhuntTeam getTeam() {
        return ManhuntTeam.SPEEDRUNNER;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Nullable
    @Override
    public Player getAsBukkitPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public void eliminate() {

    }

    @Override
    @Nullable
    public Waypoint getWaypoint(String name) {
        for(Waypoint waypoint : this.waypoints){
            if(waypoint.getName().equalsIgnoreCase(name)) return waypoint;
        }
        return null;
    }

    @Override
    public List<Waypoint> getWaypoints() {
        return new ArrayList<>(waypoints);
    }

    @Override
    public void addWaypoint(Waypoint waypoint) {
        this.waypoints.add(waypoint);
    }
}
