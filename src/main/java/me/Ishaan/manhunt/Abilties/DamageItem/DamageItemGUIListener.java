package me.Ishaan.manhunt.Abilties.DamageItem;

import me.Ishaan.manhunt.Abilties.CooldownsManager;
import me.Ishaan.manhunt.Enums.Ability;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.Mana.Manacounter;
import me.Ishaan.manhunt.ManhuntGameManager;
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
import java.util.Map;

public class DamageItemGUIListener extends CooldownsManager implements Listener {

    String ability = "Damage Items";

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public DamageItemGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
        this.main = main;
        this.manacounter = manacounter;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        Map<String, Long> damageCooldown = getCooldown(Ability.DAMAGEITEM);
        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
        Inventory getInventory = inv.getInv();
        if (event.getInventory().getHolder() instanceof GUIInventoryHolder && event.getCurrentItem() != null && manhuntGameManager.getGameStatus()) {
            String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
            if (hunter.contains(event.getView().getPlayer().getName())) {
                Player player = (Player) event.getView().getPlayer();
                if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getDamageItem())) {
                    if (damageCooldown.containsKey(player.getName())) {
                        if (damageCooldown.get(player.getName()) > System.currentTimeMillis()) {
                            player.closeInventory(Reason.PLUGIN);
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
                    damageCooldown.put(player.getName(), (System.currentTimeMillis() + (cooldown * 1000)));

                    manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 40);
                    manacounter.updateActionbar(player);

                    player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 100.0F, 0.0F);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.damageitem.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));
                    selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.damageitem.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));

                    player.closeInventory(Reason.PLUGIN);
                }
            }
        }
    }
}