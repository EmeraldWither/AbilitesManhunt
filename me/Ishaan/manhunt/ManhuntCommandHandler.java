package me.Ishaan.manhunt;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public class ManhuntCommandHandler extends ManHuntInventory implements CommandExecutor{
    Main plugin;


    //Speedrunners

    private SpeedrunList speedrunList;
    SpeedrunList Speedrunners = new SpeedrunList();
    List<String> speedrunner = Speedrunners.getList();

    //Hunters

    private HunterList hunterlist;
    HunterList hunters = new HunterList();
    List<String> hunter = hunters.getList();


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

                        speedrunner.add(args[1].toString());
                        sender.sendMessage(speedrunner.toString());


                        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GREEN + Bukkit.getPlayer(args[1]).getName() + ChatColor.GREEN + " to the list of speedrunners!");
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

                        hunter.add(args[1].toString());
                        sender.sendMessage(hunter.toString());


                        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.GREEN + Bukkit.getPlayer(args[1]).getName() + ChatColor.GREEN + " to the list of hunters!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(ChatColor.RED + "Please input a name!");
                return false;
            }

            else if (args[0].equalsIgnoreCase("listgroups")){
                sender.sendMessage(speedrunner.toString());
                sender.sendMessage(hunter.toString());
            }




        }
        return false;
    }

}
