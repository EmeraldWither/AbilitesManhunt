package me.Ishaan.manhunt.Abilties.DamageItem;

import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.Mana.Manacounter;
import me.Ishaan.manhunt.ManhuntGameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class DamageItemListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    public DamageItemListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        this.main = main;
        this.manacounter = manacounter;
    }
    @EventHandler
    public void DetectDamageItem(PlayerInteractEvent event) {
        if (manhuntGameManager.getGameStatus() && event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getDamageItem())) {
            String name = event.getPlayer().getName();
            if (hunter.contains(event.getPlayer().getName())) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (manacounter.getManaList().get(name) >= 40) {
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

