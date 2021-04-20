package me.Ishaan.manhunt;

import me.Ishaan.manhunt.LauncherListener.LaunchAbility;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    //No touch touch

    @Override
    public void onEnable() {
        getCommand("manhunt").setExecutor(new ManhuntCommandHandler());
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin");
        getServer().getPluginManager().registerEvents(new LaunchAbility(), this);


    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin, SHUTTING OFF NOW");
    }

}

