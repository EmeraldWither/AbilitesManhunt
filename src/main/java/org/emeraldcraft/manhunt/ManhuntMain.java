package org.emeraldcraft.manhunt;

import org.bukkit.plugin.java.JavaPlugin;

public class ManhuntMain extends JavaPlugin {
    @Override
    public void onEnable(){
        ManhuntAPI api = new ManhuntAPI(this);

    }
    @Override
    public void onDisable(){
    }
}

