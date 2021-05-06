package me.Ishaan.manhunt;

import me.Ishaan.manhunt.Enums.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ManHuntInventory {

    //Inventory Manager

    public ItemStack getLauncher() {
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
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Strike lightning down onto the speedrunner.");
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
        lore.add(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Apply gravity to nearby blocks.");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        gravity.setItemMeta(meta);

        return gravity;
    }

    public ItemStack getScrambler() {
        List<String> lore = new ArrayList<String>();
        ItemStack scrambler = new ItemStack(Material.TNT, 1);
        ItemMeta meta = scrambler.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Scramble Inventory");
        lore.add("");
        lore.add(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Scramble the selected players inventory!");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        scrambler.setItemMeta(meta);

        return scrambler;
    }
    public ItemStack getrandomTP() {
        List<String> lore = new ArrayList<String>();
        ItemStack randomTP = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = randomTP.getItemMeta();

        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Teleports the speedrunner within a 50 block radius!");
        lore.add("");
        meta.setDisplayName(ChatColor.RED + "Randomly Teleport Speedrunner");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        randomTP.setItemMeta(meta);


        return randomTP;

    }
    public ItemStack getDamageItem() {
        List<String> lore = new ArrayList<String>();
        ItemStack damageItem = new ItemStack(Material.GOLDEN_PICKAXE, 1);
        ItemMeta meta = damageItem.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Damage Item");
        lore.add("");
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Sets the durability of the item that");
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "the speedruner is holding to half");
        lore.add(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "of what it was at!");
        lore.add("");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        damageItem.setDurability((short) (damageItem.getDurability() / 2));

        damageItem.setItemMeta(meta);
        return damageItem;

    }

    public ItemStack getPlayerTP() {
        List<String> lore = new ArrayList<String>();
        ItemStack playerTP = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = playerTP.getItemMeta();

        lore.add("");
        lore.add(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Teleport to a speedrunner!");
        lore.add("");
        meta.setDisplayName(ChatColor.AQUA + "Teleport To Speedrunner");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        playerTP.setItemMeta(meta);


        return playerTP;

    }

    public void giveAbility(Ability ability, String name){
        Player hunter = Bukkit.getPlayer(name);

        if(ability.equals(Ability.LAUNCHER)){
            hunter.getInventory().addItem(getLauncher());
        }
        else if(ability.equals(Ability.LIGHTNING)){
            hunter.getInventory().addItem(getLightning());
        }
        else if(ability.equals(Ability.GRAVITY)){
            hunter.getInventory().addItem(getGravity());
        }
        else if(ability.equals(Ability.SCRAMBLE)){
            hunter.getInventory().addItem(getScrambler());
        }
        else if(ability.equals(Ability.RANDOMTP)){
            hunter.getInventory().addItem(getrandomTP());
        }
        else if(ability.equals(Ability.DAMAGEITEM)){
            hunter.getInventory().addItem(getDamageItem());
        }
        else if(ability.equals(Ability.PLAYERTP)){
            hunter.getInventory().addItem(getPlayerTP());
        }
    }
    public void giveAbility(Ability ability, String name, Integer slot){
        Player hunter = Bukkit.getPlayer(name);

        if(ability.equals(Ability.LAUNCHER)){
            hunter.getInventory().setItem(slot, getLauncher());
        }
        else if(ability.equals(Ability.LIGHTNING)){
            hunter.getInventory().setItem(slot,getLightning());
        }
        else if(ability.equals(Ability.GRAVITY)){
            hunter.getInventory().setItem(slot,getGravity());
        }
        else if(ability.equals(Ability.SCRAMBLE)){
            hunter.getInventory().setItem(slot,getScrambler());
        }
        else if(ability.equals(Ability.RANDOMTP)){
            hunter.getInventory().setItem(slot,getrandomTP());
        }
        else if(ability.equals(Ability.DAMAGEITEM)){
            hunter.getInventory().setItem(slot,getDamageItem());
        }
        else if(ability.equals(Ability.PLAYERTP)){
            hunter.getInventory().setItem(slot,getPlayerTP());
        }
    }
}
