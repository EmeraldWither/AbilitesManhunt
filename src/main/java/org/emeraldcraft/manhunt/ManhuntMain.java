package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.commands.ManhuntAddPlayersCommand;
import org.emeraldcraft.manhunt.listeners.AbilityExecuteListener;

public class ManhuntMain extends JavaPlugin {
    @Override
    public void onEnable(){
        this.saveDefaultConfig();
        ManhuntAPI api = new ManhuntAPI(this);
        Manhunt.setAPI(api);
        this.getCommand("manhunt").setExecutor(new ManhuntAddPlayersCommand());
        Bukkit.getPluginManager().registerEvents(new AbilityExecuteListener(), this);

    }
    @Override
    public void onDisable(){
    }
}

