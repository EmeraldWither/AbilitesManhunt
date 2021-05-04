package me.Ishaan.manhunt.Mana;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaCounter extends BukkitRunnable{

    private JavaPlugin plugin;

    public void ExampleTask(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        // What you want to schedule goes here
        plugin.getServer().broadcastMessage("Welcome to Bukkit! Remember to read the documentation!");
    }

}
