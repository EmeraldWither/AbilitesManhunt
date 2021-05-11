package me.Ishaan.manhunt.Abilties.LauncherListener;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class LaunchAbility implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    private final Main main;
    public LaunchAbility(Main main){
        this.main = main;
    }

@EventHandler
    public void DetectLauncher(PlayerInteractEvent event){
    if (new ManhuntCommandHandler(main).hasGameStarted()) {
        if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLauncher())){
            String name = event.getPlayer().getName();
                if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                    if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (speedrunner.toString() != null) {
                            Player player = event.getPlayer();

                            SpeedrunnerGUI inv = new SpeedrunnerGUI();
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


