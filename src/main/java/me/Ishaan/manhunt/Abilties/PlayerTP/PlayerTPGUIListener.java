package me.Ishaan.manhunt.Abilties.PlayerTP;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Objects;

public class PlayerTPGUIListener implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    private final Main main;
    public PlayerTPGUIListener(Main main){
        this.main = main;
    }

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
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getPlayerTP())){

                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                            int height = main.getConfig().getInt("abilities.playertp.height-above-player");

                            player.teleport(selectedPlayer.getLocation().add(0,height,0));
                            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1000, 0);
                            player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);

                        }
                    }
                }
            }
        }
    }

}
