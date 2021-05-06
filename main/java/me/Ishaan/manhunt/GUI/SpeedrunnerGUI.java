package me.Ishaan.manhunt.GUI;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
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

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;

    public Inventory createInventory() {

        Inventory inv;
        inv = Bukkit.createInventory(new GUIInventoryHolder(), 9, ChatColor.translateAlternateColorCodes('&',"&9&lSelect Speedrunner"));

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
