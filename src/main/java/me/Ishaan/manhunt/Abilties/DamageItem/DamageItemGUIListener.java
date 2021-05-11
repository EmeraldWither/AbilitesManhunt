package me.Ishaan.manhunt.Abilties.DamageItem;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;

public class DamageItemGUIListener implements Listener {
    List<String> speedrunner;
    List<String> hunter;

    private final Main main;
    public DamageItemGUIListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();
        if (event.getInventory().getHolder() instanceof GUIInventoryHolder && event.getCurrentItem() != null && (new ManhuntCommandHandler(main)).hasGameStarted()) {
            String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
            if ((new ManhuntCommandHandler(main)).getTeam(name).equals(Team.HUNTER)) {
                Player player = (Player)event.getView().getPlayer();
                if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getGravity())) {
                    SkullMeta skull = (SkullMeta)event.getCurrentItem().getItemMeta();
                    Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                    for(ItemStack item : selectedPlayer.getInventory().getContents()) {
                        if(item != null) {
                            if (item.getItemMeta() instanceof Damageable) {
                                int itemDurablity = item.getType().getMaxDurability() - ((Damageable) item.getItemMeta()).getDamage();
                                if (itemDurablity >= 1) {
                                    int itemDamage = item.getType().getMaxDurability() - (itemDurablity / 2);
                                    item.setDurability((short) itemDamage);
                                }
                            }
                        }
                    }

                    player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 100.0F, 0.0F);
                    player.closeInventory(Reason.UNLOADED);
                }
            }
        }

    }
}