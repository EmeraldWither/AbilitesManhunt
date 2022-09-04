package org.emeraldcraft.manhunt.abilites;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;

import static org.emeraldcraft.manhunt.Manhunt.getAPI;

public class TNTAbility extends ManhuntAbility {
    public TNTAbility(){
        super("Tnt Ability",
                "Spawns TNT on the speedrunner",
                getAPI().getConfig().getFileConfig().getInt("ability.tnt.cooldown"),
                getAPI().getConfig().getFileConfig().getInt("ability.tnt.mana"),
                Material.getMaterial(getAPI().getConfig().getFileConfig().getString("ability.tnt.material")),
                "tnt"
        );
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        Player player = speedrunner.getAsBukkitPlayer();
        assert player != null;
        player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
        player.sendMessage(
                ManhuntUtils.parseConfigMessage(
                        getAttributes().getString("msg"),
                        this,
                        speedrunner,
                        hunter,
                        null,
                        null
                )
        );
        hunter.getAsBukkitPlayer().sendMessage(
                ManhuntUtils.parseConfigMessage(
                        getAttributes().getString("hunter-msg"),
                        this,
                        speedrunner,
                        hunter,
                        null,
                        null
                )
        );
    }
}
