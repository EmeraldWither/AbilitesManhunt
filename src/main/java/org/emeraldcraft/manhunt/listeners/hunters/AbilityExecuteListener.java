package org.emeraldcraft.manhunt.listeners.hunters;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.events.hunter.HunterExecuteAbilityEvent;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

public class AbilityExecuteListener implements Listener {
    private final JavaPlugin plugin;

    public AbilityExecuteListener(ManhuntMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAbilityExecute(PlayerInteractEvent event) {
        IManhuntUtils.debug("Called event");
        if (Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) == null) return;
        ManhuntPlayer player = Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId());
        assert player != null;

        IManhuntUtils.debug("Player is not NULL. Their team: " + player.getTeam());


        if (!(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) instanceof ManhuntHunter hunter)) return;
        IManhuntUtils.debug("Player is a hunter");


        for (ManhuntAbility ability : Manhunt.getAPI().getAbilities()) {
            if(event.getItem() == null || event.getItem().getItemMeta() == null) return;

            IManhuntUtils.debug("Item is not null");
            for (NamespacedKey key : event.getItem().getItemMeta().getPersistentDataContainer().getKeys()) {
                IManhuntUtils.debug("Key is: " + key.toString());
                if (!key.getNamespace().equalsIgnoreCase("manhuntabilites")) return;
                IManhuntUtils.debug("Key is not null");
                if (ability.getUUID().toString().equalsIgnoreCase(key.getKey())) {
                    IManhuntUtils.debug("Ability found");
                    Speedrunner speedrunner = (Speedrunner) Manhunt.getAPI().getTeam(ManhuntTeam.SPEEDRUNNER).get(0);

                    HunterExecuteAbilityEvent hunterExecuteAbilityEvent = new HunterExecuteAbilityEvent(ability, hunter, speedrunner);
                    plugin.getServer().getPluginManager().callEvent(hunterExecuteAbilityEvent);
                    if (hunterExecuteAbilityEvent.isCancelled()){
                        IManhuntUtils.debug("Event was cancelled. No longer executing ability.");
                        return;
                    }

                    ability.execute(hunter, speedrunner);
                }
            }
        }

    }

}
