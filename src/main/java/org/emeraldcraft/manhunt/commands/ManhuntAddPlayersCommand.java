package org.emeraldcraft.manhunt.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.jetbrains.annotations.NotNull;

public class ManhuntAddPlayersCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length > 2){
            commandSender.sendMessage("Wrong way kiddo.");
            return false;
        }
        if(args[0].equalsIgnoreCase("speedrunner")){
            String speedrunner = args[1];
            Player player = Bukkit.getPlayer(speedrunner);
            Manhunt.getAPI().registerPlayer(player, ManhuntTeam.SPEEDRUNNER);
            return true;
        }
        if(args[0].equalsIgnoreCase("hunter")){
            String hunter = args[1];
            Player player = Bukkit.getPlayer(hunter);
            Manhunt.getAPI().registerPlayer(player, ManhuntTeam.HUNTER);
            return true;
        }
        if(args[0].equalsIgnoreCase("start")){
            Manhunt.getAPI().testStart();
            commandSender.sendMessage("Started. ");
            return true;
        }
        if(args[0].equalsIgnoreCase("list")){
            String msg = "Hunters: ";
            for(ManhuntPlayer player : Manhunt.getAPI().getTeam(ManhuntTeam.HUNTER)) msg = msg.concat(player.getAsBukkitPlayer().getName());
            commandSender.sendMessage(msg);
            String speedrunnerMsg = "Speedrunners: ";
            for(ManhuntPlayer player : Manhunt.getAPI().getTeam(ManhuntTeam.SPEEDRUNNER)) speedrunnerMsg = speedrunnerMsg.concat(player.getAsBukkitPlayer().getName());
            commandSender.sendMessage(speedrunnerMsg);
            return true;
        }
        return false;
    }
}
