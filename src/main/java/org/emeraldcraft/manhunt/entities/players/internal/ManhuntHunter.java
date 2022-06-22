package org.emeraldcraft.manhunt.entities.players.internal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Internal Class.
 * Represents the {@link Hunter} in the plugin level, allowing for close control of this player.
 *
 * This class should not be from other plugins, due to the low-level functionality that this class provides,
 * and should only be used internally.
 */
public class ManhuntHunter implements Hunter {
    private final UUID uuid;
    private final HashMap<ManhuntAbility, Long> cooldowns = new HashMap<>();
    private int mana = 0;

    public ManhuntHunter(Player player) {
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
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void addMana(){
        this.mana++;
    }
    public void setCooldown(ManhuntAbility ability, long cooldown){
        if(cooldowns.containsKey(ability)) {
            IManhuntUtils.debug("Cooldown for " + ability.name() + " already exists, overwriting");
            cooldowns.replace(ability, cooldown);
        }
        else {
            IManhuntUtils.debug("Cooldown for " + ability.name() + " does not exist, adding");
            cooldowns.put(ability, cooldown);
        }
    }
    @Override
    public long getCooldownEndPeriod(ManhuntAbility ability) {
        if(cooldowns.get(ability) == null){
            IManhuntUtils.debug("Cooldown for " + ability.name() + " is null");
            return 0;
        }
        IManhuntUtils.debug("Cooldown for " + ability.name() + " is " + cooldowns.get(ability));
        IManhuntUtils.debug("Current time is " + System.currentTimeMillis());
        IManhuntUtils.debug("Current size of cooldowns is " + cooldowns.size());
        return cooldowns.get(ability);
    }
}
