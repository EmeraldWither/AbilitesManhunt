package org.emeraldcraft.manhunt.listeners.hunters;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntSpeedrunner;
import org.emeraldcraft.manhunt.gui.ManhuntPlayerSelector;

import java.util.UUID;

import static org.bukkit.Material.AIR;
import static org.emeraldcraft.manhunt.enums.ManhuntTeam.HUNTER;
import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

public class AbilityExecuteListener implements Listener {
    @EventHandler
    public void onAbilityExecute(InventoryClickEvent event) {

        Player player = (Player) event.getView().getPlayer();
        if (Manhunt.getAPI().getPlayer(player.getUniqueId()) == null) return;
        if(!(Manhunt.getAPI().getPlayer(player.getUniqueId()) instanceof ManhuntHunter hunter)) return;
        //make sure that the inventory is ours
        boolean isOurs = false;
        for (ManhuntPlayerSelector gui : Manhunt.getAPI().getGUIManager().getGUIs()) {
            if (event.getInventory() == gui.getInventory()){
                isOurs = true;
                break;
            }
        }
        if(!isOurs) return;

        //Detect the player that the hunter selected
        if(event.getCurrentItem() == null || event.getCurrentItem().getType() == AIR) return;
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
    //Check to see if the player closes the inventory
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if (Manhunt.getAPI().getPlayer(player.getUniqueId()) == null || Manhunt.getAPI().getPlayer(player.getUniqueId()).getTeam() != HUNTER) return;
        for (ManhuntPlayerSelector gui : Manhunt.getAPI().getGUIManager().getGUIs()) {
            if (event.getInventory() == gui.getInventory()) {
                Manhunt.getAPI().getGUIManager().processManhuntGUI(gui);
                return;
            }
        }
    }

}
