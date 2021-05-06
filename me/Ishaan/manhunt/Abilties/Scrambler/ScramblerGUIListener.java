package me.Ishaan.manhunt.Abilties.Scrambler;

import me.Ishaan.manhunt.Enums.ManhuntTeam;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ScramblerGUIListener implements Listener {

    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler().hasGameStarted()) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (new ManhuntCommandHandler().getTeam(name).equals(ManhuntTeam.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta().getLore()).contains(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Scramble the selected players inventory!")) {
                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));

                            assert selectedPlayer != null;
                            ItemStack[] oldInv = selectedPlayer.getInventory().getStorageContents();
                            Collections.shuffle(Arrays.asList(oldInv));
                            selectedPlayer.getInventory().setStorageContents(oldInv);
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

}
