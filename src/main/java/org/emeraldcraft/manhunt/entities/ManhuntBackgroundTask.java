package org.emeraldcraft.manhunt.entities;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Represents a task that should run in the background while the gamemode is running. 
 */
public abstract class ManhuntBackgroundTask {
    private final int delay;
    private final int period;
    public ManhuntBackgroundTask(JavaPlugin plugin, int delay, int period) {
        this.delay = delay;
        this.period = period;
    }
    public abstract void run();

    public int getDelay() {
        return delay;
    }

    public int getPeriod() {
        return period;
    }
}
