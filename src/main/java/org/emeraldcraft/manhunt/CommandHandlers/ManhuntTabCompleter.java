package org.emeraldcraft.manhunt.CommandHandlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manhunt;

import java.util.*;

public class ManhuntTabCompleter implements TabCompleter {
    private static final List<String> SUBCOMMANDS = Arrays.asList("create", "remove", "teleport", "tp");
    private static final List<String> players = new ArrayList<>();
    private static final List<String> BLANK = Arrays.asList("", "", "");
    private final Manhunt manhunt;
    public ManhuntTabCompleter(Manhunt manhunt){
     this.manhunt = manhunt;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<>();
            if(manhunt.hasGameStarted()){
                subCommands.add("listgroups");
                subCommands.add("stats");
                subCommands.add("help");
                subCommands.add("resourcepack");
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.forceend")){
                    subCommands.add("forceend");
                }
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.setmana")){
                    subCommands.add("setmana");
                }
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.reload")){
                    subCommands.add("reload");
                }
                if(sender instanceof Player && manhunt.getTeam(((Player) sender).getUniqueId()).equals(ManhuntTeam.SPEEDRUNNER)){
                    subCommands.add("waypoint");
                }
            }
            else {
                subCommands.add("listgroups");
                subCommands.add("stats");
                subCommands.add("help");
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.addhunter")){
                    subCommands.add("hunter");
                }
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.addspeedrunner")){
                    subCommands.add("speedrunner");
                }
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.removeplayer")){
                    subCommands.add("remove");
                }
                if(sender.hasPermission("abilitiesmanhunt.admin") || sender.hasPermission("abilitiesmanhunt.start")){
                    subCommands.add("start");
                }
            }
            return StringUtil.copyPartialMatches(args[0], subCommands , new ArrayList<>());
        }
        if (args.length == 2) {
            if(manhunt.hasGameStarted()){
                if (args[0].equalsIgnoreCase("setmana") ||  args[0].equalsIgnoreCase("stats") || args[0].equalsIgnoreCase("waypoint")) {
                    if (args[0].equalsIgnoreCase("setmana")) {
                        for(UUID uuid : manhunt.getTeam(ManhuntTeam.HUNTER)){
                            players.add(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());
                        }
                    }
                    else if(args[0].equalsIgnoreCase("stats")){
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            players.add(player.getName());
                        }
                    }
                    else if(args[0].equalsIgnoreCase("waypoint")){
                        return StringUtil.copyPartialMatches(args[1], SUBCOMMANDS, new ArrayList<>());
                    }
                    return StringUtil.copyPartialMatches(args[1], players, new ArrayList<>());
                }
            }
            else {
                if(args[0].equalsIgnoreCase("hunter") || args[0].equalsIgnoreCase("speedrunner") || args[0].equalsIgnoreCase("remove")){
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        players.add(player.getName());
                    }
                    return StringUtil.copyPartialMatches(args[1], players, new ArrayList<>());
                }
            }
        }
        if (args.length >= 2) {
            return StringUtil.copyPartialMatches(args[args.length - 1], BLANK, new ArrayList<>());
        }
        return null;
    }
}
