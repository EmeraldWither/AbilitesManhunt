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
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.Main;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.ManhuntGameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GravityGUIListener implements Listener {

    String ability = "Gravity Blocks";

    Map<String, Long> gravityCooldown;

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    private AbilitesManager abilitesManager;
    List<String> hunter;
    List<String> speedrunner;
    public GravityGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter, AbilitesManager AbilitesManager){
        this.main = main;
        this.abilitesManager = AbilitesManager;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        gravityCooldown = abilitesManager.getCooldown(Ability.GRAVITY);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null) {
            Player player = (Player) event.getView().getPlayer();
            if (abilitesManager.getHeldAbility(player).equals(Ability.GRAVITY)) {
                if (gravityCooldown.containsKey(player.getName())) {
                    if (gravityCooldown.get(player.getName()) > System.currentTimeMillis()) {
                        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((gravityCooldown.get(player.getName()) - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                        return;
                    }
                }
                SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                int radius = main.getConfig().getInt("abilities.gravity.radius");
                for (Block block : getBlocks(selectedPlayer.getLocation().getBlock(), radius)) {
                    if (block.getRelative(BlockFace.DOWN).getType() == Material.AIR) {
                        BlockData blockdata = block.getBlockData();
                        block.setType(Material.AIR);
                        player.getWorld().spawnFallingBlock(block.getLocation(), blockdata);
                    }
                }

                manacounter.getManaList().put(player.getName(), manacounter.getManaList().get(player.getName()) - 60);
                manacounter.updateActionbar(player);


                Integer cooldown = main.getConfig().getInt("abilities.gravity.cooldown");
                gravityCooldown.put(player.getName(), System.currentTimeMillis() + (cooldown * 1000));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.gravity.msg").replace("%hunter%", player.getName()).replace("%speedrunner%", selectedPlayer.getName()).replace("%radius%", Integer.toString(radius))));
                selectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("abilities.gravity.speedrunner-msg").replace("%hunter%", player.getName()).replace("%radius%", Integer.toString(radius))));

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