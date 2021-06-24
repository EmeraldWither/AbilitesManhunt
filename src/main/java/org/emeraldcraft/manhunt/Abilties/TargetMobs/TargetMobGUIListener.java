package org.emeraldcraft.manhunt.Abilties.TargetMobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TargetMobGUIListener  implements Listener {
    String ability = "Command Mobs";

    private ManhuntMain manhuntMain;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;
    Map<String, Long> targetMobCooldown;
    List<String> hunter;

    public TargetMobGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntMain = manhuntMain;
        targetMobCooldown = AbilitesManager.getCooldown(Ability.TARGETMOB);
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.TARGETMOB)) {
                if (targetMobCooldown.containsKey(player.getName())) {
                    if (targetMobCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((targetMobCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }


                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                assert selectedPlayer != null;
                int range = manhuntMain.getConfig().getInt("abilities.mob-targeting.range");
                List<Entity> entities = selectedPlayer.getNearbyEntities(range, range, range);

                int mobs = 0;

                for (Entity entity : entities) {
                    if (entity instanceof Creature) {
                        ((Creature) entity).setTarget(selectedPlayer);
                        mobs++;
                    }
                }

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 100);
                manacounter.updateActionbar(player);


                Integer cooldown = manhuntMain.getConfig().getInt("abilities.mob-targeting.cooldown");
                targetMobCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.mob-targeting.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%mobs%", Integer.toString(mobs))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.mob-targeting.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%mobs%", Integer.toString(mobs))));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
}
