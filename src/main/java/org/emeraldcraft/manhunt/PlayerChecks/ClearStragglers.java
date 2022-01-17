package org.emeraldcraft.manhunt.PlayerChecks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Manhunt;

public class ClearStragglers implements Listener {
    private Manhunt manhunt;
    public ClearStragglers(Manhunt manhunt){
        this.manhunt = manhunt;
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        Player player = event.getPlayer();
        if (manhunt.getTeam(event.getPlayer().getUniqueId()) == ManhuntTeam.HUNTER) {
            player.getInventory().clear();
            ManHuntInventory manHuntInventory = new ManHuntInventory();
            manHuntInventory.giveAbility(Ability.LIGHTNING, player.getName(), 0);
            manHuntInventory.giveAbility(Ability.LAUNCHER, player.getName(), 1);
            manHuntInventory.giveAbility(Ability.FREEZER, player.getName(), 2);
            manHuntInventory.giveAbility(Ability.DAMAGEITEM, player.getName(), 3);
            manHuntInventory.giveAbility(Ability.SCRAMBLE, player.getName(), 4);
            manHuntInventory.giveAbility(Ability.GRAVITY, player.getName(), 5);
            manHuntInventory.giveAbility(Ability.RANDOMTP, player.getName(), 6);
            manHuntInventory.giveAbility(Ability.TARGETMOB, player.getName(), 7);
            manHuntInventory.giveAbility(Ability.PLAYERTP, player.getName(), 8);

            ItemStack barrier = manHuntInventory.getBarrier();
            Inventory inv = player.getInventory();

            for (int i = 0; i < 36; i++) {
                if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                    inv.setItem(i, barrier);
                }
            }
            player.setCollidable(false);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.ADVENTURE);
            player.setInvulnerable(true);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setSaturation(10000);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
            PotionEffect potionEffect = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2);
            potionEffect.withParticles(false);
            player.addPotionEffect(potionEffect);
            player.setGlowing(true);
            return;
        }
        player.setGameMode(GameMode.SURVIVAL);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setInvulnerable(false);
        player.setGlowing(true);
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (!manhunt.hasGameStarted()) {
            return;
        }
        Player player = event.getPlayer();
        if (manhunt.getTeam(event.getPlayer().getUniqueId()) == ManhuntTeam.HUNTER) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
            player.setGlowing(false);
            player.getInventory().clear();
            player.setGameMode(GameMode.SURVIVAL);
            player.setInvulnerable(false);
            player.closeInventory();
            player.setFlying(false);
            player.setAllowFlight(false);
            player.setCollidable(true);
            for (org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
                if (team.hasEntry(player.getName())) {
                    team.removeEntry(player.getName());
                }
            }
            return;
        }
        player.setGlowing(false);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.setInvulnerable(false);
        player.closeInventory();
        player.setFlying(false);
        player.setAllowFlight(false);
    }

}
