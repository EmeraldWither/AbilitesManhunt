package org.emeraldcraft.manhunt.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.abilites.LaunchAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntSpeedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

public class AbilityExecuteListener implements Listener {
    @EventHandler
    public void onAbilityExecute(PlayerInteractEvent event) {
        IManhuntUtils.debug("Called event");
        if (Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) == null) return;
        ManhuntPlayer player = Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId());
        assert player != null;
        IManhuntUtils.debug("Player is not NULL. Their team: " + player.getTeam());

        if (!(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) instanceof ManhuntSpeedrunner speedrunner)) return;
        IManhuntUtils.debug("Player is a speedrunner");
        if(!(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) instanceof ManhuntHunter hunter)) return;
        IManhuntUtils.debug("Player is a hunter");

        if (Manhunt.getAPI().getAbility("Launch Ability") == null) return;
        IManhuntUtils.debug("Ability is not null.");

        LaunchAbility ability = (LaunchAbility) Manhunt.getAPI().getAbility("Launch Ability");
        assert ability != null;
        ability.execute(hunter, speedrunner);
    }

}
