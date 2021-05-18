package me.Ishaan.manhunt.Abilties.TargetMobs;

import me.Ishaan.manhunt.Abilties.CooldownsManager;
import me.Ishaan.manhunt.Enums.Ability;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.Mana.Manacounter;
import me.Ishaan.manhunt.ManhuntGameManager;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TargetMobGUIListener extends CooldownsManager implements Listener {
    Map<String, Long> targetMobCooldown = getCooldown(Ability.TARGETMOB);
    String ability = "Command Mobs";

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    public TargetMobGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        this.main = main;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(manhuntGameManager.getGameStatus()) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (hunter.contains(name)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getMobTargeter())){
                            if (targetMobCooldown.containsKey(player.getName())) {
                                if (targetMobCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((targetMobCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }


                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                            assert selectedPlayer != null;
                            int range = main.getConfig().getInt("abilities.mob-targeting.range");
                            List<Entity> entities = selectedPlayer.getNearbyEntities(range ,range ,range);

                            int mobs = 0;

                            for(Entity entity : entities){
                                if(entity instanceof Creature){
                                    ((Creature) entity).setTarget(selectedPlayer);
                                    mobs++;
                                }
                            }

                            manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 100);
                            manacounter.updateActionbar(player);


                            Integer cooldown = main.getConfig().getInt("abilities.mob-targeting.cooldown");
                            targetMobCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.mob-targeting.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%mobs%", Integer.toString(mobs))));
                            selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.mob-targeting.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%mobs%", Integer.toString(mobs))));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }


}
