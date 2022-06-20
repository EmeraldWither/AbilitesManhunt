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
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.gui.ManhuntPlayerSelector;

import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

public class AbilityOpenGUIListener implements Listener {
    private final JavaPlugin plugin;

    public AbilityOpenGUIListener(ManhuntMain plugin) {
        this.plugin = plugin;
    }

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
                if (!key.getNamespace().equalsIgnoreCase("manhuntabilites")) return;
                debug("Key is not null");
                if (ability.getUUID().toString().equalsIgnoreCase(key.getKey())) {
                    debug("""
                    Ability found (%s). Passing it off to the GUI Manager""".formatted(ability.getName()));
                    ManhuntPlayerSelector gui = new ManhuntPlayerSelector(ability, hunter);
                    Manhunt.getAPI().getGUIManager().registerManhuntGUI(gui);
                    event.getPlayer().openInventory(gui.getInventory());

                }
            }
        }

    }

}
