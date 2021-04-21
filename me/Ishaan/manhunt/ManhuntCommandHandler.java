package me.Ishaan.manhunt;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Effect.Type.SOUND;


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
                    Bukkit.broadcastMessage(ChatColor.RED + "The manhunt is starting!");
                for (Player player: Bukkit.getServer().getOnlinePlayers()){
                    if(speedrunner.contains(player.getName()))
                    player.getInventory().clear();
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setAllowFlight(false);
                    player.setFlying(false);

                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);

                }

               for (Player player: Bukkit.getServer().getOnlinePlayers()){
                   if(hunter.contains(player.getName())){
                       player.sendMessage(ChatColor.GREEN + "You have received your items!");
                       player.getInventory().clear();
                       player.getInventory().setItem(0,getLauncher());
                       player.getInventory().setItem(1,getLightning());
                       player.setHealth(20);
                       player.setFoodLevel(20);
                       player.setAllowFlight(true);
                       player.setFlying(true);
                       player.setGameMode(GameMode.CREATIVE);
                       player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 10);

                   }
               }

            }

            //Add Speedrunner
            else if (args[0].equalsIgnoreCase("speedrunner")) {
                Integer cmdlenght = args.length;
                if (cmdlenght > 1) {
                    if (Bukkit.getPlayer(args[1].toString()) != null) {
                        if(Bukkit.getPlayer(args[1].toString()).isOnline()) {

                            speedrunner.add(args[1].toString());

                            if (hunter.contains(args[1])) {
                                hunter.remove(args[1]);
                            }

                            sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.BOLD + Bukkit.getPlayer(args[1]).getName() + ChatColor.GREEN + " to the list of speedrunners!");
                            return true;
                        }
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

                        if(speedrunner.contains(args[1])){
                            speedrunner.remove(args[1]);
                        }

                        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.BOLD + Bukkit.getPlayer(args[1]).getName() + ChatColor.GREEN + " to the list of hunters!");
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "That player is not online!");
                    return false;
                }
                sender.sendMessage(ChatColor.RED + "Please input a name!");
                return false;
            }

            else if (args[0].equalsIgnoreCase("listgroups")){
                if (!(speedrunner.size() == 0)) {
                    sender.sendMessage(ChatColor.GREEN + "Speedrunners: " + speedrunner.toString().replaceAll("]", "").replaceAll("\\[", ""));
                } else {
                    sender.sendMessage(ChatColor.GREEN + "Spedrunners: "+ ChatColor.BOLD + ChatColor.DARK_GREEN + "There is no one in this group!");
                }
                if (!(hunter.size() == 0)) {
                    sender.sendMessage(ChatColor.RED + "Hunters: " + hunter.toString().replaceAll("]", "").replaceAll("\\[", ""));
                } else {
                    sender.sendMessage(ChatColor.RED + "Hunters: " + ChatColor.BOLD + "" + ChatColor.DARK_RED + "There is no one in this group!");
                }
                return true;
            }

        }
        return false;
    }

}
