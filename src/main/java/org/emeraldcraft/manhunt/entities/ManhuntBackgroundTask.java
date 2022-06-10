package org.emeraldcraft.manhunt.entities;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public abstract class ManhuntBackgroundTask extends BukkitRunnable {
    private final int delay;
    private final int period;
    private JavaPlugin plugin;
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
            bukkitTask.cancel();
            bukkitTask = null;
        }
    }
}
