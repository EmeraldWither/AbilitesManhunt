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
            if(new ManhuntCommandHandler().hasGameStarted()) {
                if (event.getCurrentItem() != null) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (new ManhuntCommandHandler().getTeam(name).equals(ManhuntTeam.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta().getLore()).contains(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Teleports the speedrunner within a 50 block radius!")) {
                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                            assert selectedPlayer != null;
                            randomTP(selectedPlayer.getName(), player.getName(), 30);
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

    public void randomTP(String speedrunner, String hunter, int radius){
        Player selectedPlayer = Bukkit.getPlayer(speedrunner);
        Player player = Bukkit.getPlayer(hunter);


        double x = ThreadLocalRandom.current().nextInt(radius * -1, radius + 1);
        double z = ThreadLocalRandom.current().nextInt(radius * -1, radius + 1);
        Location loc = selectedPlayer.getLocation().set(selectedPlayer.getLocation().getX() + x, selectedPlayer.getLocation().getY(), selectedPlayer.getLocation().getZ() + z);

        selectedPlayer.teleport(loc);
        player.teleport(selectedPlayer.getLocation().add(0, 5, 0));
    }
}
