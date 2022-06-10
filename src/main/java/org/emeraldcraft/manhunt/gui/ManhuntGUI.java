package org.emeraldcraft.manhunt.gui;

import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

public class ManhuntGUI {
    private final ManhuntAbility ability;
    private final Hunter hunter;
    private Speedrunner speedrunner;

    /**
     * @param ability The ability that will execute when player is selected
     * @param hunter The hunter that originally ran it
     */
    public ManhuntGUI(ManhuntAbility ability, Hunter hunter) {
        this.ability = ability;
        this.hunter = hunter;
    }
    public ManhuntAbility getAbility() {
        return ability;
    }

    public Hunter getHunter() {
        return hunter;
    }

    /**
     * @return The speedrunner that was selected
     */
    public Speedrunner getSpeedrunner() {
        return speedrunner;
    }

}
