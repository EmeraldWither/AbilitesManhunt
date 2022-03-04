package org.emeraldcraft.manhunt.entities.players;

import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Represents a basic manhunt player.
 */
public interface ManhuntPlayer{

    //TODO Create stats class and pass it here
    int getStats();

    ManhuntTeam getTeam();

    UUID getUUID();

    @Nullable
    Player getAsBukkitPlayer();
}
