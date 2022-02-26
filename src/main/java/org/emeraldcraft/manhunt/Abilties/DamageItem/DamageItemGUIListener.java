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
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DamageItemGUIListener implements Listener {

    String ability = "Damage Items";

    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private Manhunt manhunt;
    private Abilites Abilites;

    private Map<UUID, Long> damageCooldown;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public DamageItemGUIListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites) {
        this.manhunt = manhunt;
        this.Abilites = Abilites;
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);
        this.damageCooldown = Abilites.getCooldown(Ability.DAMAGEITEM);
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getView().getPlayer();
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            if (Abilites.getHeldAbility(player).equals(Ability.DAMAGEITEM)) {
                if (damageCooldown.containsKey(player.getUniqueId())) {
                    if (damageCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        player.closeInventory(Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((damageCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                int damageableItems = 0;
                Random rand = new Random();
                ItemStack[] givenList = selectedPlayer.getInventory().getContents();

                int numberOfElements = manhuntMain.getConfig().getInt("abilities.damageitem.amount");
                for (int i = 0; i < numberOfElements; i++) {
                    int randomIndex = rand.nextInt(givenList.length);
                    int index = randomIndex - 1;
                    if(index == -1) index = 0;
                    ItemStack item = givenList[index];
                    if (item != null) {
                        if (item.getItemMeta() instanceof Damageable) {
                            int itemDurablity = item.getType().getMaxDurability() - ((Damageable) item.getItemMeta()).getDamage();
                            if (itemDurablity >= 1) {
                                int itemDamage = item.getType().getMaxDurability() - (itemDurablity / 2);
                                item.setDurability((short) itemDamage);
                                damageableItems++;
                            }
                            else i--;
                        }
                        else i--;
                    }
                    else i--;
                }
                int cooldown = manhuntMain.getConfig().getInt("abilities.damageitem.cooldown");
                damageCooldown.put(player.getUniqueId(), (System.currentTimeMillis() + (cooldown * 1000L)));

                manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 40);
                manacounter.updateActionbar(player);

                player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BREAK, 100.0F, 0.0F);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.damageitem.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.damageitem.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(damageableItems))));

                player.closeInventory(Reason.PLUGIN);
            }
        }
    }
}