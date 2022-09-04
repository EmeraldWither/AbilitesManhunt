package org.emeraldcraft.manhunt.abilites;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;

public class LavaAbility extends ManhuntAbility {

    private final ManhuntMain main;
    private static int duration;

    public LavaAbility(ManhuntMain main) {
        super("Lava",
                "Sets the player on fire and puts lava on them.",
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.lava.cooldown"),
                Manhunt.getAPI().getConfig().getFileConfig().getInt("ability.lava.mana"),
                Material.getMaterial(Manhunt.getAPI().getConfig().getFileConfig().getString("ability.lava.material")),
                "lava");
        duration = getAttributes().getInt("duration");
        this.main = main;
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if (speedrunner.getAsBukkitPlayer() != null) {
            Player player = speedrunner.getAsBukkitPlayer();
            new LavaScheduler(player).runTaskTimer(main, 0, 1);
            //Minimessage start parsing config
            String lavaMsg = getAttributes().getString("msg");
            if (lavaMsg == null) return;
            //Parse into minimessage
            Component msg = (MiniMessage.miniMessage().deserialize(
                    ManhuntUtils.parseBasicMessage(lavaMsg, this, speedrunner, hunter)
            ));
            player.sendMessage(msg);
            hunter.getAsBukkitPlayer().sendMessage(
                    ManhuntUtils.parseBasicMessage(
                            getAttributes().getString("hunter-msg"),
                            this,
                            speedrunner,
                            hunter
                    )
            );
        }
    }
    static class LavaScheduler extends BukkitRunnable{
        private final Player player;
        private int timePassed = 0;
        private Block block;
        private BlockData blockData;
        private Material blockType;
        public LavaScheduler(Player player) {
            this.player = player;
        }
        @Override
        public void run() {
            //If it is the first tick that the scheduler is running (the ability was just called)
            //then set the lava block to the player's location
            if(timePassed == 0){
                block = player.getLocation().getBlock();
                blockData = player.getLocation().getBlock().getBlockData();
                blockType = player.getLocation().getBlock().getType();
                player.getLocation().getBlock().setType(Material.LAVA);
                timePassed++;
                return;
            }
            //Wait for X (config) seconds
            if(timePassed < 20 * duration){
                player.setFireTicks(80);
                player.setVisualFire(false);
                timePassed++;
                return;
            }
            //The ability period has ended, so lets set the original block back to where it was
            block.getWorld().getBlockAt(block.getLocation()).setType(blockType);
            block.getWorld().getBlockAt(block.getLocation()).setBlockData(blockData, false);
            cancel();
        }
    }
}
