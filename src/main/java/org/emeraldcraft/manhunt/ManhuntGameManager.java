package org.emeraldcraft.manhunt;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.GUI.SpeedrunnerGUI;
import org.emeraldcraft.manhunt.Mana.Manacounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManhuntGameManager {

    List<String> hunter = new ArrayList<>();
    List<String> speedrunner = new ArrayList<>();
    List<String> deadSpeedrunners = new ArrayList<>();
    List<String> frozenPlayers = new ArrayList<String>();
    private boolean hasGameStarted = false;

    ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

     org.bukkit.scoreboard.Team hunterTeam = scoreboard.registerNewTeam("hunterTeam");
     org.bukkit.scoreboard.Team speedrunnerTeam = scoreboard.registerNewTeam("speedrunnerTeam");
     public org.bukkit.scoreboard.Team deadSpeedrunnerTeam = scoreboard.registerNewTeam("deadPlayersTeam");

    public List<String> getTeam(Team team) {
        if (team == Team.HUNTER) {
            return hunter;
        }
        if (team == Team.FROZEN) {
            return frozenPlayers;
        }
        if (team == Team.DEAD) {
                return deadSpeedrunners;
        }
        if (team == Team.SPEEDRUNNER) {
            return speedrunner;
        }
        return null;
    }
    public Team getTeam(String playerName){
        String name = Bukkit.getPlayer(playerName).getName();

        if(hunter.contains(name)){
            return Team.HUNTER;
        }
        if(speedrunner.contains(name)) {
            return Team.SPEEDRUNNER;
        }
        return Team.NONE;
    }
    public boolean getGameStatus(){
        return hasGameStarted;
    }
    public void setGameStatus(boolean b){
        hasGameStarted = b;
    }
    public boolean startGame(CommandSender sender, ManhuntMain manhuntMain, Manacounter manacounter, Integer manadelay) {
        String prefix = manhuntMain.getConfig().getString("plugin-prefix");
        try {
            new SpeedrunnerGUI(this, manhuntMain).createInventory();
            hunterTeam.setColor(ChatColor.RED);
            hunterTeam.setPrefix("[HUNTER] ");
            speedrunnerTeam.setColor(ChatColor.GREEN);
            speedrunnerTeam.setPrefix("[SPEEDRUNNER] ");
            deadSpeedrunnerTeam.setColor(ChatColor.DARK_GRAY);
            deadSpeedrunnerTeam.setPrefix("[DEAD] ");
            hunterTeam.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            deadSpeedrunnerTeam.setOption(org.bukkit.scoreboard.Team.Option.COLLISION_RULE, org.bukkit.scoreboard.Team.OptionStatus.NEVER);
            ManHuntInventory manHuntInventory = new ManHuntInventory();
            setGameStatus(true);
            String hunters = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");
            String speedrunners = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (getTeam(player.getName()).equals(Team.SPEEDRUNNER)) {
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
                    player.setScoreboard(Objects.requireNonNull(getScoreboardTeam(Team.SPEEDRUNNER).getScoreboard()));
                    player.setGlowing(true);
                }
            }

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (getTeam(player.getName()).equals(Team.HUNTER)) {
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
                    hunterTeam.addEntry(player.getName());
                    player.setScoreboard(Objects.requireNonNull(getScoreboardTeam(Team.HUNTER).getScoreboard()));
                    player.setGlowing(true);
                }
            }
        if (Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.KEEP_INVENTORY).equals(true)) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
            return true;
        }
        manacounter.startMana((JavaPlugin) manhuntMain.plugin, 0, manadelay);
        return true;
    }
        catch(Exception e){
                sender.sendMessage(prefix + ChatColor.DARK_RED + "An internal error has occurred with the plugin! The start has been aborted.");
                e.printStackTrace();
            }
            return false;
        }
        public org.bukkit.scoreboard.Team getScoreboardTeam(Team team){
        if(team == Team.HUNTER){
            return hunterTeam;
        }
        if(team == Team.SPEEDRUNNER){
            return speedrunnerTeam;
        }
        if(team == Team.DEAD){
            return deadSpeedrunnerTeam;
        }
        else {
            throw new IllegalArgumentException("Team can only be HUNTER, SPEEDRUNNER, and DEAD");
        }
     }

}
