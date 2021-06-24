package org.emeraldcraft.manhunt.Abilties;

import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;

import java.util.HashMap;
import java.util.Map;

public class AbilitesManager {

    private ManhuntGameManager manhuntGameManager;
    public AbilitesManager(ManhuntGameManager manhuntGameManager){
        this.manhuntGameManager = manhuntGameManager;
    }
    private ManHuntInventory manhuntInventory = new ManHuntInventory();

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

    public Ability getHeldAbility(Player player) {
            if (manhuntGameManager.getGameStatus()) {
                if (manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(player.getName())) {
                    if (player.getInventory().getItemInMainHand() != null) {
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getGravity())) {
                            return Ability.GRAVITY;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getLauncher())) {
                            return Ability.LAUNCHER;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getLightning())) {
                            return Ability.LIGHTNING;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getDamageItem())) {
                            return Ability.DAMAGEITEM;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getFreezer())) {
                            return Ability.FREEZER;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getMobTargeter())) {
                            return Ability.TARGETMOB;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getPlayerTP())) {
                            return Ability.PLAYERTP;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getrandomTP())) {
                            return Ability.RANDOMTP;
                        }
                        if (player.getInventory().getItemInMainHand().isSimilar(manhuntInventory.getScrambler())) {
                            return Ability.SCRAMBLE;
                        }
                    }
                }
            }
        return Ability.NONE;
    }


    public void clearCooldown(){
        //Clears all of the cooldowns
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
