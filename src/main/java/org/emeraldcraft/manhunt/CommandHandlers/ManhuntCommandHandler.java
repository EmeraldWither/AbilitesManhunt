package org.emeraldcraft.manhunt.CommandHandlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.*;

import static org.emeraldcraft.manhunt.Enums.ManhuntTeam.FROZEN;
import static org.emeraldcraft.manhunt.Enums.ManhuntTeam.SPEEDRUNNER;


public class ManhuntCommandHandler implements CommandExecutor {

    private final ManhuntGameManager manhuntGameManager;
    private final AbilitesManager AbilitesManager;
    List<UUID> speedrunner;
    List<UUID> hunter;
    List<UUID> deadSpeedrunner;
    HashMap<UUID, Integer> Mana;
    private final ManhuntMain manhuntMain;
    private Manacounter manacounter;

    public ManhuntCommandHandler(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager) {
        this.manhuntGameManager = manhuntGameManager;
        this.AbilitesManager = AbilitesManager;
        this.speedrunner = manhuntGameManager.getTeam(SPEEDRUNNER);
        this.hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        this.deadSpeedrunner = manhuntGameManager.getTeam(ManhuntTeam.DEAD);
        this.manhuntMain = manhuntMain;
        this.manacounter = manacounter;
        this.Mana = manacounter.getManaList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(manhuntMain.getConfig().getString("plugin-prefix")));
        Integer manaDelay = manhuntMain.getConfig().getInt("mana-delay");
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
                            manhuntGameManager.startGame(sender, manhuntMain, manacounter, manaDelay, AbilitesManager);
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
                            UUID uuid = Bukkit.getPlayer(arg).getUniqueId();
                            String name = Bukkit.getPlayer(uuid).getName();

