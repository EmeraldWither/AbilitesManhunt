package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

import java.util.Random;

public class CryAbility extends ManhuntAbility {
    public CryAbility() {
        super("Cry", "Insults the player. Sets the player to half of what they have.", 200, 50, Material.WATER_BUCKET);
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        //Make player cry
        if (speedrunner.getAsBukkitPlayer() != null) {
            Player player = speedrunner.getAsBukkitPlayer();
            //Pick a random insult and send it to the player9
            String randomInsult = (String) Manhunt.getAPI().getConfigValues().getInsults().toArray()[new Random().nextInt(Manhunt.getAPI().getConfigValues().getInsults().toArray().length)];
            Component text = Component.text(randomInsult).color(TextColor.color(255, 0, 0));
            player.sendMessage(text);

            Component cryMessage = Component.text("You are now crying in your own tears").color(TextColor.color(0, 0, 255));
            player.sendMessage(cryMessage);
            player.setRemainingAir(5);

            player.setHealth(player.getHealth()/2);
        }
    }
}
