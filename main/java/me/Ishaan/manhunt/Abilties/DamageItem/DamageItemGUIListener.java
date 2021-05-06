package me.Ishaan.manhunt.Abilties.DamageItem;

import me.Ishaan.manhunt.Enums.ManhuntTeam;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;

public class DamageItemGUIListener implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler().hasGameStarted()) {
                    String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
                    if (new ManhuntCommandHandler().getTeam(name).equals(ManhuntTeam.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta().getLore()).contains(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Sets the durability of the item that")) {

                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                            player.sendMessage("About to check if the item is damageable");
                            ItemStack item = selectedPlayer.getInventory().getItemInMainHand();
                            if (item.getItemMeta() instanceof Damageable) {
                                player.sendMessage("About to check the item durability!");
                                int itemDurablity = selectedPlayer.getInventory().getItemInMainHand().getType().getMaxDurability() - ((Damageable) selectedPlayer.getInventory().getItemInMainHand().getItemMeta()).getDamage();
                                player.sendMessage("The Hunters Current Item Durability is " + Integer.toString(itemDurablity));
                                if (itemDurablity >= 1) {
                                    int itemDamage = item.getType().getMaxDurability() - (itemDurablity / 2);
                                    int newItemDurability = itemDurablity - itemDamage;
                                    player.sendMessage("Setting the item durability to " + Integer.toString(newItemDurability));
                                    ((Damageable) item.getItemMeta()).setDamage(itemDamage);
                                    selectedPlayer.getInventory().getItemInMainHand().setItemMeta(item.getItemMeta());
                                    player.sendMessage("Succesfully set the item durability to " + Integer.toString(newItemDurability));

                                }
                            }

                            player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 100, 0);
                            player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);

                        }
                    }
                }
            }
        }
    }

}
