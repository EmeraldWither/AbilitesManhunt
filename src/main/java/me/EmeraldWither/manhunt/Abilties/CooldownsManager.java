package me.EmeraldWither.manhunt.Abilties;

import me.EmeraldWither.manhunt.Enums.Ability;

import java.util.HashMap;
import java.util.Map;

public class CooldownsManager {

    private Map<String, Long> launcherCooldown = new HashMap<String, Long>();
    private Map<String, Long> freezeCooldown = new HashMap<String, Long>();
    private Map<String, Long> gravityCooldown = new HashMap<String, Long>();
    private Map<String, Long> damageCooldown = new HashMap<String, Long>();
    private Map<String, Long> randomTPCooldown = new HashMap<String, Long>();
    private Map <String, Long> scramblerCooldown = new HashMap<String, Long>();
    private Map<String, Long> lightningCooldown = new HashMap<String, Long>();
    private Map<String, Long> targetMobsCooldown = new HashMap<String, Long>();

    public Map<String, Long> getCooldown(Ability ability){
        if(ability.equals(Ability.LAUNCHER)){
            return launcherCooldown;
        }
        if(ability.equals(Ability.FREEZER)){
            return freezeCooldown;
        }
        if(ability.equals(Ability.GRAVITY)){
            return gravityCooldown;
        }
        if(ability.equals(Ability.DAMAGEITEM)){
            return damageCooldown;
        }
        if(ability.equals(Ability.RANDOMTP)){
            return randomTPCooldown;
        }
        if(ability.equals(Ability.SCRAMBLE)){
            return scramblerCooldown;
        }
        if(ability.equals(Ability.LIGHTNING)){
            return lightningCooldown;
        }
        if(ability.equals(Ability.TARGETMOB)){
            return targetMobsCooldown;
        }
        return null;
    }


    public void clearCooldown(Ability ability){
        if(ability.equals(Ability.LAUNCHER)){
            launcherCooldown.clear();
        }
        if(ability.equals(Ability.FREEZER)){
            freezeCooldown.clear();
        }
        if(ability.equals(Ability.GRAVITY)){
            gravityCooldown.clear();
        }
        if(ability.equals(Ability.DAMAGEITEM)){
            damageCooldown.clear();
        }
        if(ability.equals(Ability.RANDOMTP)){
            randomTPCooldown.clear();
        }
        if(ability.equals(Ability.SCRAMBLE)){
            scramblerCooldown.clear();
        }
        if(ability.equals(Ability.LIGHTNING)){
            lightningCooldown.clear();
        }
        if(ability.equals(Ability.TARGETMOB)){
            targetMobsCooldown.clear();
        }

    }
    public void clearCooldown(){
        launcherCooldown.clear();
        freezeCooldown.clear();
        gravityCooldown.clear();
        damageCooldown.clear();
        randomTPCooldown.clear();
        scramblerCooldown.clear();
        lightningCooldown.clear();
        targetMobsCooldown.clear();

    }
}
