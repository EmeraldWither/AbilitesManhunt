package org.emeraldcraft.manhunt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            if(Manhunt.getAPI().getPlayer(player.getUniqueId()) == null) return true;
            if(Manhunt.getAPI().getPlayer(player.getUniqueId()) instanceof Speedrunner speedrunner){
                speedrunner.getAsBukkitPlayer().sendMessage("Opening the shop! You have " + speedrunner.getCoins() + " coins.");
                Manhunt.getAPI().openShop(speedrunner);
            }
        }
        return true;
    }
}
