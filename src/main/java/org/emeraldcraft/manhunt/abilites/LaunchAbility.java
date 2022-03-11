package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntSpeedrunner;

public class LaunchAbility extends ManhuntAbility {
    public LaunchAbility() {
        super("Launch Ability",
                "Launches the speedrunner into the air",
                180,
                180,
                Material.SLIME_BALL);
    }
    @Override
    protected void onExecute(ManhuntHunter hunter, ManhuntSpeedrunner speedrunner) {
        if(hunter.getAsBukkitPlayer() == null) return;
        Player player = hunter.getAsBukkitPlayer();
        Vector velocity = player.getVelocity();
        velocity.setY(10);
        player.setVelocity(velocity);
        Component message = Component.text("You have been launched by " + player.getName() + "!").color(TextColor.color(255, 0, 0));
        player.sendMessage(message);
    }
}
