package org.emeraldcraft.manhunt.Managers;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.ScoreboardManager;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.ManHuntInventory;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.*;

public class ManhuntGameManager {

    List<String> hunter = new ArrayList<>();
    List<String> speedrunner = new ArrayList<>();
    List<String> deadSpeedrunners = new ArrayList<>();
    List<String> frozenPlayers = new ArrayList<>();
    private boolean hasGameStarted = false;

    public HashMap<String, Integer> scoreboardID = new HashMap<>();

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    org.bukkit.scoreboard.Scoreboard hunterScoreboardTeam = manager.getNewScoreboard();
    org.bukkit.scoreboard.Scoreboard speedrunnerScoreboardTeam = manager.getNewScoreboard();

    public org.bukkit.scoreboard.Team speedrunnerTeam = speedrunnerScoreboardTeam.registerNewTeam("speedrunnerTeam");
    public org.bukkit.scoreboard.Team deadSpeedrunnerTeam = speedrunnerScoreboardTeam.registerNewTeam("deadPlayersTeam");
    public org.bukkit.scoreboard.Team frozenTeam = speedrunnerScoreboardTeam.registerNewTeam("frozenPlayerTeam");
    public org.bukkit.scoreboard.Team hunterTeam = hunterScoreboardTeam.registerNewTeam("hunterTeam");


     public List<String> getTeam(ManhuntTeam team) {
        if (team == ManhuntTeam.HUNTER) {
            return hunter;
        }
        if (team == ManhuntTeam.FROZEN) {
            return frozenPlayers;
        }
        if (team == ManhuntTeam.DEAD) {
                return deadSpeedrunners;
        }
        if (team == ManhuntTeam.SPEEDRUNNER) {
            return speedrunner;
        }
        return null;
    }
    public ManhuntTeam getTeam(String playerName){
        String name = Bukkit.getPlayer(playerName).getName();

        if(hunter.contains(name)){
            return ManhuntTeam.HUNTER;
        }
        if(speedrunner.contains(name)) {
            return ManhuntTeam.SPEEDRUNNER;
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
                return ChatColor.translateAlternateColorCodes('&', "&c" + cooldown + " seconds");
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
            // Teams Creation

            // End Teams Creation

            new SpeedrunnerGUI(this, manhuntMain).createInventory();
            ManHuntInventory manHuntInventory = new ManHuntInventory();
            setGameStatus(true);
            String hunters = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");
            String speedrunners = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (getTeam(player.getName()).equals(ManhuntTeam.SPEEDRUNNER)) {
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
                    speedrunnerTeam.addEntry(player.getName());
                    player.setScoreboard(Objects.requireNonNull(getScoreboardTeam(ManhuntTeam.SPEEDRUNNER).getScoreboard()));
                    player.setGlowing(true);
                }
            }

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (getTeam(player.getName()).equals(ManhuntTeam.HUNTER)) {
                    UUID uuid = player.getUniqueId();
                    ManhuntScoreboardManager manhuntScoreboardManager = new ManhuntScoreboardManager(this, abilitesManager);
                    manhuntScoreboardManager.showHunterScoreboard(uuid, manhuntMain.plugin);
                    int id = manhuntScoreboardManager.id;
                    scoreboardID.put(player.getName(), id);

                    player.spigot().respawn();
                    for (String msg : manhuntMain.getConfig().getStringList("messages.start-msg")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%hunters%", hunters).replace("%speedrunners%", speedrunners)));
                    }
                    player.getInventory().clear();
                    manHuntInventory.giveAbility(Ability.LIGHTNING, player.getName(), 0);
                    manHuntInventory.giveAbility(Ability.LAUNCHER, player.getName(), 1);
                    manHuntInventory.giveAbility(Ability.FREEZER, player.getName(), 2);
                    manHuntInventory.giveAbility(Ability.DAMAGEITEM, player.getName(), 3);
                    manHuntInventory.giveAbility(Ability.SCRAMBLE, player.getName(), 4);
                    manHuntInventory.giveAbility(Ability.GRAVITY, player.getName(), 5);
                    manHuntInventory.giveAbility(Ability.RANDOMTP, player.getName(), 6);
                    manHuntInventory.giveAbility(Ability.TARGETMOB, player.getName(), 7);
                    manHuntInventory.giveAbility(Ability.PLAYERTP, player.getName(), 8);
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
                   // hunterTeam.addEntry(player.getName());
                    // player.setScoreboard(Objects.requireNonNull(getScoreboardTeam(ManhuntTeam.HUNTER).getScoreboard()));
                    player.setGlowing(true);
                }
            }
        if (Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.KEEP_INVENTORY)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
            return true;
        }
        manacounter.startMana((JavaPlugin) manhuntMain.plugin, 0, manadelay);
        return true;
    }
        catch(Exception e){
            sender.sendMessage(prefix + ChatColor.DARK_RED + "An internal error has occurred with the plugin! The start has been aborted");
            sender.sendMessage(prefix + ChatColor.DARK_RED + "It is suggested that you report this to the plugin by posting the stacktrace!");
            sender.sendMessage(prefix + ChatColor.DARK_RED + "It is suggested that you restart the server, and try again. ");
            e.printStackTrace();
        }
        return false;
        }
        public org.bukkit.scoreboard.Team getScoreboardTeam(ManhuntTeam team){
        if(team == ManhuntTeam.FROZEN){
            return frozenTeam;
        }
        if(team == ManhuntTeam.HUNTER){
            return hunterTeam;
        }
        if(team == ManhuntTeam.SPEEDRUNNER){
            return speedrunnerTeam;
        }
        if(team == ManhuntTeam.DEAD){
            return deadSpeedrunnerTeam;
        }
        else {
            throw new IllegalArgumentException("ManhuntTeam can only be HUNTER, SPEEDRUNNER, FROZEN, and DEAD");
        }
     }

}
