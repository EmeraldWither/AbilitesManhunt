package me.Ishaan.manhunt;

import me.Ishaan.manhunt.LauncherListener.LaunchAbility;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    //No touch touch
    //please lemme touch
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LaunchAbility(), this);
        getCommand("manhunt").setExecutor(new ManhuntCommandHandler());
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin");

    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin, we are now shutting down");
    }

}

