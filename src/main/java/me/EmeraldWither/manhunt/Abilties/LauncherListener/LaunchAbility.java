package me.EmeraldWither.manhunt.Abilties.LauncherListener;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.GUI.SpeedrunnerGUI;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManHuntInventory;
import me.EmeraldWither.manhunt.Mana.Manacounter;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class LaunchAbility implements Listener {

    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private Main main;
    List<String> hunter;
    List<String> speedrunner;
    public LaunchAbility(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }


    @EventHandler
    public void DetectLauncher(PlayerInteractEvent event){
    if (manhuntGameManager.getGameStatus()) {
        if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLauncher())){
            String name = event.getPlayer().getName();
                if (hunter.contains(event.getPlayer().getName())) {
                    if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (speedrunner.toString() != null) {
                            Player player = event.getPlayer();
                            if (manacounter.getManaList().get(player.getName())>= 20) {
                                SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                                inv.createInventory();
                                Inventory getInventory = inv.getInv();

                                player.openInventory(getInventory);
                            }
                            else{
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.mana-error-msg")));
                            }
                        }
                    }
                }
            }
        }
    }
}



