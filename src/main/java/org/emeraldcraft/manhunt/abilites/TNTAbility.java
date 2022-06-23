package org.emeraldcraft.manhunt.abilites;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import static org.emeraldcraft.manhunt.Manhunt.getAPI;

public class TNTAbility extends ManhuntAbility {
    public TNTAbility(){
        super("Tnt Ability",
                "Spawns TNT on the speedrunner",
                getAPI().getConfig().getFileConfig().getInt("ability.tnt.cooldown"),
                getAPI().getConfig().getFileConfig().getInt("ability.tnt.mana"),
                Material.getMaterial(getAPI().getConfig().getFileConfig().getString("ability.tnt.material"))
        );
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        Player player = speedrunner.getAsBukkitPlayer();
        assert player != null;
        player.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
        player.sendMessage(
                IManhuntUtils.parseBasicMessage(
                        getAPI().getConfig().getFileConfig().getString("ability.tnt.msg"),
                        this,
                        speedrunner,
                        hunter
                )
        );
    }
}
