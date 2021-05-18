package me.EmeraldWither.manhunt.Abilties.Freeze;

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

public class FreezeListener implements Listener {
    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public FreezeListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void DetectDamageItem(PlayerInteractEvent event) {
        if (manhuntGameManager.getGameStatus() && event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getFreezer())) {
            String name = event.getPlayer().getName();
            if (hunter.contains(event.getPlayer().getName())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (manacounter.getManaList().get(name) >= 30) {
                        Player player = event.getPlayer();
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