                            if (!(speedrunner.contains(uuid))) {
                                addTeam(SPEEDRUNNER, Bukkit.getPlayer(arg).getUniqueId());

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

                                addTeam(ManhuntTeam.HUNTER, Bukkit.getPlayer(arg).getUniqueId());

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

            List<String> speedrunnerList = new ArrayList<>();
            List<String> hunterList = new ArrayList<>();

            for(UUID uuid : speedrunner){
                speedrunnerList.add(Bukkit.getPlayer(uuid).getName());
            }
            for(UUID uuid : hunter){
                hunterList.add(Bukkit.getPlayer(uuid).getName());
            }

            String speedrunnersList = speedrunnerList.toString().replaceAll("]", "").replaceAll("\\[", "");
            String huntersList = hunterList.toString().replaceAll("]", "").replaceAll("\\[", "");

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
                manhuntMain.reloadConfig();
                prefix = manhuntMain.getConfig().getString("plugin-prefix");
                String msg = manhuntMain.getConfig().getString("config-reload-msg");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg));
                return true;
            }
            String invalidPerms = manhuntMain.getConfig().getString("permission-denied-msg");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + invalidPerms));
        }

        if (args[0].equalsIgnoreCase("forceend")) {
            if (manhuntGameManager.getGameStatus()) {
                if (sender.hasPermission("manhunt.forceend")) {
                    Bukkit.getScheduler().cancelTasks(manhuntMain.getPlugin());
                    endGame(sender);
                    return true;
                }
            }
            sender.sendMessage(prefix + ChatColor.RED + "There is no game in progress! You can start one with \"" + ChatColor.DARK_RED + "/manhunt start" + ChatColor.RED + "\".");
            return false;
        }
        else if(args[0].equalsIgnoreCase("setmana")) {
            if(args.length >= 3) {
                if (manhuntGameManager.getGameStatus()) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (hunter.contains(Bukkit.getPlayer(args[1]).getUniqueId())) {
                            Player player = Bukkit.getPlayer(args[1]);
                            try {
                                manacounter.getManaList().put(player.getUniqueId(), Integer.parseInt(args[2]));
                                manacounter.updateActionbar(player);
                                Bukkit.broadcastMessage(prefix + ChatColor.DARK_GREEN + sender.getName() + ChatColor.GREEN + " successfully set the mana of " + ChatColor.DARK_GREEN + player.getName() + ChatColor.GREEN + " to " + ChatColor.DARK_GREEN + args[2] + ChatColor.GREEN + "!");
                            } catch (NumberFormatException e) {
                                sender.sendMessage(prefix + ChatColor.DARK_RED + "Please input a integer!");
                                return true;
                            }
                            return true;
                        }
                        sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not a hunter!");
                        return false;
                    }
                    sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(prefix + ChatColor.DARK_RED + "There is no game in progess! You can start one with /manhunt start");
                return false;
            }
            sender.sendMessage(prefix + ChatColor.DARK_RED + "Command Usage: /manhunt setmana <player> <amount>");
            return false;
        }
        else if(args[0].equalsIgnoreCase("stats")){
            if(args.length == 1){
                showStats(sender);
                return true;
            }
            if(args.length >= 2){
                if(Bukkit.getPlayer(args[1]) != null) {
                    Player selectedPlayer = Bukkit.getPlayer(args[1]);
                    assert selectedPlayer != null;
                    showStats(selectedPlayer, sender);
                    return true;
                }
                sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                return false;
            }

            sender.sendMessage(prefix + ChatColor.RED + "Command Usage: /manhunt stats <player (optional)>");
            return false;
        }
        else if(args[0].equalsIgnoreCase("remove")){
            if(args.length >= 2){
                if(Bukkit.getPlayer(args[1]) != null) {
                    Player selectedPlayer = Bukkit.getPlayer(args[1]);
                    assert selectedPlayer != null;
                    if(hunter.contains(selectedPlayer.getName())){
                        hunter.remove(selectedPlayer.getName());
                        sender.sendMessage(prefix + ChatColor.GREEN + "Success!");
                    }
                    if(speedrunner.contains(selectedPlayer.getName())){
                        speedrunner.remove(selectedPlayer.getName());
                        sender.sendMessage(prefix + ChatColor.GREEN + "Success!");
                    }
                    return true;
                }
                sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                return false;
            }
            sender.sendMessage(prefix + ChatColor.RED + "Command Usage: /manhunt remove <player>");
            return false;
        }
        else if(args[0].equalsIgnoreCase("waypoint")){
            if(sender instanceof Player) {
                if (manhuntGameManager.getGameStatus()) {
                    if (args.length >= 2) {
                        sender.sendMessage(manhuntGameManager.getTeam(((Player) sender).getUniqueId()).toString());
                        if (manhuntGameManager.getTeam(((Player) sender).getUniqueId()) == SPEEDRUNNER || manhuntGameManager.getTeam(((Player) sender).getUniqueId()) == FROZEN) {
                            if (args[1].equalsIgnoreCase("create")) {
                                if (args.length >= 3) {
                                    if (manhuntGameManager.getWaypoints().containsKey(((Player) sender).getUniqueId())) {
                                        sender.sendMessage(ChatColor.RED + "You already have a waypoint!");
                                        return false;
                                    }
                                    HashMap<UUID, HashMap<String, Location>> waypoints = manhuntGameManager.getWaypoints();
                                    HashMap<String, Location> waypoint = new HashMap<>();
                                    StringBuilder name = new StringBuilder(args[2]);
                                    for (int arg = 3; arg < args.length; arg++) {
                                        name.append(" ").append(args[arg]);
                                    }

                                    if(!(name.length() > 50)) {
                                        waypoint.put(name.toString(), ((Player) sender).getLocation());
                                        waypoints.put(((Player) sender).getUniqueId(), waypoint);
                                        sender.sendMessage(ChatColor.GREEN + "Successfully created waypoint \"" + name + "\" at the location " + ChatColor.DARK_GREEN + waypoint.get(name.toString()).getBlockX() + ", " + waypoint.get(name.toString()).getBlockY() + ", " + waypoint.get(name.toString()).getBlockZ());
                                        return true;

                                    }
                                    sender.sendMessage(ChatColor.RED + "Your waypoint name cannot be longer than 51 characters!");
                                    return false;
                                }
                                sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint create <waypoint name> ");
                                return false;
                            }
                            if (args[1].equalsIgnoreCase("remove")) {
                                if (!manhuntGameManager.getWaypoints().containsKey(((Player) sender).getUniqueId())) {
                                    sender.sendMessage(ChatColor.RED + "You do not have a waypoint! You can make one with /manhunt waypoint create <waypoint name> !");
                                    return false;
                                }
                                HashMap<UUID, HashMap<String, Location>> waypoints = manhuntGameManager.getWaypoints();
                                waypoints.remove(((Player) sender).getUniqueId());
                                sender.sendMessage(ChatColor.GREEN + "Successfully removed waypoint. ");
                                return true;
                            }
                            sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint create <waypoint name> ");
                            sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint remove");
                            return false;
                        }
                        sender.sendMessage(ChatColor.RED + "You must be an alive speedrunner to use this command!");
                        return false;
                    }
                    sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint create <waypoint name> ");
                    sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint remove");
                    return false;
                }
                sender.sendMessage(ChatColor.RED + "There is no ongoing game in progress! Start a game with /manhunt start!");
                return false;
            }
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        showHelp(sender);

        return false;
    }


    public void addTeam(ManhuntTeam team, UUID uuid){
        if(team.equals(ManhuntTeam.HUNTER)){
            hunter.add(uuid);

            if(speedrunner.contains(uuid)){
                speedrunner.remove(uuid);
            }
        }
        if(team.equals(SPEEDRUNNER)){
            speedrunner.add(uuid);
            if(hunter.contains(uuid)){
                hunter.remove(uuid);
            }
        }
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
                        "&e/manhunt stats <player (optional)>: Gets the selected players stats. If there is no player, it will pick the one who is sending the command. (Note: The player must be online)\n" +
                        "&e/manhunt remove <player> : Removes the player from any group. \n" +
                        "&e/manhunt waypoint create <name> : Creates a waypoint with a name.  \n" +
                        "&e/manhunt waypoint remove : Deletes your waypoint. \n" +
                        "&4--------------------------------"));
    }
    public void endGame(CommandSender sender) {
        if (manhuntGameManager.getGameStatus()) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                players.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
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
            for (String msg : manhuntMain.getConfig().getStringList("messages.force-end-msg")) {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", sender.getName())));
            }
            speedrunner.clear();
            hunter.clear();
            deadSpeedrunner.clear();
            manacounter.cancelMana();
            manacounter.clearMana();
            manhuntGameManager.setGameStatus(false);
            AbilitesManager.clearCooldown();
            manhuntGameManager.getTeam(FROZEN).clear();
            manhuntGameManager.getWaypoints().clear();
            for(org.bukkit.scoreboard.Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()){
                if(team.getName().equals("hunterTeam") || team.getName().equals("speedrunnerTeam")){
                    team.unregister();
                }
            }
        }
    }
    public void showStats(Player player, CommandSender sender){
        int losses = 0;
        int deaths = 0;
        int wins = 0;
        if(manhuntMain.data.getConfig().contains("players." + player.getUniqueId().toString() + ".losses")){
            losses = manhuntMain.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".losses");
        }
        if(manhuntMain.data.getConfig().contains("players." + player.getUniqueId().toString() + ".deaths")){
            deaths = manhuntMain.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".deaths");
        }
        if(manhuntMain.data.getConfig().contains("players." + player.getUniqueId().toString() + ".wins")){
            wins = manhuntMain.data.getConfig().getInt("players." + player.getUniqueId().toString() + ".wins");
        }
        for(String msg : manhuntMain.getConfig().getStringList("messages.stats-msg")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%wins%", Integer.toString(wins)).replace("%losses%", Integer.toString(losses)).replace("%deaths%", Integer.toString(deaths)).replace("%player%", player.getName())));
        }
    }
    public void showStats(CommandSender sender){
        if(sender instanceof Player) {
            int losses = 0;
            int deaths = 0;
            int wins = 0;
            if (manhuntMain.data.getConfig().contains("players." + ((Player) sender).getPlayer().getUniqueId().toString() + ".losses")) {
                losses = manhuntMain.data.getConfig().getInt("players." + ((Player) sender).getPlayer().getUniqueId().toString() + ".losses");
            }
            if (manhuntMain.data.getConfig().contains("players." + ((Player) sender).getPlayer().getUniqueId().toString() + ".deaths")) {
                deaths = manhuntMain.data.getConfig().getInt("players." + ((Player) sender).getPlayer().getUniqueId().toString() + ".deaths");
            }
            if (manhuntMain.data.getConfig().contains("players." + ((Player) sender).getPlayer().getUniqueId().toString() + ".wins")) {
                wins = manhuntMain.data.getConfig().getInt("players." + ((Player) sender).getPlayer().getUniqueId().toString() + ".wins");
            }
            for (String msg : manhuntMain.getConfig().getStringList("messages.stats-msg")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%wins%", Integer.toString(wins)).replace("%losses%", Integer.toString(losses)).replace("%deaths%", Integer.toString(deaths)).replace("%player%", ((Player) sender).getPlayer().getName())));
            }
            return;
        }
        sender.sendMessage(ChatColor.RED + "Only a player can check their own stats! To see a players stats, type \"/manhunt stats <player>\"! (Note: They must be online)");
    }
}
