package org.emeraldcraft.manhunt.Abilties.DamageItem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;

public class DamageItemGUIListener implements Listener {

    String ability = "Damage Items";

    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager AbilitesManager;

    private Map<String, Long> damageCooldown;
    List<String> hunter;
    List<String> speedrunner;

    public DamageItemGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntGameManager = manhuntGameManager;
        this.AbilitesManager = AbilitesManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        this.damageCooldown = AbilitesManager.getCooldown(Ability.DAMAGEITEM);
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, manhuntMain);
        Player player = (Player) event.getView().getPlayer();
        if (event.getCurrentItem() != null) {
            if (AbilitesManager.getHeldAbility(player).equals(Ability.DAMAGEITEM)) {
                if (damageCooldown.containsKey(player.getName())) {
                    if (damageCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((damageCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
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
                Integer cooldown = manhuntMain.getConfig().getInt("abilities.damageitem.cooldown");
                damageCooldown.put(player.getName(), (System.currentTimeMillis() + (cooldown * 1000)));

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 40);
                manacounter.updateActionbar(player);

                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 100.0F, 0.0F);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.damageitem.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.damageitem.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));

                player.closeInventory(Reason.PLUGIN);
            }
        }
    }
}