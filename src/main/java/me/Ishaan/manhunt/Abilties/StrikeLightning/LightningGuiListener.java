package me.Ishaan.manhunt.Abilties.StrikeLightning;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;

public class LightningGuiListener implements Listener {

    private final Main main;
    public LightningGuiListener(Main main){
        this.main = main;
    }

    Map<String, Long> lightningCooldown = new HashMap<String, Long>();

    String ability = "Strike Lightning";


    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder) {
            if (event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler(main).hasGameStarted()) {
                    String name = Bukkit.getPlayer(event.getWhoClicked().getName()).getName();
                    if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getLightning())){
                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                            Integer cooldown = main.getConfig().getInt("abilities.lightning.cooldown");

                            if (lightningCooldown.containsKey(player.getName())) {
                                if (lightningCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((lightningCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }
                            lightningCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            selectedPlayer.getWorld().strikeLightning(selectedPlayer.getLocation());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.lightning.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName())));
                            player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);

                        }
                    }
                }
            }
        }
    }
}
