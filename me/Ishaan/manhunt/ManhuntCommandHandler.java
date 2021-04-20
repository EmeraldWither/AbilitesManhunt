package me.Ishaan.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;


public class ManhuntCommandHandler extends ManHuntInventory implements CommandExecutor{
    Main plugin;

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
                if(args.length > 0){
                    if(Bukkit.getServer().getPlayer(args[1].toString()).getName() != null){
                        sender.sendMessage("Added " + Bukkit.getPlayer(args[1]).getName() + "to the list of speedrunners!");
                    }
                    sender.sendMessage("That player is not online!");
                    return false;
                }
                sender.sendMessage("Please input a player name!");
                return false;
            }

        }
        return false;
    }

}
