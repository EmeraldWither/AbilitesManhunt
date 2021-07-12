package org.emeraldcraft.manhunt.Abilties.Scrambler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.*;

public class ScramblerGUIListener  implements Listener {

    String ability = "Scramble Inventory";

    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    Map<UUID, Long> scramblerCooldown;
    List<UUID> hunter;
    List<UUID> speedrunner;

    public ScramblerGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntMain = manhuntMain;
        this.abilitesManager = AbilitesManager;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        scramblerCooldown = AbilitesManager.getCooldown(Ability.SCRAMBLE);
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        ;
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.SCRAMBLE)) {
                if (scramblerCooldown.containsKey(player.getUniqueId())) {
                    if (scramblerCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        Integer timeLeft = (int) (System.currentTimeMillis() - scramblerCooldown.get(player.getUniqueId()));
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((scramblerCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }
                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));

                assert selectedPlayer != null;
                ItemStack[] oldInv = selectedPlayer.getInventory().getStorageContents();
                Collections.shuffle(Arrays.asList(oldInv));
                selectedPlayer.getInventory().setStorageContents(oldInv);

                Integer cooldown = manhuntMain.getConfig().getInt("abilities.scramble.cooldown");
                scramblerCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));

                int items = 0;

                for (ItemStack item : oldInv) {
                    if (item != null) {
                        items++;
                    }
                }

                manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 50);
                manacounter.updateActionbar(player);


                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.scramble.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(items))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.scramble.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(items))));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
}
