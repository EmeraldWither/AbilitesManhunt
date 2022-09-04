package org.emeraldcraft.manhunt;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents configuration values from the config.yml as well as any constants that cannot be configured
 * <p>
 * This can be called with{@link ManhuntAPI#getConfig()}, and the object will be replaced on every refresh of the configuration.
 * <p>
 * For ability-specific config settings, check the specific ability class for getting those values.
 * Values shared across all abilities (cooldown, mana, type etc.), will be exposed by {@link org.emeraldcraft.manhunt.entities.ManhuntAbility}
 */
public class ManhuntConfigValues {
    private final boolean isDebugging;
    private final int socialDistanceRadius;
    private final String socialDistanceMessage;
    private final FileConfiguration config;

    public ManhuntConfigValues(FileConfiguration config){
        this.config = config;
        isDebugging = config.getBoolean("debug");
        socialDistanceRadius = config.getInt("hunter.social-distance");
        socialDistanceMessage = config.getString("hunter.distance-msg");
    }

    public boolean isDebugging() {
        return isDebugging;
    }
    public FileConfiguration getFileConfig(){
        return config;
    }

    public int getSocialDistanceRadius() {
        return socialDistanceRadius;
    }

    public String getSocialDistanceMessage() {
        return socialDistanceMessage;
    }
}
