package org.emeraldcraft.manhunt;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Represents configuration values from the config.yml as well as any constants that cannot be configured
 *
 * This can be called with{@link ManhuntAPI#getConfigValues()}, and the object will be replaced on every refresh of the configuration.
 */
public class ManhuntConfigValues {
    private final boolean isDebugging;
    public ManhuntConfigValues(FileConfiguration config){
        isDebugging = config.getBoolean("debug");
    }

    public boolean isDebugging() {
        return isDebugging;
    }

    public static class Constants{

    }
}
