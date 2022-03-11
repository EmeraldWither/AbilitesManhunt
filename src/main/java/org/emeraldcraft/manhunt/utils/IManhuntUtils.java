package org.emeraldcraft.manhunt.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntHunter;

import static java.util.logging.Level.INFO;

/**
 * Internal class using for certain utility functions for the plugin.
 */
public class IManhuntUtils {
    public static void debug(String msg){
        if(Manhunt.getAPI().getConfigValues().isDebugging()) Bukkit.getLogger().log(INFO, "[Manhunt Debug] " + msg);
    }
    public static Inventory constructInventory(ManhuntHunter hunter, ManhuntAbility... abilities){
        if(hunter.getAsBukkitPlayer() != null) return null;
        Inventory inventory = Bukkit.createInventory(hunter.getAsBukkitPlayer(), 36);
        return null;
    }
}
