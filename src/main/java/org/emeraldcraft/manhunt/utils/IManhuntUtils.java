package org.emeraldcraft.manhunt.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static java.util.logging.Level.INFO;

/**
 * Internal class using for certain utility functions for the plugin.
 */
public class IManhuntUtils {
    public static void debug(String msg){
        if(Manhunt.getAPI().getConfigValues().isDebugging()) Bukkit.getLogger().log(INFO, "[Manhunt Debug] " + msg);
    }
    @Nullable
    public static Inventory constructInventory(Hunter hunter, List<ManhuntAbility> abilities){
        if(hunter.getAsBukkitPlayer() == null) return null;
        Inventory inventory = hunter.getAsBukkitPlayer().getInventory();
        for(int i = 0; i < abilities.size(); i++){
            if(abilities.get(i) == null) continue;
            ManhuntAbility ability = abilities.get(i);
            inventory.setItem(i + 1, ability.getAsItemStack());
            IManhuntUtils.debug("Set itemstack for ability " + ability.getName() + " at slot " + i);
        }
        return inventory;
    }
    public static void createItemLore(ManhuntAbility manhuntAbility, ItemStack ability, String name, String description){
        ItemMeta itemMeta = ability.getItemMeta();
        itemMeta.displayName(Component.text(name + " (" + manhuntAbility.getMana() + " mana)").color(TextColor.color(0, 4, 255)).decorate(TextDecoration.ITALIC));
        List<Component> components = new ArrayList<>();
        Component descriptionComponent = Component.text(description).color(TextColor.fromCSSHexString("#00eeff"));
        Component manaComponent = Component.text("Mana: " + manhuntAbility.getMana()).color(TextColor.fromCSSHexString("#ff0026"));
        Component cooldownComponent = Component.text("Cooldown:  " + manhuntAbility.getCooldown() + " seconds").color(TextColor.fromHexString("#00ff0d"));
        components.add(descriptionComponent);
        components.add(manaComponent);
        components.add(cooldownComponent);
        itemMeta.lore(components);
        ability.setItemMeta(itemMeta);
    }
}
