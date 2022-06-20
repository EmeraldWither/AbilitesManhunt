/*
 * Copyright (C) 2022-2023 Ishaan Sayal 
 *
 * Licensed under the EmeraldCraft License, Version 2.0 (the "Eshan License");
 * you may not use this file except in compliance with the Eshan License.
 * You may obtain a copy of the Eshan License at 
 *
 *      http://www.emeraldcraft.org/licenses/EmeraldCraft-LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Eshan License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Eshan License for the specific language governing permissions and 
 * limitations under the Eshan License.
 */
package org.emeraldcraft.manhunt.abilites;

import static org.emeraldcraft.manhunt.Manhunt.getAPI;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class RandomPotionAbility extends ManhuntAbility{
	private final int duration;
	private final int amplifer;
	public RandomPotionAbility() {
		super("Random Potion Effect",
				"Applies a random potion effect",
                getAPI().getConfig().getFileConfig().getInt("ability.random-potion.cooldown"),
                getAPI().getConfig().getFileConfig().getInt("ability.random-potion.mana"),
                Material.getMaterial(getAPI().getConfig().getFileConfig().getString("ability.random-potion.material")));
				duration = getAPI().getConfig().getFileConfig().getInt("ability.random-potion.duration");
				amplifer = getAPI().getConfig().getFileConfig().getInt("ability.random-potion.amplifer");
		}

	@Override
	protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
		//pick out a random potion effect
		PotionEffectType[] potionEffects = PotionEffectType.values();
		PotionEffectType effect = potionEffects[new Random().nextInt(potionEffects.length)];
		
		PotionEffect potionEffect = new PotionEffect(effect, duration, amplifer);
		speedrunner.getAsBukkitPlayer().addPotionEffect(potionEffect);
		
		//Minimessage start parsing config
        String msgStr = getAPI().getConfig().getFileConfig().getString("ability.itemdeleter.msg");
        if (msgStr == null) return;
        Component msg = MiniMessage.miniMessage().deserialize(
                IManhuntUtils.parseBasicMessage(msgStr
                                .replaceAll("%effect%", effect.getName()),
                        this,
                        speedrunner,
                        hunter)
        );
        speedrunner.getAsBukkitPlayer().sendMessage(msg);
		
	}
	

}
