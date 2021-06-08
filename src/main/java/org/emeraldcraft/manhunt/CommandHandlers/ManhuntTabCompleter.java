package org.emeraldcraft.manhunt.CommandHandlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManhuntTabCompleter implements TabCompleter {
    private static final List<String> COMMANDS = Arrays.asList("hunter", "speedrunner", "start", "listgroups", "help", "reload", "forceend", "setmana");
    private static final List<String> players = new ArrayList<String>();
    private static final List<String> BLANK = Arrays.asList("", "", "");


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("hunter") || args[0].equalsIgnoreCase("speedrunner") || args[0].equalsIgnoreCase("setmana")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    players.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[1], players, new ArrayList<>());
            }
        }
        if(args.length >= 2) {
            final List<String> blank = Arrays.asList("", "", "");
            return StringUtil.copyPartialMatches(args[args.length - 1], blank, new ArrayList<>());
        }
        return null;
    }
}