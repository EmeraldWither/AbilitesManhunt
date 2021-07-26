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
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTPGUIListener implements Listener {

    private ManhuntMain manhuntMain;
    String ability = "RandomTP";
    private Manhunt manhunt;
    private Manacounter manacounter;
    private Abilites abilites;
    Map<UUID, Long> randomTPCooldown;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public RandomTPGUIListener(Manhunt manhunt, ManhuntMain manhuntMain, Manacounter manacounter, Abilites Abilites){
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
        this.manhunt = manhunt;
        this.abilites = Abilites;
        this.randomTPCooldown = abilites.getCooldown(Ability.RANDOMTP);
        hunter = manhunt.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhunt.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilites.getHeldAbility(player).equals(Ability.RANDOMTP)) {
                if (randomTPCooldown.containsKey(player.getUniqueId())) {
                    if (randomTPCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((randomTPCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }

                SkullMeta skull = (SkullMeta) Objects.requireNonNull(event.getCurrentItem()).getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(Objects.requireNonNull(skull.getOwner()));
                assert selectedPlayer != null;

                manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 80);
                manacounter.updateActionbar(player);


                Integer radius = manhuntMain.getConfig().getInt("abilities.randomtp.tp-radius");
                Location oldLoc = selectedPlayer.getLocation();
                randomTP(selectedPlayer.getUniqueId(), player.getUniqueId(), radius);

                double distance = selectedPlayer.getLocation().distance(oldLoc);

                Integer cooldown = manhuntMain.getConfig().getInt("abilities.randomtp.cooldown");
                randomTPCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));


                selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1000, 0);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.randomtp.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%distance%", Integer.toString((int) Math.round(distance)))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.randomtp.speedrunner-msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%distance%", Integer.toString((int) Math.round(distance)))));
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }

    public void randomTP(UUID speedrunner, UUID hunter, int radius){
        Player selectedPlayer = Bukkit.getPlayer(speedrunner);
        Player player = Bukkit.getPlayer(hunter);


        double x = ThreadLocalRandom.current().nextInt(radius * -1, radius + 1);
        double z = ThreadLocalRandom.current().nextInt(radius * -1, radius + 1);
        Location loc = selectedPlayer.getLocation().set(selectedPlayer.getLocation().getX() + x, selectedPlayer.getLocation().getY(), selectedPlayer.getLocation().getZ() + z);

        selectedPlayer.teleport(loc);
        player.teleport(selectedPlayer.getLocation().add(0, 5, 0));
    }
}
