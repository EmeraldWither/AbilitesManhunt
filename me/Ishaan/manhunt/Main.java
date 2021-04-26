package me.Ishaan.manhunt;

import me.Ishaan.manhunt.Abilties.GravityBlocks.GravityGUIListener;
import me.Ishaan.manhunt.Abilties.GravityBlocks.GravityListener;
import me.Ishaan.manhunt.Abilties.LauncherListener.LaunchAbility;
import me.Ishaan.manhunt.Abilties.LauncherListener.LauncherGUIListener;
import me.Ishaan.manhunt.Abilties.StrikeLightning.LightningGuiListener;
import me.Ishaan.manhunt.Abilties.StrikeLightning.LightningListener;
import me.Ishaan.manhunt.PlayerChecks.HunterChecks.CheckChest;
import me.Ishaan.manhunt.PlayerChecks.HunterChecks.PreventAttacking;
import me.Ishaan.manhunt.PlayerChecks.HunterChecks.PreventPickingUp;
import me.Ishaan.manhunt.PlayerChecks.HunterChecks.PreventPlacing;
import me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
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
        getServer().getPluginManager().registerEvents(new LightningGuiListener() ,this);
        getServer().getPluginManager().registerEvents(new LauncherGUIListener() ,this);
        getServer().getPluginManager().registerEvents(new GravityGUIListener() ,this);
        getServer().getPluginManager().registerEvents(new PreventPickingUp() ,this);
        getServer().getPluginManager().registerEvents(new PreventAttacking(),this);







        getCommand("manhunt").setExecutor(new ManhuntCommandHandler());
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin");


    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Thank you for using Emeralds ManHunt Plugin, we are now shutting down");
    }
}


