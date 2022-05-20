package org.emeraldcraft.manhunt;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Represents configuration values from the config.yml as well as any constants that cannot be configured
 *
 * This can be called with{@link ManhuntAPI#getConfigValues()}, and the object will be replaced on every refresh of the configuration.
 */
public class ManhuntConfigValues {
    private final boolean isDebugging;
    private final List<String> insults;
    public ManhuntConfigValues(FileConfiguration config){
        isDebugging = config.getBoolean("debug");
        insults = config.getStringList("insults");
    }

    public boolean isDebugging() {
        return isDebugging;
    }

    public List<String> getInsults() {
        return insults;
    }

    public static class Constants{

    }
}
