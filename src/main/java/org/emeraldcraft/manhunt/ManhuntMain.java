package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.abilites.*;
import org.emeraldcraft.manhunt.background.ManaDisplayTask;
import org.emeraldcraft.manhunt.background.ManaUpdaterTask;
import org.emeraldcraft.manhunt.commands.ManhuntCommand;
import org.emeraldcraft.manhunt.listeners.hunters.AbilityExecuteListener;
import org.emeraldcraft.manhunt.listeners.hunters.AbilityOpenGUIListener;
import org.emeraldcraft.manhunt.listeners.hunters.prevent.PreventAttackListener;
import org.emeraldcraft.manhunt.listeners.hunters.prevent.PreventInventoryMoveListener;
import org.emeraldcraft.manhunt.listeners.hunters.prevent.PreventItemInteractionListener;
import org.emeraldcraft.manhunt.listeners.hunters.prevent.PreventItemPlacementListener;
import org.emeraldcraft.manhunt.listeners.speedrunners.PlayerDeathListener;

public class ManhuntMain extends JavaPlugin {
    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        ManhuntAPI api = new ManhuntAPI(this);
        Manhunt.setAPI(api);
        this.getCommand("manhunt").setExecutor(new ManhuntCommand());
        registerListeners();
        registerDefaultAbilities();
        Manhunt.getAPI().registerBackgroundTask(new ManaUpdaterTask(this));
        Manhunt.getAPI().registerBackgroundTask(new ManaDisplayTask(this));
    }
    @Override
    public void onDisable(){
        if(Manhunt.getAPI().isRunning()) Manhunt.getAPI().end();
    }
    private void registerDefaultAbilities(){
        Manhunt.getAPI().registerAbility(new LaunchAbility());
        Manhunt.getAPI().registerAbility(new LavaAbility(this));
        Manhunt.getAPI().registerAbility(new CryAbility());
        Manhunt.getAPI().registerAbility(new CommandMonstersAbility());
        Manhunt.getAPI().registerAbility(new ItemDeleterAbility());
        Manhunt.getAPI().registerAbility(new RandomPotionAbility());
        Manhunt.getAPI().registerAbility(new TNTAbility());
        Manhunt.getAPI().registerAbility(new PlayerTPAbility());
    }
    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new AbilityOpenGUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new AbilityExecuteListener(), this);
        //Speedrunner section
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        //Prevent hunter from doing X section of the game
        Bukkit.getPluginManager().registerEvents(new PreventItemInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new PreventAttackListener(), this);
        Bukkit.getPluginManager().registerEvents(new PreventInventoryMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PreventItemPlacementListener(), this);
    }
}

