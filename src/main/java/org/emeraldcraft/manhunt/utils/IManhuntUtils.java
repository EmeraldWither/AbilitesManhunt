package org.emeraldcraft.manhunt.utils;

import org.bukkit.Bukkit;
import org.emeraldcraft.manhunt.Manhunt;

import static java.util.logging.Level.INFO;

/**
 * Internal class using for certain utility functions for the plugin.
 */
public class IManhuntUtils {
    public static void debug(String msg){
        if(Manhunt.getAPI().getConfigValues().isDebugging()) Bukkit.getLogger().log(INFO, "[Manhunt Debug] " + msg);
    }
}
