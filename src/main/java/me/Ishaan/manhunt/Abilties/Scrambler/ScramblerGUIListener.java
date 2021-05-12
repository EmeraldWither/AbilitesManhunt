package me.Ishaan.manhunt.Abilties.Scrambler;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ScramblerGUIListener implements Listener {

    private final Main main;
    public ScramblerGUIListener(Main main){
        this.main = main;
    }

    Map<String, Long> scramblerCooldown = new HashMap<String, Long>();

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(new ManhuntCommandHandler(main).hasGameStarted()) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getScrambler())){
                            if (scramblerCooldown.containsKey(player.getName())) {
                                if (scramblerCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg")));
                                    return;
                                }
                            }
                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));

                            assert selectedPlayer != null;
                            ItemStack[] oldInv = selectedPlayer.getInventory().getStorageContents();
                            Collections.shuffle(Arrays.asList(oldInv));
                            selectedPlayer.getInventory().setStorageContents(oldInv);

                            Integer cooldown = main.getConfig().getInt("abilities.scramble.cooldown");
                            scramblerCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

}
