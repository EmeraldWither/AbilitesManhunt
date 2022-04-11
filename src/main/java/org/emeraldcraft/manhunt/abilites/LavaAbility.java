package org.emeraldcraft.manhunt.abilites;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.emeraldcraft.manhunt.ManhuntMain;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

public class LavaAbility extends ManhuntAbility {

    private final ManhuntMain main;

    public LavaAbility(ManhuntMain main) {
        super("Lava", "Sets the player on fire and puts lava on them.", 500, 60, Material.LAVA_BUCKET);
        this.main = main;
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if (speedrunner.getAsBukkitPlayer() != null) {
            Player player = speedrunner.getAsBukkitPlayer();
            new Scheduler(player).runTaskTimer(main, 0, 1);
            player.sendMessage("You are now on fire!");
        }
    }
    private class Scheduler extends BukkitRunnable{
        private final Player player;
        private int amount = 0;
        private Block block;
        private BlockData blockData;
        private Material blockType;
        public Scheduler(Player player) {
            this.player = player;
        }
        @Override
        public void run() {
            if(amount == 0){
                block = player.getLocation().getBlock();
                blockData = player.getLocation().getBlock().getBlockData();
                blockType = player.getLocation().getBlock().getType();
                player.getLocation().getBlock().setType(Material.LAVA);
            }
            if(amount < 10 * 20) {
                player.setFireTicks(80);
                player.setVisualFire(false);
                amount++;
            }
            else {
                block.getWorld().getBlockAt(block.getLocation()).setType(blockType);
                block.getWorld().getBlockAt(block.getLocation()).setBlockData(blockData, false);
                cancel();
            }
        }
    }
}
