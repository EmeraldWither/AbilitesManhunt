package me.Ishaan.manhunt;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Effect.Type.SOUND;


public class ManhuntCommandHandler extends ManHuntInventory implements CommandExecutor {
    Main plugin;


    //Speedrunners
    List<String> speedrunner = SpeedrunList.getSpeedruners();

    //Hunters

    List<String> hunter = HunterList.getHunters();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String prefix = ChatColor.DARK_GREEN + "[Manhunt Abilities] ";

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
            // Starts the manhunt, will only have 1 speedrunner for now.
            //
            if(args[0].equalsIgnoreCase("start")){

                Bukkit.broadcastMessage(ChatColor.RED + "The manhunt is starting!");
                for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                    if (speedrunner.contains(player.getName())) {
                        player.getInventory().clear();
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setGameMode(GameMode.SURVIVAL);
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.setGlowing(true);

                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);

                    }
                }

                for (Player player: Bukkit.getServer().getOnlinePlayers()){
                    if(hunter.contains(player.getName())){
                        player.sendMessage(ChatColor.GREEN + "You have received your items!");
                        player.getInventory().clear();
                        player.getInventory().setItem(0,getLauncher());
                        player.getInventory().setItem(1,getLightning());
                        player.getInventory().setItem(2, getGravity());
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setGameMode(GameMode.CREATIVE);
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);

                    }
                }


            }

            if(args[0].equalsIgnoreCase("speedrunner")){

                Integer argsLength = args.length;

                if(argsLength > 1){
                    if(Bukkit.getPlayer(args[1]) != null){
                        if(Bukkit.getPlayer(args[1]).isOnline()){

                            String arg = args[1];
                            String name = Bukkit.getPlayer(arg).getName();

                            speedrunner.add(name);

                            if(hunter.contains(name)){
                                hunter.remove(name);
                            }

                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&2" + prefix + "&bYou have added " + name + " to the speedrunners group! "));
                            return true;

                        } sender.sendMessage( prefix + ChatColor.DARK_RED +"That player is not online!");
                        return false;

                    }  sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage( prefix + ChatColor.DARK_RED +"Please input a player name!");
                return false;

            }

            //
            // Hunters
            //


            if (args[0].equalsIgnoreCase("hunter")) {

                Integer argsLength = args.length;

                if (argsLength > 1) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        if (Bukkit.getPlayer(args[1]).isOnline()) {

                            String arg = args[1];
                            String name = Bukkit.getPlayer(arg).getName();

                            hunter.add(name);

                            if (speedrunner.contains(name)) {
                                speedrunner.remove(name);
                            }

                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&bYou have added " + name + " to the hunters group! "));
                            return true;

                        } sender.sendMessage( prefix + ChatColor.DARK_RED +"That player is not online!");
                        return false;

                    }  sender.sendMessage(prefix + ChatColor.DARK_RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage( prefix + ChatColor.DARK_RED +"Please input a player name!");
                return false;
            }



                //List Groups Command

        if(args[0].equalsIgnoreCase("listgroups")){

            Player player = (Player) sender;
            String speedrunnersList = speedrunner.toString().replaceAll("]","").replaceAll("\\[","");
            String huntersList = hunter.toString().replaceAll("]","").replaceAll("\\[","");

            //
            // Check if Speedrunners list is Empty
            //


            if(speedrunner.isEmpty()){

                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',   "&2" + prefix + "&cSpeedrunnner: &4There is no one in this group!"));
                player.sendMessage("");
            }

            else {
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cSpeedrunnner: &4" + speedrunnersList));
                player.sendMessage("");
            }

            //
            // Check if hunters list is empty
            //

            if(hunter.isEmpty()){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cHunters: &4There is no one in this group!"));
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
            }

            else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2" + prefix + "&cHunters: &4" + huntersList));
                player.sendMessage(ChatColor.GOLD + "-----------------------------------------");
            }

            return true;

        }
        return false;
    }
}