package org.emeraldcraft.manhunt.gui;

import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

public class ManhuntGUI {
    private final ManhuntAbility ability;
    private final Hunter hunter;
    private Speedrunner speedrunner;
    private Inventory inventory;

    /**
     * @param ability The ability that will execute when player is selected
     * @param hunter The hunter that originally ran it
     */
    public ManhuntGUI(ManhuntAbility ability, Hunter hunter) {
        this.ability = ability;
        this.hunter = hunter;
        this.inventory = IManhuntUtils.createPlayerSelector();
    }
    public ManhuntAbility getAbility() {
        return ability;
    }

    public Hunter getHunter() {
        return hunter;
    }

    public Inventory getInventory(){
        return inventory;
    }
    public void createNewInventory(){
        inventory = IManhuntUtils.createPlayerSelector();
    }
    /**
     * @return The speedrunner that was selected
     */
    public Speedrunner getSpeedrunner() {
        return speedrunner;
    }

}
