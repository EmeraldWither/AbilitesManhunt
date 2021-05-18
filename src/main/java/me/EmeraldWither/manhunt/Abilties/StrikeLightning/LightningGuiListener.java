package me.EmeraldWither.manhunt.Abilties.StrikeLightning;

import me.EmeraldWither.manhunt.Abilties.CooldownsManager;
import me.EmeraldWither.manhunt.Enums.Ability;
import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.GUI.GUIInventoryHolder;
import me.EmeraldWither.manhunt.GUI.SpeedrunnerGUI;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManHuntInventory;
import me.EmeraldWither.manhunt.Mana.Manacounter;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;

public class LightningGuiListener extends CooldownsManager implements Listener {
    Map<String, Long> lightningCooldown = getCooldown(Ability.LIGHTNING);
    String ability = "Strike Lightning";

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public LightningGuiListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        this.manacounter = manacounter;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder) {
            if (event.getCurrentItem() != null) {
                if(manhuntGameManager.getGameStatus()) {
                    String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
                    if (hunter.contains(name)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLightning())){
                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                            Integer cooldown = main.getConfig().getInt("abilities.lightning.cooldown");

                            if (lightningCooldown.containsKey(player.getName())) {
                                if (lightningCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((lightningCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }

                            manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 10);
                            manacounter.updateActionbar(player);


                            lightningCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            selectedPlayer.getWorld().strikeLightning(selectedPlayer.getLocation());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.lightning.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                            selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.lightning.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }
}
