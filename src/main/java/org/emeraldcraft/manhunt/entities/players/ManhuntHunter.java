package org.emeraldcraft.manhunt.entities.players;

import org.emeraldcraft.manhunt.entities.ManhuntAbility;

/**
 * Represents a manhunt hunter.
 */
public interface ManhuntHunter extends ManhuntPlayer {
    /**
     * Executes an ability considering the mana, and cooldown costs.
     * @param ability The ability you want to execute
     */
    void executeAbility(ManhuntAbility ability);
    int getMana();


}
