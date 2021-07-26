package org.emeraldcraft.manhunt.Abilties;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Managers.Manhunt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Abilites {

    private Manhunt manhunt;
    public Abilites(Manhunt manhunt){
        this.manhunt = manhunt;
    }
    private ManHuntInventory manhuntInventory = new ManHuntInventory();

    private Map<UUID, Long> launcherCooldown = new HashMap<>();
    private Map<UUID, Long> freezeCooldown = new HashMap<>();
    private Map<UUID, Long> gravityCooldown = new HashMap<>();
    private Map<UUID, Long> damageCooldown = new HashMap<>();
    private Map<UUID, Long> randomTPCooldown = new HashMap<>();
    private Map <UUID, Long> scramblerCooldown = new HashMap<>();
    private Map<UUID, Long> lightningCooldown = new HashMap<>();
    private Map<UUID, Long> targetMobsCooldown = new HashMap<>();

    public Map<UUID, Long> getCooldown(Ability ability){
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

    public Ability getHeldAbility(Player player) {
            if (manhunt.hasGameStarted()) {
                if (manhunt.getTeam(ManhuntTeam.HUNTER).contains(player.getUniqueId())) {
                    if (player.getInventory().getItemInMainHand().getType() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
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
