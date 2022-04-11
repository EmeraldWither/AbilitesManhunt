package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.abilites.LaunchAbility;
import org.emeraldcraft.manhunt.abilites.LavaAbility;
import org.emeraldcraft.manhunt.commands.ManhuntCommand;
import org.emeraldcraft.manhunt.listeners.AbilityExecuteListener;
import org.emeraldcraft.manhunt.listeners.PreventItemInteractionListener;

public class ManhuntMain extends JavaPlugin {
    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        ManhuntAPI api = new ManhuntAPI(this);
        Manhunt.setAPI(api);
        this.getCommand("manhunt").setExecutor(new ManhuntCommand());
        Bukkit.getPluginManager().registerEvents(new AbilityExecuteListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PreventItemInteractionListener(), this);
        registerAbilities();
    }
    @Override
    public void onDisable(){
    }
    private void registerAbilities(){
        Manhunt.getAPI().registerAbility(new LaunchAbility());
        Manhunt.getAPI().registerAbility(new LavaAbility(this));
    }
}

