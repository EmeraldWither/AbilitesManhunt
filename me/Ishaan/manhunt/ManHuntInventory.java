package me.Ishaan.manhunt;

import net.minecraft.server.v1_16_R3.Item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManHuntInventory {

    //Inventory Manager

    public ItemStack getLauncher()
    {
        List<String> lore = new ArrayList<String>();
        ItemStack launcher = new ItemStack(Material.FEATHER, 1);
        ItemMeta meta = launcher.getItemMeta();

        lore.add("");
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Launches the speedrunner into the air!");
        lore.add("");
        meta.setDisplayName(ChatColor.RED + "Launch Speedrunner");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        launcher.setItemMeta(meta);

        return launcher;

    }
    public ItemStack getLightning() {
        List<String> lore = new ArrayList<String>();
        ItemStack lightning = new ItemStack(Material.STICK, 1);
        ItemMeta meta = lightning.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Strike Lightning");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY  + "" + ChatColor.BOLD + "Strike lightning down onto the speedrunner.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        lightning.setItemMeta(meta);

        return lightning;
    }
    public ItemStack getGravity() {
        List<String> lore = new ArrayList<String>();
        ItemStack gravity = new ItemStack(Material.ANVIL, 1);
        ItemMeta meta = gravity.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Apply Gravity");
        lore.add("");
        lore.add(ChatColor.DARK_AQUA  + "" + ChatColor.BOLD + "Apply gravity to nearby blocks.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        gravity.setItemMeta(meta);

        return gravity;
    }



    }
