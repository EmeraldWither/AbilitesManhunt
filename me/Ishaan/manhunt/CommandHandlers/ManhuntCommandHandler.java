package me.Ishaan.manhunt.CommandHandlers;

import me.Ishaan.manhunt.Enums.Ability;
import me.Ishaan.manhunt.Enums.Team;
import me.Ishaan.manhunt.ManHuntInventory;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class ManhuntCommandHandler implements CommandExecutor {


    //Speedrunners
    List<String> speedrunner = SpeedrunList.getSpeedruners();
    //Hunters
    List<String> hunter = HunterList.getHunters();

    public static boolean HasGameStarted = false;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_GREEN + "[Manhunt Abilities] ";

        //
        // Help Commands
        //

        if (label.equalsIgnoreCase("manhunt")) {

            Integer commandLength = args.length;

            if(commandLength == 0){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4---------------------------------\n" +
                                "&cMinecraft Manhunt, but it's with Special Abilities\n" +
                                "&7By: EmeraldWitherYT\n" +
                                "&f \n" +
                                "&aCommand Usage: \n" +
                                "&e/manhunt start : Starts the manhunt\n" +
                                "&e/manhunt hunter <player> : Adds a player to the hunter group.\n" +
                                "&e/manhunt speedrunner <player>: Adds a player to the speedrunner group. \n" +
                                "&e/manhunt listgroups: Shows which players are in which groups\n" +
                                "&4--------------------------------"));
                return false;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&4---------------------------------\n" +
                                "&cMinecraft Manhunt, but it's with Special Abilities\n" +
                                "&7By: EmeraldWitherYT\n" +
                                "&f \n" +
                                "&aCommand Usage: \n" +
                                "&e/manhunt start : Starts the manhunt\n" +
                                "&e/manhunt hunter <player> : Adds a player to the hunter group.\n" +
                                "&e/manhunt speedrunner <player>: Adds a player to the speedrunner group. \n" +
                                "&e/manhunt listgroups: Shows which players are in which groups\n" +
                                "&4--------------------------------"));
                return true;
            }
        }



        //
        // Starts the manhunt.
        //

        if(args[0].equalsIgnoreCase("start")){
            if(!(hunter.isEmpty())) {
                if(!(speedrunner.isEmpty())) {

                    ManHuntInventory manHuntInventory = new ManHuntInventory();
                    HasGameStarted = true;

                    Bukkit.broadcastMessage(ChatColor.RED + "The manhunt is starting!");
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (speedrunner.contains(player.getName())) {
                            player.getInventory().clear();
                            player.setHealth(20);
                            player.setFoodLevel(20);
                            player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                            player.setGameMode(GameMode.SURVIVAL);
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            player.setGlowing(true);
                            player.setInvulnerable(false);


                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);

                        }
                    }

                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (hunter.contains(player.getName())) {
                            player.sendMessage(ChatColor.GREEN + "You have received your items!");
                            player.getInventory().clear();
                            manHuntInventory.giveAbility(Ability.LAUNCHER, player.getName());
                            manHuntInventory.giveAbility(Ability.LIGHTNING, player.getName());
                            manHuntInventory.giveAbility(Ability.GRAVITY, player.getName());
                            manHuntInventory.giveAbility(Ability.SCRAMBLE, player.getName());
                            manHuntInventory.giveAbility(Ability.RANDOMTP, player.getName());
                            manHuntInventory.giveAbility(Ability.DAMAGEITEM, player.getName());
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

                        }
                    }
                    if(sender instanceof Player) {
                        if (((Player) sender).getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY).equals(true)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
                            return true;
                        }
                    }
                    if(!(sender instanceof Player)) {
                        if (Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.KEEP_INVENTORY).equals(true)) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4WARNING : Keep Inventory is ENABLED. This may cause problems"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4such as speedrunners inventories not dropping when they die."));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&4To fix this, please run &c\"/gamerule keepInventory false\"&4!"));
                            return true;
                        }
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "There are no players in the speedrunners group!");
                return false;
            }

            sender.sendMessage(ChatColor.RED + "There are no players in the hunters group!");
            return false;

        }
        //
        // Add Speedrunners
        //

        if(args[0].equalsIgnoreCase("speedrunner")) {

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
                        } sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is already a speedrunner!");
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

        //
        // Add Hunters
        //


        if (args[0].equalsIgnoreCase("hunter")) {

            Integer argsLength = args.length;

            if (argsLength > 1) {
                if (Bukkit.getPlayer(args[1]) != null) {
                    if (Bukkit.getPlayer(args[1]).isOnline()) {

                        String arg = args[1];
                        String name = Bukkit.getPlayer(arg).getName();

                        if (!(hunter.contains(name))) {

                        addTeam(Team.HUNTER, name);

                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&bYou have added " + name + " to the hunters group! "));
                        return true;
                    } sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is already a hunter!");
                    return false;

                    } sender.sendMessage( prefix + ChatColor.DARK_RED +"That player is not online!");
                    return false;

                }  sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                return false;
            }
            sender.sendMessage( prefix + ChatColor.DARK_RED +"Please input a player name!");
            return false;
        }



        //List Groups Command

        if(args[0].equalsIgnoreCase("listgroups")) {

            CommandSender player = sender;
            String speedrunnersList = speedrunner.toString().replaceAll("]", "").replaceAll("\\[", "");
            String huntersList = hunter.toString().replaceAll("]", "").replaceAll("\\[", "");

            //
            // Check if Speedrunners list is Empty
            //


            if (speedrunner.isEmpty()) {

                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cSpeedrunnner: &4There is no one in this group!"));
                player.sendMessage("");
            } else {
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cSpeedrunnner: &4" + speedrunnersList));
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
        //Bukkit Runnable
        // Test only right now
       /* if (args[0].equalsIgnoreCase("runnable")){
            sender.sendMessage("Started Countdown");
            JavaPlugin javaPlugin = new Main().javaPlugin;
            ManaCounter manaCounter = new ManaCounter();
            manaCounter.run();
            sender.sendMessage("Finished Countdown");
        }*/
        return false;

    }

    public void addTeam(Team team, String name){
        Player player = Bukkit.getPlayer(name);
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
        return null;
    }
    public boolean hasGameStarted(){
        return HasGameStarted;
    }
    public void setGameStarted(boolean b){
        HasGameStarted = b;
    }
}
