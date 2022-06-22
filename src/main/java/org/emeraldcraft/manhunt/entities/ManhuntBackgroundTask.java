package org.emeraldcraft.manhunt.entities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Represents a task that should run in the background while the gamemode is running. 
 */
public abstract class ManhuntBackgroundTask extends BukkitRunnable {
    private final int delay;
    private final int period;
    private final JavaPlugin plugin;
    private BukkitTask bukkitTask;
    public ManhuntBackgroundTask(JavaPlugin plugin, int delay, int period) {
        this.plugin = plugin;
        this.delay = delay;
        this.period = period;
    }
    public abstract void run();
    public void start(){
        bukkitTask = this.runTaskTimer(plugin, delay, period);
    }
    public void end(){
        if (bukkitTask != null){
            Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
            bukkitTask = null;
        }
    }
}
