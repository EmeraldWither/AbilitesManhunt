package me.Ishaan.manhunt.Abilties.Freeze;

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
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.logging.Level;

public class FreezeGUIListener implements Listener {
    private final Main main;

    public FreezeGUIListener(Main main) {
        this.main = main;
    }

    Map<String, Long> freezeCooldown = new HashMap<String, Long>();
    List<String> frozenPlayers = new ArrayList<String>();

    String ability = "Freeze Player";


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        SpeedrunnerGUI inv = new SpeedrunnerGUI();

        if (event.getInventory().getHolder() instanceof GUIInventoryHolder) {
            if (new ManhuntCommandHandler(main).hasGameStarted()) {
                if (event.getCurrentItem() != null) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (new ManhuntCommandHandler(main).getTeam(name).equals(Team.HUNTER)) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getFreezer())) {
                            if (freezeCooldown.containsKey(player.getName())) {
                                if (freezeCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((freezeCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }

                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                            assert selectedPlayer != null;

                            Integer time = main.getConfig().getInt("abilities.freeze.time");
                            FreezePlayer(selectedPlayer);

                            Integer cooldown = main.getConfig().getInt("abilities.freeze.cooldown");
                            freezeCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));


                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.freeze.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%time%", time.toString())));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

    public void FreezePlayer(Player player) {
        int time = main.getConfig().getInt("abilities.freeze.time") * 20;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.freeze-msg").replace("%hunter%", player.getName())));
        frozenPlayers.add(player.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.unfreeze-msg").replace("%hunter%", player.getName())));
                frozenPlayers.remove(player.getName());
            }
        }, time); //20 Tick (1 Second) delay before run() is called

    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        if (new ManhuntCommandHandler(main).hasGameStarted()) {
            if (frozenPlayers.contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent event){
        if(main.getConfig().getBoolean("abilities.freeze.prevent-kicking")) {
            if (frozenPlayers.contains(event.getPlayer().getName())) {
                if (new ManhuntCommandHandler(main).hasGameStarted()) {
                    if (event.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
                        event.setCancelled(true);
                        Bukkit.getServer().getLogger().log(Level.WARNING, "[Abilites Manhunt] " + event.getPlayer().getName() + " would have been kicked for flying due to them being frozen. We have prevented this by preventing them from getting kicked. You can change in the config.");
                    }
                }
            }
        }
    }
}
