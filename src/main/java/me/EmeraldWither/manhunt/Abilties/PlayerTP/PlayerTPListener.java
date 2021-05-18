package me.EmeraldWither.manhunt.Abilties.PlayerTP;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.GUI.SpeedrunnerGUI;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManHuntInventory;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class PlayerTPListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public PlayerTPListener(ManhuntGameManager manhuntGameManager, Main main){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

@EventHandler
    public void DetectLauncher(PlayerInteractEvent event){
    if (manhuntGameManager.getGameStatus()) {
        if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getPlayerTP())){
            String name = event.getPlayer().getName();
                if (hunter.contains(event.getPlayer().getName())) {
                    if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (speedrunner.toString() != null) {
                            Player player = event.getPlayer();

                            SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                            inv.createInventory();
                            Inventory getInventory = inv.getInv();

                            player.openInventory(getInventory);
                        }
                    }
                }
            }
        }
    }
}




