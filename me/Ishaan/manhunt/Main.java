package me.Ishaan.manhunt;

import me.Ishaan.manhunt.Abilties.GravityBlocks.GravityListener;
import me.Ishaan.manhunt.HunterChecks.CheckChest;
import me.Ishaan.manhunt.HunterChecks.PreventPlacing;
import me.Ishaan.manhunt.Abilties.LauncherListener.LaunchAbility;
import me.Ishaan.manhunt.Abilties.StrikeLightning.LightningListener;
import me.Ishaan.manhunt.SpeedrunnerChecks.DeathCheck;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LaunchAbility(), this);
        getServer().getPluginManager().registerEvents(new LightningListener() ,this);
        getServer().getPluginManager().registerEvents(new GravityListener() ,this);
        getServer().getPluginManager().registerEvents(new PreventPlacing() ,this);
        getServer().getPluginManager().registerEvents(new CheckChest() ,this);
        getServer().getPluginManager().registerEvents(new DeathCheck() ,this);


        getCommand("manhunt").setExecutor(new ManhuntCommandHandler());
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin");


    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin, we are now shutting down");
    }
}


