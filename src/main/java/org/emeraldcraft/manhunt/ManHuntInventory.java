package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.emeraldcraft.manhunt.Enums.Ability;

import java.util.ArrayList;
import java.util.List;

public class ManHuntInventory {

    //Inventory Manager
    public ItemStack getLightning() {
        List<String> lore = new ArrayList<String>();
        ItemStack lightning = new ItemStack(Material.STICK, 1);
        ItemMeta meta = lightning.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Strike Lightning on Speedrunner" + ChatColor.DARK_GREEN + " (10 Mana)");
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bStrike lightning down"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bon the speedrunner!"));
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&3(10 Mana)"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(2300);
        lightning.setItemMeta(meta);

        return lightning;
    }
    public ItemStack getLauncher() {
        List<String> lore = new ArrayList<String>();
        ItemStack launcher = new ItemStack(Material.FEATHER, 1);
        ItemMeta meta = launcher.getItemMeta();

        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bLaunch the speedrunner"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&binto the air!"));
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&b(20 Mana) "));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aLaunch Speedrunner &2(20 Mana) "));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setCustomModelData(2301);
        launcher.setItemMeta(meta);
        return launcher;

    }
    public ItemStack getFreezer(){
        List<String> lore = new ArrayList<>();
        ItemStack freezer = new ItemStack(Material.PACKED_ICE, 1);
        ItemMeta meta = freezer.getItemMeta();
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bFreeze the speedrunner"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bright where they are standing!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&3(30 Mana)"));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aFreeze Player &2(30 Mana)"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        freezer.setItemMeta(meta);
        meta.setCustomModelData(2302);
        return freezer;
    }
    public ItemStack getDamageItem() {
        List<String> lore = new ArrayList<>();
        ItemStack damageItem = new ItemStack(Material.GOLDEN_PICKAXE, 1);
        ItemMeta meta = damageItem.getItemMeta();

        meta.setDisplayName(ChatColor.GREEN + "Damage Item " + ChatColor.DARK_GREEN +"(40 Mana)");
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bDamage all of the items"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bin the speedrunners"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&binventory by half!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&3(40 Mana)"));

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setUnbreakable(true);
        meta.setCustomModelData(2303);
        damageItem.setItemMeta(meta);
        return damageItem;

    }
    public ItemStack getScrambler() {
        List<String> lore = new ArrayList<String>();
        ItemStack scrambler = new ItemStack(Material.TNT, 1);
        ItemMeta meta = scrambler.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aScramble Speedrunners Inventory &2(50 Mana) "));
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bCompletly scramble the"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bspeedrunners inventory!"));
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&3(50 Mana)"));

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(2304);
        scrambler.setItemMeta(meta);
        return scrambler;
    }
    public ItemStack getGravity() {
        List<String> lore = new ArrayList<String>();
        ItemStack gravity = new ItemStack(Material.ANVIL, 1);
        ItemMeta meta = gravity.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aApply Gravity to Nearby Blocks &2(60 Mana)"));
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bApply gravity to blocks"));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&bnear the speedrunner!"));
        lore.add(ChatColor.translateAlternateColorCodes('&',""));
        lore.add(ChatColor.translateAlternateColorCodes('&',"&3(60 Mana)"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.setCustomModelData(2305);
        gravity.setItemMeta(meta);
        return gravity;
    }


    public ItemStack getrandomTP() {
        List<String> lore = new ArrayList<String>();
        ItemStack randomTP = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = randomTP.getItemMeta();

        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bRandomly teleport the"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bspeedrunners to a"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bdifferent place!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&3(80 Mana)"));

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aRandomly Teleport Speedrunner &2(80 Mana)"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setCustomModelData(2306);
        randomTP.setItemMeta(meta);


        return randomTP;

    }
    public ItemStack getMobTargeter(){
        List<String> lore = new ArrayList<String>();
        ItemStack mobTargeter = new ItemStack(Material.CARROT_ON_A_STICK, 1);
        ItemMeta meta = mobTargeter.getItemMeta();

        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&bCommand all mobs to"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&btarget the speedrunner!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&3(100 Mana)"));
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&aCommand Mobs &2(100 Mana)"));
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(2307);
        mobTargeter.setItemMeta(meta);


        return mobTargeter;
    }
    public ItemStack getPlayerTP() {
        List<String> lore = new ArrayList<String>();
        ItemStack playerTP = new ItemStack(Material.ENDER_PEARL, 1);
        ItemMeta meta = playerTP.getItemMeta();

        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&aAllows you to teleport"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&ato the speedrunner!"));
        lore.add(ChatColor.translateAlternateColorCodes('&', ""));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&3(No Mana)"));
        meta.setDisplayName(ChatColor.AQUA + "Teleport to the Speedrunner");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(2308);
        playerTP.setItemMeta(meta);


        return playerTP;

    }


    public ItemStack getBarrier(){
        ItemStack barrier = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = barrier.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.GRAY + "");
        barrier.setItemMeta(meta);
        return barrier;
    }
    public void giveAbility(Ability ability, String name, Integer slot){
        Player hunter = Bukkit.getPlayer(name);

        if(ability.equals(Ability.LAUNCHER)){
            hunter.getInventory().setItem(slot, getLauncher());
        }
        else if(ability.equals(Ability.FREEZER)){
            hunter.getInventory().setItem(slot,getFreezer());
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
        else if(ability.equals(Ability.TARGETMOB)){
            hunter.getInventory().setItem(slot,getMobTargeter());
        }
        else if(ability.equals(Ability.BARRIER)) {
            hunter.getInventory().setItem(slot, getBarrier());
        }
    }
}
