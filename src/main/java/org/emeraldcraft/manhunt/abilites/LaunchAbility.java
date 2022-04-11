package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

public class LaunchAbility extends ManhuntAbility {
    public LaunchAbility() {
        super("Launch Ability",
                "Launches the speedrunner into the air",
                180,
                50,
                Material.SLIME_BALL);
    }
    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if(speedrunner.getAsBukkitPlayer() == null) return;

        Player player = speedrunner.getAsBukkitPlayer();
        Vector velocity = player.getVelocity();
        velocity.setY(10);
        player.setVelocity(velocity);
        player.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(),10);
        Component message = Component.text("You have been launched by " + player.getName() + "!").color(TextColor.color(255, 0, 0));
        player.sendMessage(message);
    }
}
