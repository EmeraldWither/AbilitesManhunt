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
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import java.util.Collection;

public class CommandMonstersAbility extends ManhuntAbility {
    private final int range;
    public CommandMonstersAbility() {
        super("Command Monsters",
                "Commands all mobs nearby",
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.commandmonsters.cooldown"),
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.commandmonsters.mana"),
                Material.getMaterial(Manhunt.getAPI().getConfig().getFileConfig().getString("ability.commandmonsters.material")));
        range = Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.commandmonsters.range");
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if(speedrunner.getAsBukkitPlayer() != null){
            Player player = speedrunner.getAsBukkitPlayer();
            Collection<LivingEntity> entities = player.getLocation().getNearbyLivingEntities(getRange());
            entities.removeIf(livingEntity -> !(livingEntity instanceof Monster));
            entities.forEach(livingEntity -> ((Monster) livingEntity).setTarget(player));
            //Minimessage start parsing config
            String mobsMsgStr = Manhunt.getAPI().getConfig().getFileConfig().getString("ability.commandmonsters.msg");
            if (mobsMsgStr == null) return;

            //Parse into minimessage
            Component msg = IManhuntUtils.parseConfigMessage(mobsMsgStr,
                    this,
                    speedrunner,
                    hunter,
                    new String[]{"%mobs%"},
                    new String[]{entities.size() + " "});
            player.sendMessage(msg);
        }
    }
    public int getRange(){
        return this.range;
    }
}
