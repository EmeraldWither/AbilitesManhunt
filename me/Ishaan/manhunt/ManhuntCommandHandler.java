package me.Ishaan.manhunt;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunnerList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class ManhuntCommandHandler extends ManHuntInventory implements CommandExecutor{
    Main plugin;

    private HunterList hunters;
    private SpeedrunnerList speedrunners;



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("manhunt")) {
            if (args[0].equalsIgnoreCase("start")) {
                if (sender instanceof Player) {
                    sender.sendMessage("The manhunt is starting!");
                    ((Player) sender).getInventory().clear();
                    ((Player) sender).getInventory().setItem(0, getLauncher());
                    ((Player) sender).getInventory().setItem(1, getLightning());
                    return false;
                }
            }

            //Add Speedrunner
            else if (args[0].equalsIgnoreCase("speedrunner")) {
                if (args.length > 1) {
                    if (Bukkit.getPlayer(args[1].toString()).isOnline()) {

                        speedrunners = new SpeedrunnerList();
                        List<String> speedrunner = speedrunners.getList();

                        speedrunner.add(args[1]);


                        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GREEN + Bukkit.getPlayer(args[1]).getName() + ChatColor.GREEN + " to the list of speedrunners!");
                        sender.sendMessage("There are now " + " speedrunner(s)!" );
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(ChatColor.RED + "Please input a name!");
                return false;
            }
            //Add Hunter

            else if (args[0].equalsIgnoreCase("hunter")) {
                if (args.length > 1) {
                    if (Bukkit.getPlayer(args[1].toString()).isOnline()) {
                        // Get list
                        hunters = new HunterList();
                        List<String> hunter = hunters.getList();

                        hunter.add(args[1]);


                        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GREEN + Bukkit.getPlayer(args[1]).getName() + ChatColor.GREEN + " to the list of hunters!");
                        sender.sendMessage("There are now " + hunter.size() +   " hunters!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(ChatColor.RED + "Please input a name!");
                return false;
            }

            else if (args[0].equalsIgnoreCase("listgroups")){

                // Get Hunters List
                hunters = new HunterList();
                List<String> hunter = hunters.getList();



            }




        }
        return false;
    }

}
