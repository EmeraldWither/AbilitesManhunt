package me.Ishaan.manhunt.CommandHandlers;

import me.Ishaan.manhunt.Abilties.CooldownsManager;
import me.Ishaan.manhunt.Enums.Ability;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.Main;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.Mana.Manacounter;
import me.Ishaan.manhunt.ManhuntGameManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ManhuntCommandHandler implements CommandExecutor {

    private ManhuntGameManager manhuntGameManager;
    private CooldownsManager cooldownsManager;
    List<String> speedrunner;
    List<String> hunter;
    List<String> deadSpeedrunner;
    HashMap<String, Integer> Mana;
    private Main main;
    private Manacounter manacounter;

    public ManhuntCommandHandler(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, CooldownsManager cooldownsManager) {
        this.manhuntGameManager = manhuntGameManager;
        this.cooldownsManager = cooldownsManager;
        this.speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        this.hunter = manhuntGameManager.getTeam(Team.HUNTER);
        this.deadSpeedrunner = manhuntGameManager.getTeam(Team.DEAD);
        this.main = main;
        this.manacounter = manacounter;
        this.Mana = manacounter.getManaList();
    }





    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        String prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(main.getConfig().getString("plugin-prefix")));

        //
        // Help Commands
        //

        if (label.equalsIgnoreCase("manhunt")) {

            Integer commandLength = args.length;

            if (commandLength == 0) {
                showHelp(sender);
                return false;
            }
            if (args[0].equalsIgnoreCase("help")) {
                showHelp(sender);
            }
        }


        //
        // Starts the manhunt.
        //

        if (args[0].equalsIgnoreCase("start")) {
            if (!(manhuntGameManager.getGameStatus())) {
                if (!(hunter.isEmpty())) {
                    if (!(speedrunner.isEmpty())) {

                        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
                        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

                        org.bukkit.scoreboard.Team hunterTeam = scoreboard.registerNewTeam("hunterTeam");
                        org.bukkit.scoreboard.Team speedrunnerTeam = scoreboard.registerNewTeam("speedrunnerTeam");

                        hunterTeam.setColor(ChatColor.DARK_RED);
                        speedrunnerTeam.setColor(ChatColor.DARK_GREEN);

                        ManHuntInventory manHuntInventory = new ManHuntInventory();
                        manhuntGameManager.setGameStatus(true);

                        for (String msg : main.getConfig().getStringList("messages.start-msg")) {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (getTeam(player.getName()).equals(Team.SPEEDRUNNER)) {
                                player.getInventory().clear();
                                player.setHealth(20);
                                player.setFoodLevel(20);
                                player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                                player.setGameMode(GameMode.SURVIVAL);
                                player.setAllowFlight(false);
                                player.setFlying(false);
                                player.setGlowing(true);
                                player.setInvulnerable(false);
                                for(PotionEffect potionEffect: player.getActivePotionEffects()){
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
                            }
                        }

                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (getTeam(player.getName()).equals(Team.HUNTER)) {
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
                                player.setGlowing(true);
                                player.setSaturation(10000);
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);
                                for(PotionEffect potionEffect: player.getActivePotionEffects()){
                                    player.removePotionEffect(potionEffect.getType());
                                }
                                PotionEffect potionEffect = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 2);
                                potionEffect.withParticles(false);
                                player.addPotionEffect(potionEffect);
                                hunterTeam.addEntry(player.getName());
                            }
                        }
                        if (sender instanceof Player) {
                            if (((Player) sender).getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY).equals(true)) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
                                return true;
                            }
                        }
                        if (!(sender instanceof Player)) {
                            if (Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.KEEP_INVENTORY).equals(true)) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
                                return true;
                            }
                        }
                        manacounter.startMana((JavaPlugin) main.plugin, 0, 20);
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "There are no players in the speedrunners group!");
                    return false;
                }

                sender.sendMessage(ChatColor.RED + "There are no players in the hunters group!");
                return false;

            }
            sender.sendMessage(ChatColor.RED + "There is already a game in progress! You can force end it \"" + ChatColor.DARK_RED + "/manhunt forceend" + ChatColor.RED + "\".");
            return false;
        }
        //
        // Add Speedrunners
        //

        if (args[0].equalsIgnoreCase("speedrunner")) {
            if (!(manhuntGameManager.getGameStatus())) {

                Integer argsLength = args.length;
                if (argsLength > 1) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Bukkit.getPlayer(args[1]).isOnline()) {
                            String arg = args[1];
                            String name = Bukkit.getPlayer(arg).getName();

                            if (!(speedrunner.contains(name))) {

                                addTeam(Team.SPEEDRUNNER, name);

                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&bYou have added " + name + " to the speedrunners group! "));
                                return true;
                            }
                            sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is already a speedrunner!");
                            return false;
                        }
                        sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                        return false;
                    }
                    sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(prefix + ChatColor.DARK_RED + "Please input a player name!");
                return false;
            }
            sender.sendMessage(ChatColor.RED + "There is a game in progress! You can force end it \"" + ChatColor.DARK_RED + "/manhunt forceend" + ChatColor.RED + "\".");
            return false;
        }

        //
        // Add Hunters
        //


        if (args[0].equalsIgnoreCase("hunter")) {

            Integer argsLength = args.length;
            if (!(manhuntGameManager.getGameStatus())) {
                if (argsLength > 1) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Bukkit.getPlayer(args[1]).isOnline()) {

                            String arg = args[1];
                            String name = Bukkit.getPlayer(arg).getName();

                            if (!(hunter.contains(name))) {

                                addTeam(Team.HUNTER, name);

                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&bYou have added " + name + " to the hunters group! "));
                                return true;
                            }
                            sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is already a hunter!");
                            return false;

                        }
                        sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                        return false;

                    }
                    sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(prefix + ChatColor.DARK_RED + "Please input a player name!");
                return false;
            }
            sender.sendMessage(ChatColor.RED + "There is a game in progress! You can force end it \"" + ChatColor.DARK_RED + "/manhunt forceend" + ChatColor.RED + "\".");
            return false;
        }


        //List Groups Command

        if (args[0].equalsIgnoreCase("listgroups")) {

            CommandSender player = sender;
            String speedrunnersList = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");
            String huntersList = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");

            //
            // Check if Speedrunners list is Empty
            //


            if (speedrunner.isEmpty()) {

                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cspeedrunner: &4There is no one in this group!"));
                player.sendMessage("");
            } else {
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cspeedrunner: &4" + speedrunnersList));
                player.sendMessage("");
            }

            //
            // Check if hunters list is empty
            //

            if (hunter.isEmpty()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cHunters: &4There is no one in this group!"));
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cHunters: &4" + huntersList));
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
            }

            return true;
        }


        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("manhunt.reload") || sender.hasPermission("manhunt.admin")) {
                main.reloadConfig();
                prefix = main.getConfig().getString("plugin-prefix");
                String msg = main.getConfig().getString("config-reload-msg");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                return true;
            }
            String invalidPerms = main.getConfig().getString("permission-denied-msg");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + invalidPerms));
        }

        if (args[0].equalsIgnoreCase("forceend")) {
            if (manhuntGameManager.getGameStatus()) {
                if (sender.hasPermission("manhunt.forceend")) {
                    endGame(sender);
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED + "There is no game in progress! You can start one with \"" + ChatColor.DARK_RED + "/manhunt start" + ChatColor.RED + "\".");
            return false;
        }
        return false;
    }


    public void addTeam(Team team, String name){
        if(team.equals(Team.HUNTER)){
            hunter.add(name);
            if(speedrunner.contains(name)){
                speedrunner.remove(name);
            }
        }
        if(team.equals(Team.SPEEDRUNNER)){
            speedrunner.add(name);
            if(hunter.contains(name)){
                hunter.remove(name);
            }
        }
        return;
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

    public void showHelp(CommandSender sender){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&4---------------------------------\n" +
                        "&cMinecraft Manhunt, but it's with Special Abilities\n" +
                        "&7By: EmeraldWitherYT\n" +
                        "&f \n" +
                        "&aCommand Usage: \n" +
                        "&e/manhunt start : Starts the manhunt\n" +
                        "&e/manhunt hunter <player> : Adds a player to the hunter group.\n" +
                        "&e/manhunt speedrunner <player>: Adds a player to the speedrunner group. \n" +
                        "&e/manhunt listgroups: Shows which players are in which groups.\n" +
                        "&e/manhunt help: Shows this help page.\n" +
                        "&e/manhunt reload: Reloads the configuration files.\n" +
                        "&e/manhunt forceend: Forcefully ends the game.\n" +
                        "&4--------------------------------"));
    }
    public void endGame(CommandSender sender) {
        if (manhuntGameManager.getGameStatus()) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.setGlowing(false);
                players.getInventory().clear();
                players.setGameMode(GameMode.SURVIVAL);
                players.setInvulnerable(false);
                players.closeInventory();
                players.setFlying(false);
                players.setAllowFlight(false);
                players.setSaturation(5);
                for(PotionEffect potionEffect: players.getActivePotionEffects()){
                    players.removePotionEffect(potionEffect.getType());
                }

            }
            for (String msg : main.getConfig().getStringList("messages.force-end-msg")) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", sender.getName())));
            }
            speedrunner.clear();
            hunter.clear();
            deadSpeedrunner.clear();
            manacounter.cancelMana();
            manhuntGameManager.setGameStatus(false);
            cooldownsManager.clearCooldown();
            manhuntGameManager.getTeam(Team.FROZEN).clear();
            for(org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()){
                if(team.getName().equals("hunterTeam") || team.getName().equals("speedrunnerTeam")){
                    team.unregister();
                }
            }
        }
    }
}
