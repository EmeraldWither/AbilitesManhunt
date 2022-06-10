package org.emeraldcraft.manhunt.abilites;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

import java.util.Collection;

public class CommandMobsAbility extends ManhuntAbility {
    public CommandMobsAbility() {
        super("Command Mobs", "Commands all mobs nearby", 1000, 100, Material.COMMAND_BLOCK);
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if(speedrunner.getAsBukkitPlayer() != null){
            Player player = speedrunner.getAsBukkitPlayer();
            Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(100);
            entities.removeIf(livingEntity -> !(livingEntity instanceof Monster));
            entities.forEach(livingEntity -> ((Monster) livingEntity).setTarget(player));
            player.sendMessage("You have " + entities.size() + " coming towards you!");
        }
    }
}
