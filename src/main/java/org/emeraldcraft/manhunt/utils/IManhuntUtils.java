package org.emeraldcraft.manhunt.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.logging.Level.INFO;
import static org.emeraldcraft.manhunt.enums.ManhuntTeam.SPEEDRUNNER;

/**
 * Internal class using for certain utility functions for the plugin.
 */
public class IManhuntUtils {
    public static void debug(String msg){
        if(Manhunt.getAPI().getConfig().isDebugging()) Bukkit.getLogger().log(INFO, "[Manhunt DEBUG] " + msg);
    }
    @Nullable
    public static Inventory constructInventory(Hunter hunter, List<ManhuntAbility> abilities){
        if(hunter.getAsBukkitPlayer() == null) return null;
        Inventory inventory = hunter.getAsBukkitPlayer().getInventory();
        for(int i = 0; i < abilities.size(); i++){
            if(abilities.get(i) == null) continue;
            ManhuntAbility ability = abilities.get(i);
            inventory.setItem(i + 1, ability.getAsItemStack());
            debug("Set itemstack for ability " + ability.name() + " at slot " + i);
        }
        return inventory;
    }
    public static Inventory createPlayerSelector(){
        //Calculate how many inventory slots we need!

        //Divide the current players by 9 (amount in each row of an inventory), then round up, and cast it back into an int
        int inventorySlots = (int) (Math.ceil(Manhunt.getAPI().getTeam(SPEEDRUNNER).size()/9.0) * 9);
        if(inventorySlots == 0) inventorySlots = 9;

        Inventory inventory = Bukkit.createInventory(null, inventorySlots, Component.text("Select a player").color(TextColor.color(52, 229, 235)));
        for (int i = 0; i < Manhunt.getAPI().getTeam(SPEEDRUNNER).size(); i++) {
            Speedrunner speedrunner = (Speedrunner) Manhunt.getAPI().getTeam(SPEEDRUNNER).get(i);
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta itemMeta = itemStack.getItemMeta();
            Component playerName = Component.text(speedrunner.getAsBukkitPlayer().getName()).color(TextColor.color(52, 229, 235));
            itemMeta.displayName(playerName);
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text("Click to select this player").color(TextColor.color(52, 229, 235)));
            itemMeta.lore(lore);
            ((SkullMeta) itemMeta).setOwningPlayer(speedrunner.getAsBukkitPlayer());
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(i, itemStack);
        }
        return inventory;
    }
    public static void createItemLore(ManhuntAbility manhuntAbility, ItemStack ability, String name, String description){
        ItemMeta itemMeta = ability.getItemMeta();
        String displayText = "<aqua>%s mes<dark_aqua><u>(%s Mana)</u></dark_aqua> </aqua>".formatted(name, manhuntAbility.getMana() + "");
        itemMeta.displayName(MiniMessage.miniMessage().deserialize(displayText));
        List<Component> components = new ArrayList<>();
        Component descriptionComponent = Component.text(description).color(TextColor.fromCSSHexString("#00eeff"));
        Component manaComponent = Component.text("Mana: " + manhuntAbility.getMana()).color(TextColor.fromCSSHexString("#ff0026"));
        Component cooldownComponent = Component.text("Cooldown: " + manhuntAbility.getCooldown() + " seconds").color(TextColor.fromHexString("#00ff0d"));
        components.add(descriptionComponent);
        components.add(manaComponent);
        components.add(cooldownComponent);
        itemMeta.lore(components);
        ability.setItemMeta(itemMeta);
    }
    public static String parseBasicMessage(String message, ManhuntAbility ability, @Nullable Speedrunner speedrunner, @Nullable Hunter hunter){
        return message.replaceAll("%hunter%",
                Objects.requireNonNull(hunter.getAsBukkitPlayer()).getName())
                .replaceAll("%speedrunner%",
                        Objects.requireNonNull(speedrunner.getAsBukkitPlayer()).getName())
                .replaceAll("%ability-name%",
                        ability.name());
    }
    public static Component parseConfigMessage(String message, ManhuntAbility ability, @Nullable Speedrunner speedrunner, @Nullable Hunter hunter, String[] placeholders, String[] values){
        String parsedMessage = parseBasicMessage(message, ability, speedrunner, hunter);
        if(placeholders == null || values == null) return MiniMessage.miniMessage().deserialize(parsedMessage);
        for(int i = 0; i < placeholders.length; i++){
            parsedMessage = parsedMessage.replaceAll(placeholders[i], values[i]);
        }
        return MiniMessage.miniMessage().deserialize(parsedMessage);
    }
}
