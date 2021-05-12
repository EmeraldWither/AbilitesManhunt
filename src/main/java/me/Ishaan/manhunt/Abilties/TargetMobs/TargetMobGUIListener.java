package me.Ishaan.manhunt.Abilties.TargetMobs;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TargetMobGUIListener implements Listener {

    private final Main main;
    public TargetMobGUIListener(Main main){
        this.main = main;
    }
    Map<String, Long> targetMobCooldown = new HashMap<String, Long>();

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler(main).hasGameStarted()) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getMobTargeter())){
                            if (targetMobCooldown.containsKey(player.getName())) {
                                if (targetMobCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg")));
                                    return;
                                }
                            }


                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                            assert selectedPlayer != null;
                            int range = main.getConfig().getInt("abilities.mob-targeting.range");

                            for(Entity entity : selectedPlayer.getNearbyEntities(range ,range ,range)){
                                if(entity instanceof Creature){
                                    ((Creature) entity).setTarget(selectedPlayer);
                                }
                            }
                            Integer cooldown = main.getConfig().getInt("abilities.mob-targeting.cooldown");
                            targetMobCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }


}
