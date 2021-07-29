package org.emeraldcraft.manhunt.Managers;

import org.bukkit.entity.Player;

public class ManhuntPackManager {

    public void loadPack(Player player){
        player.setResourcePack("https://manhunt.resourcepacks.hunter.emeraldcraft.org");
    }
    public void unloadPack(Player player){
        player.setResourcePack("https://manhunt.resourcepacks.empty.emeraldcraft.org");
    }
}
