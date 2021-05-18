package me.EmeraldWither.manhunt.Abilties.GravityBlocks;

import me.EmeraldWither.manhunt.Abilties.CooldownsManager;
import me.EmeraldWither.manhunt.Enums.Ability;
import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.GUI.GUIInventoryHolder;
import me.EmeraldWither.manhunt.GUI.SpeedrunnerGUI;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManHuntInventory;
import me.EmeraldWither.manhunt.Mana.Manacounter;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GravityGUIListener extends CooldownsManager implements Listener {

    String ability = "Gravity Blocks";

    Map<String, Long> gravityCooldown = getCooldown(Ability.GRAVITY);

    private Main main;
    private ManhuntGameManager manhuntGameManager;
    private Manacounter manacounter;
    List<String> hunter;
    List<String> speedrunner;
    public GravityGUIListener(ManhuntGameManager manhuntGameManager, Main main, Manacounter manacounter){
        this.main = main;
        this.manacounter = manacounter;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event){

        SpeedrunnerGUI inv = new SpeedrunnerGUI(manhuntGameManager, main);
        Inventory getInventory = inv.getInv();

        if(event.getInventory().getHolder() instanceof GUIInventoryHolder){
            if(event.getCurrentItem() != null) {
                if (manhuntGameManager.getGameStatus()) {
                    if (hunter.contains(event.getView().getPlayer().getName())) {
                        Player player = (Player) event.getView().getPlayer();
                        if (player.getInventory().getItemInMainHand().isSimilar(new ManHuntInventory().getGravity())){
                            if (gravityCooldown.containsKey(player.getName())) {
                                if (gravityCooldown.get(player.getName()) > System.currentTimeMillis()) {
                                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.cooldown-msg").replace("%time-left%", Long.toString((gravityCooldown.get(player.getName())  - System.currentTimeMillis()) / 1000)).replace("%ability%", ability)));
                                    return;
                                }
                            }

                            SkullMeta skull = (SkullMeta) event.getCurrentItem().getItemMeta();
                            Player selectedPlayer = Bukkit.getPlayer(skull.getOwner());
                            Byte blockData = 0x0;

                            Integer radius = main.getConfig().getInt("abilities.gravity.radius");

                            for (Block block : getBlocks(selectedPlayer.getLocation().getBlock(), radius)) {

                                BlockData blockdata = block.getBlockData();
                                block.setType(Material.AIR);
                                player.getWorld().spawnFallingBlock(block.getLocation(), blockdata);
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
