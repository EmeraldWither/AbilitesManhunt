package me.EmeraldWither.manhunt.Abilties.RandomTP;

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

public class RandomTPListener implements Listener {

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    public RandomTPListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void getClickedItem(PlayerInteractEvent event) {
        if(manhuntGameManager.getGameStatus()) {
                if (event.getPlayer().getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getrandomTP())){
                    String name = event.getPlayer().getName();
                    if (hunter.contains(event.getPlayer().getName())) {
                            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                                if (manacounter.getManaList().get(name) >= 80) {
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


