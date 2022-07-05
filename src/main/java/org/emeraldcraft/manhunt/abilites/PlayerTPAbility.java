package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import static org.emeraldcraft.manhunt.Manhunt.getAPI;

public class PlayerTPAbility extends ManhuntAbility {
    public PlayerTPAbility(){
        super("Player TP",
                "Teleports to a player",
                0,
                0,
                Material.getMaterial(getAPI().getConfig().getFileConfig().getString("ability.player-tp.material")),
                "player-tp");
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if (speedrunner.getAsBukkitPlayer() == null) return;
        Player player = speedrunner.getAsBukkitPlayer();
        //find the nearest air block to the player
        int y = player.getLocation().getBlockY() + 5;
        Location loc;
        while(true){
            loc = new Location(player.getWorld(), player.getLocation().getBlockX(), y, player.getLocation().getBlockZ());
            if(loc.getBlock().getType() == Material.AIR
            && loc.getBlock().getRelative(BlockFace.UP).getType() == Material.AIR){
                hunter.getAsBukkitPlayer().teleport(loc);
                speedrunner.getAsBukkitPlayer().sendMessage(
                        IManhuntUtils.parseConfigMessage(
                                getAttributes().getString("msg"),
                                this,
                                speedrunner,
                                hunter,
                                null,
                                null
                        )
                );
                break;
            }
            if(y >= 512){
                hunter.getAsBukkitPlayer().sendMessage(
                        MiniMessage.miniMessage().deserialize(
                                getAttributes().getString("no-success")
                        )
                );
                break;
            }
            y++;
        }
    }
}
