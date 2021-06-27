package org.emeraldcraft.manhunt.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.ArrayList;
import java.util.List;

public class SpeedrunnerGUI {

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;
    List<String> hunter;
    List<String> speedrunner;
    public SpeedrunnerGUI(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntMain = manhuntMain;
        this.manhuntGameManager = manhuntGameManager;
        hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);;
    }
    public Inventory createInventory() {

        Inventory inv;
        inv = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', "&9&lSelect a Speedrunner: "));
        for (String playerName : manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER)) {
            Player player = Bukkit.getPlayer(playerName);
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta im = (SkullMeta) skull.getItemMeta();
            List<String> il = new ArrayList<String>();
            im.setOwningPlayer(player.getServer().getOfflinePlayer(player.getName()));
            im.setLore(il);
            im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b" + player.getName()));
            im.addEnchant(Enchantment.RIPTIDE, 0, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            skull.setItemMeta(im);

            inv.addItem(skull);
        }
        ItemStack barrier = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = barrier.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.GRAY + "");
        barrier.setItemMeta(meta);
        for (int i = 0; i < 9; i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType().equals(Material.AIR)) {
                inv.setItem(i, barrier);
            }
        }
        return inv;
    }

    public Inventory getInv() {

        return createInventory();
    }
}
