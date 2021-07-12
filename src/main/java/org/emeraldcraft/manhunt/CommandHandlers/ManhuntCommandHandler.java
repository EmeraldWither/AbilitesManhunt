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
                return true;
            }
        }

        //
        // Starts the manhunt.
        //

        if (args[0].equalsIgnoreCase("start")) {
            if (sender.hasPermission("abilitiesmanhunt.start") || sender.hasPermission("abilitiesmanhunt.admin")) {
                if (!(manhuntGameManager.hasGameStarted())) {
                    if (!(hunter.isEmpty())) {
                        if (!(speedrunner.isEmpty())) {
                            try {
                                manhuntGameManager.startGame(sender, manhuntMain, manacounter, manaDelay, AbilitesManager);
                                return true;
                            } catch (Exception e) {
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
            }
        }
        //
        // Add Speedrunners
        //

        if (args[0].equalsIgnoreCase("speedrunner")) {
            if (sender.hasPermission("abilitiesmanhunt.addspeedrunner") || sender.hasPermission("abilitiesmanhunt.admin")) {
                if (!(manhuntGameManager.hasGameStarted())) {
                    int argsLength = args.length;
                    if (argsLength > 1) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            if (Bukkit.getPlayer(args[1]).isOnline()) {
                                String arg = args[1];
                                Player player = Bukkit.getPlayer(arg);
                                UUID uuid = Bukkit.getPlayer(arg).getUniqueId();
                                String name = Bukkit.getPlayer(uuid).getName();
                                if (!(speedrunner.contains(uuid))) {
                                    addTeam(SPEEDRUNNER, Bukkit.getPlayer(arg).getUniqueId());

                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&bYou have added " + name + " to the speedrunners group! "));
                                    player.sendMessage(prefix + ChatColor.RED + "You have been added to the speedrunner team by " + sender);
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
        }

        //
        // Add Hunters
        //


        if (args[0].equalsIgnoreCase("hunter")) {
            if (sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.addhunter")) {
                Integer argsLength = args.length;
                if (!(manhuntGameManager.hasGameStarted())) {
                    if (argsLength > 1) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            if (Bukkit.getPlayer(args[1]).isOnline()) {
                                String arg = args[1];
                                Player player = Bukkit.getPlayer(arg);
                                if (!(hunter.contains(player.getUniqueId()))) {
                                    addTeam(ManhuntTeam.HUNTER, Bukkit.getPlayer(arg).getUniqueId());
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&bYou have added " + player.getName() + " to the hunters group! "));
                                    player.sendMessage(prefix + ChatColor.RED + "You have been added to the speedrunner team by " + sender.getName());

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
            if (sender.hasPermission("emeraldmanhunt.reload") || sender.hasPermission("manhunt.admin")) {
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
            if (sender.hasPermission("abilitiesmanhunt.forceend") || sender.hasPermission("abilitiesmanhunt.admin")) {
                if (manhuntGameManager.hasGameStarted()) {
                    Bukkit.getScheduler().cancelTasks(manhuntMain.getPlugin());
                    endGame(sender);
                    return true;
                }
            }
            sender.sendMessage(prefix + ChatColor.RED + "There is no game in progress! You can start one with \"" + ChatColor.DARK_RED + "/manhunt start" + ChatColor.RED + "\".");
            return false;
        }
        else if(args[0].equalsIgnoreCase("setmana")) {
            if (sender.hasPermission("abilitiesmanhunt.setmana") || sender.hasPermission("abilitiesmanhunt.admin")) {
                if (args.length >= 3) {
                    if (manhuntGameManager.hasGameStarted()) {
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
        else if(args[0].equalsIgnoreCase("remove")) {
            if (sender.hasPermission("abilitiesmanhunt.removeplayer") || sender.hasPermission("abilitiesmanhunt.admin")) {
                if (args.length >= 2) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Player selectedPlayer = Bukkit.getPlayer(args[1]);
                        assert selectedPlayer != null;
                        if (hunter.contains(selectedPlayer.getUniqueId())) {
                            hunter.remove(selectedPlayer.getUniqueId());
                            sender.sendMessage(prefix + ChatColor.GREEN + selectedPlayer.getName() + " has been removed from the hunter team.");
                            selectedPlayer.sendMessage(prefix + ChatColor.RED + "You have been removed from the hunter team. ");
                        }
                        if (speedrunner.contains(selectedPlayer.getUniqueId())) {
                            speedrunner.remove(selectedPlayer.getUniqueId());
                            sender.sendMessage(prefix + ChatColor.GREEN + selectedPlayer.getName() + " has been removed from the speedrunner team.");
                            selectedPlayer.sendMessage(prefix + ChatColor.RED + "You have been removed from the speedrunner team. ");
                        }
                        return true;
                    }
                    sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(prefix + ChatColor.RED + "Command Usage: /manhunt remove <player>");
                return false;
            }
        }
        else if(args[0].equalsIgnoreCase("waypoint")){
            if(sender instanceof Player) {
                if (manhuntGameManager.hasGameStarted()) {
                    if (args.length >= 2) {
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
                            else if(args[1].equalsIgnoreCase("teleport") || args[1].equalsIgnoreCase("tp")){
                                if(manhuntGameManager.getWaypoints().containsKey(((Player) sender).getUniqueId())) {
                                    if(!(manhuntGameManager.getWaypointTeleports().get(((Player) sender).getUniqueId()) >= 3)) {
                                        HashMap<String, Location> location = manhuntGameManager.getWaypoints().get(((Player) sender).getUniqueId());
                                        for(String locName : location.keySet()){
                                            ((Player) sender).teleport(location.get(locName));
                                            int teleports = manhuntGameManager.getWaypointTeleports().get(((Player) sender).getUniqueId());
                                            manhuntGameManager.getWaypointTeleports().put(((Player) sender).getUniqueId(), (teleports + 1));
                                            teleports = manhuntGameManager.getWaypointTeleports().get(((Player) sender).getUniqueId());

                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have been teleported to the waypoint \"" + locName + "\"!"));
                                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have " + teleports + "teleport(s) remaining."));
                                            return true;
                                        }
                                    }
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have any teleports left."));
                                    return false;
                                }
                                sender.sendMessage(ChatColor.RED + "You do not have a waypoint! You can make one with /manhunt waypoint create <waypoint name> !");
                                return false;
                            }
                            sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint create <waypoint name> ");
                            sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint remove");
                            sender.sendMessage(ChatColor.RED + "Command Usage: /manhunt waypoint teleport");
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
    public void endGame(CommandSender sender) {
        if (manhuntGameManager.hasGameStarted()) {
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

    public void showHelp(CommandSender sender) {


        //Send the general commands
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6-----------------------------------\n" +
                "        &cMinecraft Abilities Manhunt \n" +
                "            &8By: EmeraldWither\n" +
                "&6\n" +
                "&7<required> (optional)\n" +
                "&7Note: You can only see commands that\n" +
                "&7you have permission to use\n" +
                "&6\n" +
                "&aGeneral Commands: &8(These can be used anytime)\n" +
                "&e/manhunt help: &6Will display this help page\n" +
                "&e/manhunt stats (player) : &6Will show the stats of a player.\n" +
                "&e/manhunt listgroups : &6Will show the currently assigned teams"));

        if (sender.hasPermission("abilitiesmanhunt.reload") || sender.hasPermission("abilitiesmanhunt.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt reload: &6Will reload the configuration files. \n"));
        }

        //Show pre-game commands
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6\n" +
                "&aPre-Game Commands: "));
        if (sender.hasPermission("abilitiesmanhunt.addspeedrunner") || sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.addhunter") || sender.hasPermission("abilitiesmanhunt.removeplayer") || sender.hasPermission("abilitiesmanhunt.start")) {
            if (sender.hasPermission("abilitiesmanhunt.start") || sender.hasPermission("abilitiesmanhunt.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt start: &6Will start the game\n"));

            }
            if (sender.hasPermission("abilitiesmanhunt.addhunter") || sender.hasPermission("abilitiesmanhunt.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt hunter <player>: &6Will start the game\n"));

            }
            if (sender.hasPermission("abilitiesmanhunt.addspeedrunner") || sender.hasPermission("abilitiesmanhunt.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt speedrunner <player>: &6Will start the game\n"));

            }
            if (sender.hasPermission("abilitiesmanhunt.removeplayer") || sender.hasPermission("abilitiesmanhunt.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt remove <player>: &6Will start the game\n"));
            }
        }
        else{
            sender.sendMessage(ChatColor.DARK_RED + "You do not have access to any pre-game commands.");
        }

        //Show ingame commands
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6\n" +
                "&aIn-Game Commands: \n" +
                "&e/manhunt waypoint create <waypoint name>: &6Will create a waypoint with the specified name\n" +
                "&e/manhunt waypoint remove: &6Will remove your created waypoint.\n" +
                "&e/manhunt waypoint teleport: &6Will teleport you to your waypoint.\n" +
                "&8(You can only teleport to your waypoint 3 times)\n"));

        if (sender.hasPermission("abilitiesmanhunt.forceend") || sender.hasPermission("abilitiesmanhunt.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt forceend: &6Will forceend the game\n"));
        }
        if (sender.hasPermission("abilitiesmanhunt.setmana") || sender.hasPermission("abilitiesmanhunt.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/manhunt setmana <player> <amount>: &6Will set the mana of the player to the desired amount\n"));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6----------------------------------"));

    }
}
