package org.emeraldcraft.manhunt.listeners.hunters;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.events.hunter.HunterClickAbilityEvent;
import org.emeraldcraft.manhunt.gui.ManhuntPlayerSelector;

import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

public class AbilityOpenGUIListener implements Listener {
    @EventHandler
    public void onAbilityExecute(PlayerInteractEvent event) {
        debug("Detected player interacting interaction >> " + event.getPlayer().getName());
        if (Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) == null) return;

        ManhuntPlayer player = Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId());
        assert player != null;

        debug("Player is not NULL. Their team: " + player.getTeam());


        if (!(Manhunt.getAPI().getPlayer(event.getPlayer().getUniqueId()) instanceof ManhuntHunter hunter)) return;
        debug("Player is a hunter");


        for (ManhuntAbility ability : Manhunt.getAPI().getAbilities()) {
            if(event.getItem() == null || event.getItem().getItemMeta() == null) return;

            debug("Item is not null");
            for (NamespacedKey key : event.getItem().getItemMeta().getPersistentDataContainer().getKeys()) {
                debug("Key is: " + key.toString());
                if(!key.getNamespace().equalsIgnoreCase("manhuntabilities")) return;
                debug("Namespace is ours");
                debug("Key is: " + key.getKey() + " | UUID is: " + ability.getUUID());
                if (ability.getUUID().toString().equalsIgnoreCase(event.getItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING))) {
                    debug("""
                    Ability found (%s). Running event, then passing it off to the GUI Manager""".formatted(ability.name()));
                    HunterClickAbilityEvent event1 = new HunterClickAbilityEvent(ability, hunter);
                    Bukkit.getPluginManager().callEvent(event1);
                    if(event1.isCancelled()){
                        debug("Event was cancelled for running ability");
                        return;
                    }

                    ManhuntPlayerSelector gui = new ManhuntPlayerSelector(ability, hunter);
                    Manhunt.getAPI().getGUIManager().registerManhuntGUI(gui);
                    event.getPlayer().openInventory(gui.getInventory());
                    event.setCancelled(true);
                }
            }
        }

    }

}
