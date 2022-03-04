package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * The API class which provides the control of the plugin.
 *
 * The entrypoint is {@link Manhunt#getAPI()}
 */
public class ManhuntAPI {
    private final ManhuntMain main;
    private final ManhuntConfigValues configValues;

    public ManhuntAPI(ManhuntMain main){
        this.main = main;
        configValues = new ManhuntConfigValues(main.getConfig());
    }
    public void debug(String message){
        if(configValues.isDebugging()) Bukkit.getLogger().log(Level.INFO, "[MANHUNT DEBUG] " + message);
    }

    /**
     * @return The configuration values of the plugin
     */
    public ManhuntConfigValues getConfigValues(){
        return this.configValues;
    }

}
