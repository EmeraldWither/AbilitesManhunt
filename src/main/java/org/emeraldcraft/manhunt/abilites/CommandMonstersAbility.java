package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

import java.util.Collection;

import static org.emeraldcraft.manhunt.utils.ManhuntUtils.parseConfigMessage;

public class CommandMonstersAbility extends ManhuntAbility {
    private final int range;
    public CommandMonstersAbility() {
        super("Command Monsters",
                "Commands all mobs nearby",
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.commandmonsters.cooldown"),
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.commandmonsters.mana"),
                Material.getMaterial(Manhunt.getAPI().getConfig().getFileConfig().getString("ability.commandmonsters.material")),
                "commandmonsters");
        range = getAttributes().getInt("range");
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if(speedrunner.getAsBukkitPlayer() != null){
            Player player = speedrunner.getAsBukkitPlayer();
            Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(getRange());
            entities.removeIf(livingEntity -> !(livingEntity instanceof Monster));
            entities.forEach(livingEntity -> ((Monster) livingEntity).setTarget(player));
            //Minimessage start parsing config
            String mobsMsgStr = getAttributes().getString("msg");
            Component msg = parseConfigMessage(mobsMsgStr, this, speedrunner, hunter, new String[]{"%mobs%"}, new String[]{entities.size() + " "});
            player.sendMessage(msg);

            hunter.getAsBukkitPlayer().sendMessage(
                    parseConfigMessage(
                            getAttributes().getString("hunter-msg"),
                            this,
                            speedrunner,
                            hunter,
                            new String[]{"%mobs%"},
                            new String[]{entities.size() + " "}
                    )
            );
        }
    }
    public int getRange(){
        return this.range;
    }
}
