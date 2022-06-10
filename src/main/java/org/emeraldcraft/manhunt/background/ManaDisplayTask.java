package org.emeraldcraft.manhunt.background;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntBackgroundTask;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;

import static org.emeraldcraft.manhunt.enums.ManhuntTeam.HUNTER;

public class ManaDisplayTask extends ManhuntBackgroundTask {
    public ManaDisplayTask(JavaPlugin plugin) {
        super(plugin, 0, 5);
    }
    @Override
    public void run() {
        for(ManhuntPlayer hunter : Manhunt.getAPI().getTeam(HUNTER)){
            if(hunter.getAsBukkitPlayer() == null) continue;
            Component mana = Component.text("Mana: " + ((Hunter) hunter).getMana()).color(TextColor.color(0x00FF00));
            hunter.getAsBukkitPlayer().sendActionBar(mana);
        }
    }
}
