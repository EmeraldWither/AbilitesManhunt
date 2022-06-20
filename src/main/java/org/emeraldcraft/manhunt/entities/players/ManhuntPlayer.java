package org.emeraldcraft.manhunt.entities.players;

import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;

/**
 * Represents a basic manhunt player.
 */
public interface ManhuntPlayer{

    //TODO Create stats class and pass it here
    int getStats();

    ManhuntTeam getTeam();

    UUID getUUID();

    @Nullable
    /**
     * Uses the default Bukkit.getPlayer(), so it may be null
     * @return The player as a org.bukkit Player. 
     */
    Player getAsBukkitPlayer();

}
