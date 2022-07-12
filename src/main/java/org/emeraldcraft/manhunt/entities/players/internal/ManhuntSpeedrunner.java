package org.emeraldcraft.manhunt.entities.players.internal;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.ManhuntMain;
import org.emeraldcraft.manhunt.entities.Waypoint;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.GameMode.SPECTATOR;

public class ManhuntSpeedrunner implements Speedrunner {
    private final UUID uuid;
    private int coins = 0;
    private final List<Waypoint> waypoints = new ArrayList<>();
    private boolean eliminated;

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

    @Override
    public Player getAsBukkitPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public void eliminate() {
        this.eliminated = true;
        if(getAsBukkitPlayer() != null){
            Player player = getAsBukkitPlayer();
            Component deathMessage = Component.text("You have been eliminated!").color(TextColor.color(255, 0, 0));
            player.sendMessage(deathMessage);
            player.setGameMode(SPECTATOR);
            Bukkit.getScheduler().runTaskLater(JavaPlugin.getProvidingPlugin(ManhuntMain.class), () -> {
                player.spigot().respawn();
            }, 2L);
        }
    }

    @Override
    public boolean isEliminated() {
        return eliminated;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void setCoins(int amount) {
        coins = amount;
    }

    @Override
    public void addCoins(int amount) {
        coins += amount;
    }
    @Override
    public void removeCoins(int amount) {
        coins -= amount;
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
