package me.Ishaan.manhunt.Abilties.RandomTP;

import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.GUI.SpeedrunnerGUI;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTPGUIListener implements Listener {
    List<String> hunter = HunterList.hunters;

    private final Main main;
    public RandomTPGUIListener(Main main){
        this.main = main;
    }
    Map<String, Long> damageCooldown = new HashMap<String, Long>();

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){


        SpeedrunnerGUI inv = new SpeedrunnerGUI();
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(new ManhuntCommandHandler(main).hasGameStarted()) {
                if (event.getCurrentItem() != null) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getrandomTP())){
                            if (damageCooldown.containsKey(player.getName())) {
                                if (damageCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg")));
                                    return;
                                }
                            }

                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                            assert selectedPlayer != null;

                            Integer radius = main.getConfig().getInt("abilities.randomtp.tp-radius");
                            randomTP(selectedPlayer.getName(), player.getName(), radius);

                            Integer cooldown = main.getConfig().getInt("abilities.randomtp.cooldown");
                            damageCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));


                            selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1000, 0);
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

    public void randomTP(String speedrunner, String hunter, int radius){
        Player selectedPlayer = Bukkit.getPlayer(speedrunner);
        Player player = Bukkit.getPlayer(hunter);


        double x = ThreadLocalRandom.current().nextInt(radius * -1, radius + 1);
        double z = ThreadLocalRandom.current().nextInt(radius * -1, radius + 1);
        Location loc = selectedPlayer.getLocation().set(selectedPlayer.getLocation().getX() + x, selectedPlayer.getLocation().getY(), selectedPlayer.getLocation().getZ() + z);

        selectedPlayer.teleport(loc);
        player.teleport(selectedPlayer.getLocation().add(0, 5, 0));
    }
}
