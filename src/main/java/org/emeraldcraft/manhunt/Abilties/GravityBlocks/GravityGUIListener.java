package org.emeraldcraft.manhunt.Abilties.GravityBlocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Manacounter;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GravityGUIListener implements Listener {

    String ability = "Gravity Blocks";

    Map<UUID, Long> gravityCooldown;

    private ManhuntMain manhuntMain;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager abilitesManager;
    List<UUID> hunter;
    List<UUID> speedrunner;
    public GravityGUIListener(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.manhuntMain = manhuntMain;
        this.abilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        gravityCooldown = abilitesManager.getCooldown(Ability.GRAVITY);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() instanceof SkullMeta) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.GRAVITY)) {
                if (gravityCooldown.containsKey(player.getUniqueId())) {
                    if (gravityCooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((gravityCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }
                SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                int radius = manhuntMain.getConfig().getInt("abilities.gravity.radius");
                for (Block block : getBlocks(selectedPlayer.getLocation().getBlock(), radius)) {
                    if (block.getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                        BlockData blockdata = block.getBlockData();
                        block.setType(Material.AIR);
                        player.getWorld().spawnFallingBlock(block.getLocation(), blockdata);
                    }
                }

                manacounter.getManaList().put(player.getUniqueId(), manacounter.getManaList().get(player.getUniqueId()) - 60);
                manacounter.updateActionbar(player);


                Integer cooldown = manhuntMain.getConfig().getInt("abilities.gravity.cooldown");
                gravityCooldown.put(player.getUniqueId(), System.currentTimeMillis() + (cooldown * 1000));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.gravity.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%radius%", Integer.toString(radius))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', manhuntMain.getConfig().getString("abilities.gravity.speedrunner-msg").replace("%hunter%", player.getName()).replace("%radius%", Integer.toString(radius))));

                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);

            }
        }
    }
    public ArrayList<Block> getBlocks(Block start, int radius){
        ArrayList<Block> blocks = new ArrayList<Block>();
        for(double x = start.getLocation().getX() - radius; x <= start.getLocation().getX() + radius; x++){
            for(double y = start.getLocation().getY() - radius; y <= start.getLocation().getY() + radius; y++){
                for(double z = start.getLocation().getZ() - radius; z <= start.getLocation().getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    blocks.add(loc.getBlock());
                }
            }
        }
        return blocks;
    }
}
