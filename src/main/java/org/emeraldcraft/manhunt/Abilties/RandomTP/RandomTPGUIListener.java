package org.emeraldcraft.manhunt.Abilties.RandomTP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTPGUIListener implements Listener {

    private ManhuntMain manhuntMain;
    String ability = "RandomTP";
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager abilitesManager;
    Map<String, Long> randomTPCooldown;
    List<String> hunter;
    List<String> speedrunner;
    public RandomTPGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = AbilitesManager;
        this.randomTPCooldown = abilitesManager.getCooldown(Ability.RANDOMTP);
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.RANDOMTP)) {
                if (randomTPCooldown.containsKey(player.getName())) {
                    if (randomTPCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((randomTPCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                assert selectedPlayer != null;

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 80);
                manacounter.updateActionbar(player);


                Integer radius = manhuntMain.getConfig().getInt("abilities.randomtp.tp-radius");
                Location oldLoc = selectedPlayer.getLocation();
                randomTP(selectedPlayer.getName(), player.getName(), radius);

                double distance = selectedPlayer.getLocation().distance(oldLoc);

                Integer cooldown = manhuntMain.getConfig().getInt("abilities.randomtp.cooldown");
                randomTPCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));


                selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1000, 0);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.randomtp.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%distance%", Integer.toString((int) Math.round(distance)))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.randomtp.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%distance%", Integer.toString((int) Math.round(distance)))));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

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
    public void clearCooldowns() {
        randomTPCooldown.clear();
    }
    public Map<String, Long> getCooldowns(){
        return randomTPCooldown;
    }
}
