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
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.*;

public class ScramblerGUIListener  implements Listener {

    String ability = "Scramble Inventory";

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    Map<String, Long> scramblerCooldown;
    List<String> hunter;
    List<String> speedrunner;

    public ScramblerGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.main = main;
        this.abilitesManager = AbilitesManager;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        scramblerCooldown = AbilitesManager.getCooldown(Ability.SCRAMBLE);
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        ;
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.SCRAMBLE)) {
                if (scramblerCooldown.containsKey(player.getName())) {
                    if (scramblerCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        Integer timeLeft = (int) (System.currentTimeMillis() - scramblerCooldown.get(player.getName()));
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((scramblerCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }
                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));

                assert selectedPlayer != null;
                ItemStack[] oldInv = selectedPlayer.getInventory().getStorageContents();
                Collections.shuffle(Arrays.asList(oldInv));
                selectedPlayer.getInventory().setStorageContents(oldInv);

                Integer cooldown = main.getConfig().getInt("abilities.scramble.cooldown");
                scramblerCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));

                int items = 0;

                for (ItemStack item : oldInv) {
                    if (item != null) {
                        items++;
                    }
                }

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 50);
                manacounter.updateActionbar(player);


                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.scramble.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(items))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.scramble.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(items))));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
}
