package org.emeraldcraft.manhunt.Managers;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.emeraldcraft.manhunt.Enums.ManhuntTeam.DEAD;

public class ManhuntGameManager {

    List<UUID> hunter = new ArrayList<>();
    List<UUID> speedrunner = new ArrayList<>();

    List<UUID> deadSpeedrunners = new ArrayList<>();
    List<UUID> frozenPlayers = new ArrayList<>();
    private boolean hasGameStarted = false;

    //WAYPOINT
    HashMap<UUID, HashMap<String, Location>> waypoints = new HashMap<>();

    public HashMap<UUID, Integer> hunterScoreboardID = new HashMap<>();
    public HashMap<UUID, Integer> speedrunnerScoreboardID = new HashMap<>();

    public List<UUID> getTeam(ManhuntTeam team) {
        if (team == ManhuntTeam.HUNTER) {
            return hunter;
        }
        if (team == ManhuntTeam.FROZEN) {
            return frozenPlayers;
        }
        if (team == DEAD) {
                return deadSpeedrunners;
        }
        if (team == ManhuntTeam.SPEEDRUNNER) {
            return speedrunner;
        }
        return null;
    }
    public ManhuntTeam getTeam(UUID uuid){
        if(hunter.contains(uuid)){
            return ManhuntTeam.HUNTER;
        }
        if(speedrunner.contains(uuid)) {
            return ManhuntTeam.SPEEDRUNNER;
        }
        if(frozenPlayers.contains(uuid)){
            return ManhuntTeam.FROZEN;
        }
        if(deadSpeedrunners.contains(uuid)){
            return DEAD;
        }
        return ManhuntTeam.NONE;
    }

    public String getCooldown(Player player, Ability pickAbility, AbilitesManager abilitesManager){
        Long time;
        long cooldown;
        if(abilitesManager.getCooldown(pickAbility).get(player.getName()) != null) {
            time = abilitesManager.getCooldown(pickAbility).get(player.getName());
            cooldown = ((time - System.currentTimeMillis()) / 1000);
            if (!(cooldown < 1)) {
                return ChatColor.translateAlternateColorCodes('&', "&4" + cooldown + " seconds");
            }
        }
        return ChatColor.translateAlternateColorCodes('&', "&aREADY");
    }
    public boolean getGameStatus(){
        return hasGameStarted;
    }
    public void setGameStatus(boolean b){
        hasGameStarted = b;
    }
    public boolean startGame(CommandSender sender, ManhuntMain manhuntMain, Manacounter manacounter, Integer manadelay, AbilitesManager abilitesManager) {
        String prefix = manhuntMain.getConfig().getString("plugin-prefix");
        try {
            new SpeedrunnerGUI(this, manhuntMain).createInventory();
            ManHuntInventory manHuntInventory = new ManHuntInventory();
            setGameStatus(true);

            List<String> hunterList = new ArrayList<>();
            List<String> speedrunnerList = new ArrayList<>();
            for(UUID uuid : hunter){
                hunterList.add(Bukkit.getPlayer(uuid).getName());
            }
            for(UUID uuid : speedrunner){
                speedrunnerList.add(Bukkit.getPlayer(uuid).getName());
            }
            String hunters = hunterList.toString().replaceAll("]", "").replaceAll("\\[", "");
            String speedrunners = speedrunnerList.toString().replaceAll("]", "").replaceAll("\\[", "");

            for (UUID speedrunner : speedrunner) {
                Player player = Bukkit.getPlayer(speedrunner);
                ManhuntSpeedrunnerScoreboardManager speedrunnerScoreboardManager = new ManhuntSpeedrunnerScoreboardManager(this, manhuntMain);
                speedrunnerScoreboardManager.showSpeedrunnerScoreboard(player.getUniqueId(), manhuntMain.getPlugin());
                speedrunnerScoreboardID.put(player.getUniqueId(), speedrunnerScoreboardManager.id);
                player.spigot().respawn();
                for (String msg : manhuntMain.getConfig().getStringList("messages.start-msg")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%hunters%", hunters).replace("%speedrunners%", speedrunners)));

                }
                player.getInventory().clear();
                player.setHealth(20);
                player.setFoodLevel(20);
                player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                player.setGameMode(GameMode.SURVIVAL);
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setInvulnerable(false);
                for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                    player.removePotionEffect(potionEffect.getType());
                }

                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
                PotionEffect regenEffect = new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0);
                PotionEffect resistanceEffect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0);
                PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 6000, 0);
                PotionEffect saturationEffect = new PotionEffect(PotionEffectType.SATURATION, 6000, 0);
                player.addPotionEffect(regenEffect);
                player.addPotionEffect(resistanceEffect);
                player.addPotionEffect(speedEffect);
                player.addPotionEffect(saturationEffect);
                player.setGlowing(true);
            }
            for (UUID hunter : hunter) {
                Player player = Bukkit.getPlayer(hunter);

                if (manhuntMain.getConfig().getBoolean("scoreboard.enabled")) {
                    UUID uuid = player.getUniqueId();
                    ManhuntHunterScoreboardManager manhuntScoreboardManager = new ManhuntHunterScoreboardManager(this, abilitesManager, manhuntMain);
                    manhuntScoreboardManager.showHunterScoreboard(uuid, manhuntMain.getPlugin());
                    int id = manhuntScoreboardManager.id;
                    hunterScoreboardID.put(player.getUniqueId(), id);
                }
                player.spigot().respawn();
                for (String msg : manhuntMain.getConfig().getStringList("messages.start-msg")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%hunters%", hunters).replace("%speedrunners%", speedrunners)));
                }
                player.getInventory().clear();
                player.setCollidable(false);
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


                player.setHealth(20);
                player.setFoodLevel(20);
                player.setGameMode(GameMode.SURVIVAL);
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
            }
        if (Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.KEEP_INVENTORY)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
            return true;
        }
        manacounter.startMana((JavaPlugin) manhuntMain.getPlugin(), 0, manadelay);
        return true;
    }
        catch(Exception e){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cAn internal error has occurred with the plugin! The start has been aborted. It is suggested that you report this to the plugin by posting the stacktrace! The plugin will disable itself now."));
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(manhuntMain.getPlugin());
        }
        return false;
        }
        public HashMap<UUID, HashMap<String, Location>> getWaypoints(){
            return waypoints;
        }
}
