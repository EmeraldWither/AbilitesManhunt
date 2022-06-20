package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.abilites.CommandMonstersAbility;
import org.emeraldcraft.manhunt.abilites.CryAbility;
import org.emeraldcraft.manhunt.abilites.ItemDeleterAbility;
import org.emeraldcraft.manhunt.abilites.LaunchAbility;
import org.emeraldcraft.manhunt.abilites.LavaAbility;
import org.emeraldcraft.manhunt.abilites.RandomPotionAbility;
import org.emeraldcraft.manhunt.commands.ManhuntCommand;
import org.emeraldcraft.manhunt.listeners.hunters.AbilityExecuteListener;
import org.emeraldcraft.manhunt.listeners.hunters.AbilityOpenGUIListener;
import org.emeraldcraft.manhunt.listeners.hunters.PreventItemInteractionListener;
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
    }
    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new AbilityOpenGUIListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PreventItemInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new AbilityExecuteListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }
}

