package me.EmeraldWither.manhunt.Abilties.Freeze;

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
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

public class FreezeGUIListener extends CooldownsManager implements Listener {
    private Main main;
    private Manacounter manacounter;

    String ability = "Freeze Player";

    private ManhuntGameManager manhuntGameManager;
    List<String> hunter;
    List<String> speedrunner;
    public FreezeGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }
    Map<String, Long> freezeCooldown = getCooldown(Ability.FREEZER);



    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
        if (event.getInventory().getHolder() instanceof GUIInventoryHolder) {
            if (manhuntGameManager.getGameStatus()) {
                if (event.getCurrentItem() != null) {
                    String name = Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getName())).getName();
                    if (hunter.contains(event.getView().getPlayer().getName())) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getFreezer())) {
                            if (freezeCooldown.containsKey(player.getName())) {
                                if (freezeCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((freezeCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }

                            SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                            assert selectedPlayer != null;

                            Integer time = main.getConfig().getInt("abilities.freeze.time");
                            FreezePlayer(player, selectedPlayer, time);

                            Integer cooldown = main.getConfig().getInt("abilities.freeze.cooldown");
                            freezeCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));

                            manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 30);
                            manacounter.updateActionbar(player);


                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.freeze.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%time%", time.toString())));
                            player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

                        }
                    }
                }
            }
        }
    }

    public void FreezePlayer(Player hunter, Player speedrunner, Integer time) {
        Integer delay = time * 20;
        speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.speedrunner-freeze-msg").replace("%hunter%", hunter.getName()).replace("%time%", Integer.toString(time))));
        manhuntGameManager.getTeam(Team.FROZEN).add(speedrunner.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
            @Override
            public void run() {
                speedrunner.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.speedrunner-unfreeze-msg").replace("%hunter%", hunter.getName())));
                hunter.sendMessage(ChatColor.translateAlternateColorCodes('&' ,main.getConfig().getString("abilities.freeze.unfreeze-msg").replace("%hunter%", hunter.getName()).replace("%speedrunner%", speedrunner.getName())));
                manhuntGameManager.getTeam(Team.FROZEN).remove(speedrunner.getName());
            }
        }, delay); //20 Tick (1 Second) delay before run() is called

    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        if (manhuntGameManager.getGameStatus()) {
            if (manhuntGameManager.getTeam(Team.FROZEN).contains(event.getPlayer().getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent event){
        if(main.getConfig().getBoolean("abilities.freeze.prevent-kicking")) {
            if (manhuntGameManager.getTeam(Team.FROZEN).contains(event.getPlayer().getName())) {
                if (manhuntGameManager.getGameStatus()) {
                    if (event.getReason().equalsIgnoreCase("Flying is not enabled on this server")) {
                        event.setCancelled(true);
                        Bukkit.getServer().getLogger().log(Level.WARNING, "[Abilites Manhunt] " + event.getPlayer().getName() + " would have been kicked for flying due to them being frozen. We have prevented this by preventing them from getting kicked. You can change in the config.");
                    }
                }
            }
        }
    }
}
