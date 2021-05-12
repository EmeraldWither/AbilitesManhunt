package me.Ishaan.manhunt.Abilties.LauncherListener;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LauncherGUIListener implements Listener {
    private final Main main;
    public LauncherGUIListener(Main main){
        this.main = main;
    }
    Map<String, Long> launcherCooldown = new HashMap<String, Long>();

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler(main).hasGameStarted()) {
                    String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
                    if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLauncher())){
                            if (launcherCooldown.containsKey(player.getName())) {
                                if (launcherCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg")));
                                    return;
                                }
                            }

                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());

                            int velocity = main.getConfig().getInt("abilities.launcher.launch-velocity");
                            Boolean launchUpwards = main.getConfig().getBoolean("abilities.launcher.launch-upwards");

                            if(launchUpwards.equals(false)) {
                                selectedPlayer.setVelocity(selectedPlayer.getEyeLocation().getDirection().add(new Vector(0, velocity, 0)));
                            }
                            else{
                                selectedPlayer.setVelocity(new Vector(0, velocity, 0 ));
                            }

                            Bukkit.getWorld(selectedPlayer.getWorld().getName()).playSound(selectedPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1000, 0);
                            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1000, 0);
                            Bukkit.getWorld(player.getWorld().getUID()).spawnParticle(Particle.EXPLOSION_HUGE, selectedPlayer.getLocation(), 10);

                            Integer cooldown = main.getConfig().getInt("abilities.launcher.cooldown");
                            launcherCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);

                        }
                    }
                }
            }
        }
    }

}
