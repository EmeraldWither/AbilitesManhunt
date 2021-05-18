package me.Ishaan.manhunt.Abilties.Scrambler;

import me.Ishaan.manhunt.Abilties.CooldownsManager;
import me.Ishaan.manhunt.Enums.Ability;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.Mana.Manacounter;
import me.Ishaan.manhunt.ManhuntGameManager;
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

public class ScramblerGUIListener extends CooldownsManager implements Listener {
    Map<String, Long> scramblerCooldown = getCooldown(Ability.SCRAMBLE) ;

    String ability = "Scramble Inventory";

    private Main main;
    private Manacounter manacounter;
    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public ScramblerGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
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

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if(manhuntGameManager.getGameStatus()) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (hunter.contains(name)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getScrambler())){
                            if (scramblerCooldown.containsKey(player.getName())) {
                                if (scramblerCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    Integer timeLeft = (int) (System.currentTimeMillis() - scramblerCooldown.get(player.getName()));
                                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((scramblerCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
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

                            int items = 0;

                            for(ItemStack item : oldInv){
                                if(item != null) {
                                    items++;
                                }
                            }

                            manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 50);
                            manacounter.updateActionbar(player);


                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.scramble.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(items))));
                            selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.scramble.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%items%", Integer.toString(items))));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

}
