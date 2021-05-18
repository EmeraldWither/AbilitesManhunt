package me.EmeraldWither.manhunt.GUI;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class SpeedrunnerGUI {

    private ManhuntGameManager manhuntGameManager;
    private Main main;
    List<String> hunter;
    List<String> speedrunner;
    public SpeedrunnerGUI(ManhuntGameManager manhuntGameManager, Main main){
        this.main = main;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(Team.HUNTER);
        speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);;
    }
    public Inventory createInventory(){

        Inventory inv;
        inv = Bukkit.createInventory(new GUIInventoryHolder(), 9, ChatColor.translateAlternateColorCodes('&',"&9&lSelect a Speedrunner: "));
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (speedrunner.contains(player.getName())) {
                ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta im = (SkullMeta) skull.getItemMeta();
                List<String> il = new ArrayList<String>();
                im.setOwningPlayer(player.getServer().getOfflinePlayer(player.getName()));
                im.setLore(il);
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + player.getName()));
                im.addEnchant(Enchantment.RIPTIDE, 0 ,true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                skull.setItemMeta(im);

                inv.addItem(skull);
            }
        }
        return inv;
    }

    public Inventory getInv() {

        return createInventory();
    }
}
