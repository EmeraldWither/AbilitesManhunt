package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;

import java.util.Random;

import static org.emeraldcraft.manhunt.Manhunt.getAPI;

public class RandomPotionAbility extends ManhuntAbility{
	private final int duration;
	private final int amplifer;
	public RandomPotionAbility() {
		super("Random Potion Effect",
				"Applies a random potion effect",
                getAPI().getConfig().getFileConfig().getInt("ability.random-potion.cooldown"),
                getAPI().getConfig().getFileConfig().getInt("ability.random-potion.mana"),
                Material.getMaterial(getAPI().getConfig().getFileConfig().getString("ability.random-potion.material")),
				"random-potion");
				duration = getAttributes().getInt("duration");
				amplifer = getAttributes().getInt("amplifier");
		}

	@Override
	protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
		//pick out a random potion effect
		PotionEffectType[] potionEffects = PotionEffectType.values();
		PotionEffectType effect = potionEffects[new Random().nextInt(potionEffects.length)];
		
		PotionEffect potionEffect = new PotionEffect(effect, duration * 20, amplifer);
		speedrunner.getAsBukkitPlayer().addPotionEffect(potionEffect);
		
		//Minimessage start parsing config
        String msgStr = getAttributes().getString("msg");
        if (msgStr == null) return;
		Component msg = ManhuntUtils.parseConfigMessage(msgStr,
				this,
				speedrunner,
				hunter,
				new String[]{"%effect%"},
				new String[]{effect.getName()}
		);
        speedrunner.getAsBukkitPlayer().sendMessage(msg);
		hunter.getAsBukkitPlayer().sendMessage(
				ManhuntUtils.parseConfigMessage(
						getAttributes().getString("hunter-msg"),
						this,
						speedrunner,
						hunter,
						new String[]{"%effect%"},
						new String[]{effect.getName()}
				)
		);
		
	}
	

}
