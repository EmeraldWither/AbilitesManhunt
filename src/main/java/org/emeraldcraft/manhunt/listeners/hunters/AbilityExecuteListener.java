package org.emeraldcraft.manhunt.listeners.hunters;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntSpeedrunner;

import java.util.UUID;

import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

public class AbilityExecuteListener implements Listener {
    @EventHandler
    public void onAbilityExecute(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        if (Manhunt.getAPI().getPlayer(player.getUniqueId()) == null) return;
        if(!(Manhunt.getAPI().getPlayer(player.getUniqueId()) instanceof ManhuntHunter hunter)) return;
        //Detect the player that the hunter selected
        if(event.getCurrentItem() == null) return;
        ItemStack itemStack = event.getCurrentItem();
        //Pull skull meta from item
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        //Pull the UUID from the meta
        if(skullMeta.getPlayerProfile() == null){
            debug("The player profile is null");
            return;
        }
        UUID uuid = skullMeta.getPlayerProfile().getId();
        //Pull the player from the UUID
        if(Manhunt.getAPI().getPlayer(uuid) == null){
            debug("Invalid skull meta found when trying to get UUID from GUI. Skipping...");
            return;
        }
        if(!(Manhunt.getAPI().getPlayer(uuid) instanceof ManhuntSpeedrunner speedrunner)) return;
        //Fetch the current awaiting GUI
        Manhunt.getAPI().getGUIManager().getGUIs().forEach(manhuntGUI -> {
            if(manhuntGUI.getHunter().getUUID().equals(hunter.getUUID())) {
                //If the UUIDs match, execute the ability
                manhuntGUI.getAbility().execute(hunter, speedrunner);
                //Remove the GUI
                Manhunt.getAPI().getGUIManager().processManhuntGUI(manhuntGUI);
                event.getInventory().close();
            }
        });

    }
}
