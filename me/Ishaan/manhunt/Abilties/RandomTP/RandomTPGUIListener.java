package me.Ishaan.manhunt.Abilties.RandomTP;

import me.Ishaan.manhunt.Enums.ManhuntTeam;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.ManhuntCommandHandler;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTPGUIListener implements Listener {
    List<String> hunter = HunterList.hunters;

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
            if (new ManhuntCommandHandler().getTeam(name).equals(ManhuntTeam.HUNTER)) {
                Player player = (Player) event.getView().getPlayer();
                if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta().getLore()).contains(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Teleports the speedrunner within a 50 block radius!")) {
                    SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                    Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));


                    double x = ThreadLocalRandom.current().nextInt(-50, 50 + 1);
                    double z = ThreadLocalRandom.current().nextInt(-50, 50 + 1);
                    Location loc = player.getLocation().set(player.getLocation().getX() + x, player.getLocation().getY(), player.getLocation().getZ() + z);

                    selectedPlayer.teleport(loc);
                    player.teleport(selectedPlayer.getLocation().add(0, 10, 0));
                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                }
            }
        }
    }
}
