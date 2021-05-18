package me.EmeraldWither.manhunt.Abilties.StrikeLightning;

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

public class LightningListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    public LightningListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }



    @EventHandler
    public void StrikeLightning(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (manhuntGameManager.getGameStatus()) {
                String name = event.getPlayer().getName();
                if (hunter.contains(name)) {
                        if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLightning())) {
                            if (manacounter.getManaList().get(name) >= 10) {
                                Player player = event.getPlayer();

                            SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
                            inv.createInventory();
                            Inventory getInventory = inv.getInv();

                            player.openInventory(getInventory);
                            return;
                        }
                        else {
                            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.mana-error-msg")));
                        }
                    }
                }
            }
        }
    }
}


