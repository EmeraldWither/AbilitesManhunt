package org.emeraldcraft.manhunt.entities.players;

import org.emeraldcraft.manhunt.entities.ManhuntAbility;

/**
 * Represents a manhunt hunter.
 */
public interface Hunter extends ManhuntPlayer {
    int getMana();
    void setMana(int mana);
    void setCooldown(ManhuntAbility ability, long cooldown);

    /**
     * Used for calculating cool-downs
     * @param ability The ability you wish to check
     * @return The last time that this ability was executed
     */
    long getCooldownEndPeriod(ManhuntAbility ability);


}
