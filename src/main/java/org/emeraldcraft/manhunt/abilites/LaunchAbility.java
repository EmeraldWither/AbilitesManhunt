package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

public class LaunchAbility extends ManhuntAbility {
    private final int velocity;
    public LaunchAbility() {
        super("Launch Ability",
                "Launches the speedrunner into the air",
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.launch.cooldown"),
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.launch.mana"),
                Material.getMaterial(Manhunt.getAPI().getConfig().getFileConfig().getString("ability.launch.material")));
        this.velocity = Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.launch.velocity");
    }
    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if(speedrunner.getAsBukkitPlayer() == null) return;

        Player player = speedrunner.getAsBukkitPlayer();
        Vector velocity = player.getVelocity();
        velocity.setY(this.velocity);
        player.setVelocity(velocity);
        player.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(),10);

        //Minimessage start parsing config
        String launchMsgStr = Manhunt.getAPI().getConfig().getFileConfig().getString("ability.launch.msg");
        if (launchMsgStr != null) {
            Component msg = IManhuntUtils.parseConfigMessage(launchMsgStr,
                    this,
                    speedrunner,
                    hunter,
                    new String[]{"%velocity%"},
                    new String[]{this.velocity + " "});
            player.sendMessage(msg);
        }
    }
}
