package me.Ishaan.manhunt.Abilties.LauncherListener;

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

import java.util.List;
import java.util.Map;

public class LauncherGUIListener extends CooldownsManager implements Listener {
    Map<String, Long> launcherCooldown = getCooldown(Ability.LAUNCHER);

    String ability = "Launcher";
    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public LauncherGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(manhuntGameManager.getGameStatus()) {
                    String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
                    if (manhuntGameManager.getTeam(Team.HUNTER).contains(name)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLauncher())){
                            if (launcherCooldown.containsKey(player.getName())) {
                                if (launcherCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((launcherCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }

                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());

                            int velocity = main.getConfig().getInt("abilities.launcher.launch-velocity");
                            Boolean launchUpwards = main.getConfig().getBoolean("abilities.launcher.launch-upwards");

                            if(launchUpwards.equals(false)) {
                                selectedPlayer.setVelocity(selectedPlayer.getEyeLocation().getDirection().multiply(3).add(new Vector(0, velocity, 0)));
                            }
                            else{
                                selectedPlayer.setVelocity(new Vector(0, velocity, 0 ));
                            }

                            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1000, 0);
                            Bukkit.getWorld(player.getWorld().getUID()).spawnParticle(Particle.EXPLOSION_HUGE, selectedPlayer.getLocation(), 10);

                            manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 20);
                            manacounter.updateActionbar(player);


                            Integer cooldown = main.getConfig().getInt("abilities.launcher.cooldown");
                            launcherCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.launcher.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%velocity%", Integer.toString(velocity))));
                            selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.launcher.speedrunner-msg").replace("%hunter%", player.getName()).replace("%velocity%", Integer.toString(velocity))));

                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

}
