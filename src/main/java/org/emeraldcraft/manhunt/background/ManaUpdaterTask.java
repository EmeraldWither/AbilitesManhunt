package org.emeraldcraft.manhunt.background;

import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntBackgroundTask;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;

import static org.emeraldcraft.manhunt.enums.ManhuntTeam.HUNTER;

public class ManaUpdaterTask extends ManhuntBackgroundTask {
    public ManaUpdaterTask(JavaPlugin plugin) {
        super(plugin, 0, 20);
    }
    @Override
    public void run() {
        for(ManhuntPlayer hunter : Manhunt.getAPI().getTeam(HUNTER)){
            ((ManhuntHunter) hunter).addMana();
        }
    }
}
