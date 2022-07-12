package org.emeraldcraft.manhunt.shop.speedrunneritems.hunterdebuff;

import org.emeraldcraft.manhunt.entities.players.Hunter;

import java.util.HashMap;

public class HunterDebuffs {
    private final HashMap<Hunter, Long> disabledAbilities = new HashMap<>();
    public void disableAbility(Hunter hunter) {
        disabledAbilities.put(hunter, System.currentTimeMillis() + (150 * 1000));
    }
    public boolean isDisabled(Hunter hunter){
        if(disabledAbilities.containsKey(hunter)){
            if(disabledAbilities.get(hunter) > System.currentTimeMillis()){
                return true;
            }
            disabledAbilities.remove(hunter);
        }
        return false;
    }

    public void clear() {
        disabledAbilities.clear();
    }
}
