package org.emeraldcraft.manhunt.CommandHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ManhuntCommandHandler implements CommandExecutor {

    private final ManhuntGameManager manhuntGameManager;
    private final AbilitesManager AbilitesManager;
    List<String> speedrunner;
    List<String> hunter;
    List<String> deadSpeedrunner;
    HashMap<String, Integer> Mana;
    private final Main main;
    private Manacounter manacounter;

    public ManhuntCommandHandler(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntGameManager = manhuntGameManager;
        this.AbilitesManager = AbilitesManager;
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
        Integer manaDelay = main.getConfig().getInt("mana-delay");
        //
        // Help Commands
        //

        if (label.equalsIgnoreCase("manhunt")) {

            int commandLength = args.length;

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
                        try {
                            manhuntGameManager.startGame(sender, main, manacounter, manaDelay);
                            return true;
                        }
                        catch (Exception e){
                            sender.sendMessage(prefix + ChatColor.DARK_RED + "An internal error has occurred! As such, the manhunt has been aborted.");
                        }
                    }
                    sender.sendMessage(ChatColor.DARK_RED + "There are no players in the speedrunners group!");
                    return false;
                    }
                sender.sendMessage(ChatColor.DARK_RED + "There are no players in the hunters group!");
                return false;
                }
            sender.sendMessage(ChatColor.RED + "There is already a game in progress! You can force end it with  \"" + ChatColor.DARK_RED + "/manhunt forceend" + ChatColor.RED + "\".");
            return false;
            }//
        // Add Speedrunners
        //

        if (args[0].equalsIgnoreCase("speedrunner")) {
            if (!(manhuntGameManager.getGameStatus())) {

                int argsLength = args.length;
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
            sender.sendMessage(ChatColor.RED + "There is a game in progress! You can force end it with  \"" + ChatColor.DARK_RED + "/manhunt forceend" + ChatColor.RED + "\".");
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
            sender.sendMessage(ChatColor.RED + "There is a game in progress! You can force end it with  \"" + ChatColor.DARK_RED + "/manhunt forceend" + ChatColor.RED + "\".");
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
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cSpeedrunners: &4There is no one in this group!"));
                player.sendMessage("");
            } else {
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cSpeedrunner: &4" + speedrunnersList));
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
        else if(args[0].equalsIgnoreCase("setmana")) {
            if(args.length >= 3) {
                if (manhuntGameManager.getGameStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (hunter.contains(Bukkit.getPlayer(args[1]).getName())) {
                            Player player = Bukkit.getPlayer(args[1]);
                            try {
                                manacounter.getManaList().put(player.getName(), Integer.parseInt(args[2]));
                                manacounter.updateActionbar(player);
                                Bukkit.broadcastMessage(prefix + ChatColor.DARK_GREEN + sender.getName() + ChatColor.GREEN + " successfully set the mana of " + ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + " to " + ChatColor.DARK_GREEN + args[2] + ChatColor.GREEN + "!");
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatColor.DARK_RED + "Please input a integer!");
                            }
                        }
                        sender.sendMessage(ChatColor.DARK_RED + "That player is not a hunter!");
                        return false;
                    }
                    sender.sendMessage(ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(ChatColor.DARK_RED + "There is no game in progess! You can start one with /manhunt start");
                return false;
            }
            sender.sendMessage(ChatColor.DARK_RED + "Command Usage: /manhunt setmana <player> <amount>");
            return false;
        }

        showHelp(sender);

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
                        "&e/manhunt setmana <player> <mana>: Sets the mana of the player to the selected amount.\n" +
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
            manacounter.clearMana();
            manhuntGameManager.setGameStatus(false);
            AbilitesManager.clearCooldown();
            manhuntGameManager.getTeam(Team.FROZEN).clear();
            for(org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()){
                if(team.getName().equals("hunterTeam") || team.getName().equals("speedrunnerTeam")){
                    team.unregister();
                }
            }
        }
    }
}