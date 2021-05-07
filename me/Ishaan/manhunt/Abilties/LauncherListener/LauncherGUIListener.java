package me.Ishaan.manhunt.Abilties.LauncherListener;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
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
import java.util.Objects;

public class LauncherGUIListener implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler().hasGameStarted()) {
                    String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
                    if (new ManhuntCommandHandler().getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta().getLore()).contains(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Launches the speedrunner into the air!")) {

                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());

                            selectedPlayer.setVelocity(selectedPlayer.getEyeLocation().getDirection().add(new Vector(0,2.5,0)));
                            Bukkit.getWorld(selectedPlayer.getWorld().getName()).playSound(selectedPlayer.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1000, 0);
                            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1000, 0);
                            Bukkit.getWorld(player.getWorld().getUID()).spawnParticle(Particle.EXPLOSION_HUGE, selectedPlayer.getLocation(), 10);
                            player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);

                        }
                    }
                }
            }
        }
    }

}
