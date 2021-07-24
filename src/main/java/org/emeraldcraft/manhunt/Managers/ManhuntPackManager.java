package org.emeraldcraft.manhunt.Managers;

import org.bukkit.entity.Player;

public class ManhuntPackManager {

    public void unloadPack(Player player){
        player.setResourcePack("https://www.dropbox.com/s/htpuyb8htv8wp6h/Blank_pack.zip?dl=1");
    }
    public void loadPack(Player player){
        player.setResourcePack("https://www.dropbox.com/s/3wy42xnasgfcwwi/AbilitesManhunt%20Overlay.zip?dl=1");
    }

}
