package org.emeraldcraft.manhunt.entities.players.internal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntHunter;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Internal Class.
 * Represents the {@link ManhuntHunter} in the plugin level, allowing for close control of this player.
 *
 * This class should not be from other plugins, due to the low-level functionality that this class provides,
 * and should only be used internally.
 */
public class IManhuntHunter implements ManhuntHunter {
    private final UUID uuid;
    private int mana = 0;

    public IManhuntHunter(Player player) {
        if (player == null || !player.isOnline())
            throw new IllegalArgumentException("Bukkit player is invalid (null or offline)");
        this.uuid = player.getUniqueId();
    }

    @Override
    public int getStats() {
        return 0;
    }

    @Override
    public ManhuntTeam getTeam() {
        return ManhuntTeam.HUNTER;
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
    public void executeAbility(ManhuntAbility ability) {


    }
    @Override
    public int getMana() {
        return 0;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void addMana(){
        this.mana++;
    }

    @Override
    public long getCooldownEndPeriod(ManhuntAbility ability) {
        return 0;
    }
}
