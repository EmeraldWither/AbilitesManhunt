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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DamageItemGUIListener implements Listener {
    List<String> speedrunner;
    List<String> hunter;

    private final Main main;

    public DamageItemGUIListener(Main main) {
        this.main = main;
    }

    Map<String, Long> damageCooldown = new HashMap<String, Long>();

    String ability = "Damage Items";


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();
        if (event.getInventory().getHolder() instanceof GUIInventoryHolder && event.getCurrentItem() != null && (new ManhuntCommandHandler(main)).hasGameStarted()) {
            String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
            if ((new ManhuntCommandHandler(main)).getTeam(name).equals(Team.HUNTER)) {
                Player player = (Player) event.getView().getPlayer();
                if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getDamageItem())) {
                    if (damageCooldown.containsKey(player.getName())) {
                        if (damageCooldown.get(player.getName()) > System.currentTimeMillis()) {
                            player.closeInventory(Reason.UNLOADED);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((damageCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                            return;
                        }
                    }

                    SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                    Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                    int damageableItems = 0;
                    for (ItemStack item : selectedPlayer.getInventory().getContents()) {
                        if (item != null) {
                            if (item.getItemMeta() instanceof Damageable) {
                                int itemDurablity = item.getType().getMaxDurability() - ((Damageable) item.getItemMeta()).getDamage();
                                if (itemDurablity >= 1) {
                                    int itemDamage = item.getType().getMaxDurability() - (itemDurablity / 2);
                                    item.setDurability((short) itemDamage);
                                    damageableItems++;
                                }
                            }
                        }
                    }
                    Integer cooldown = main.getConfig().getInt("abilities.damageitem.cooldown");
                    damageCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                    player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 100.0F, 0.0F);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.damageitem.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));
                    player.closeInventory(Reason.UNLOADED);
                }
            }
        }
    }
}