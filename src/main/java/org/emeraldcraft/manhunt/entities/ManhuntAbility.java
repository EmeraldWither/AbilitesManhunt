package org.emeraldcraft.manhunt.entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.events.hunter.PreHunterExecuteAbilityEvent;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import java.util.UUID;

/**
 * Represents a simple ManhuntAbility. Ability execution logic is handled by {@link Hunter}.
 */
public abstract class ManhuntAbility {
    private final UUID uuid;
    private final String name;
    private final String description;
    private final int cooldown;
    private final int mana;
    private final Material material;
    private final ItemStack itemStack;
    private final String id;

    /**
     * @param cooldown The cooldown for the item (-1 for no cooldown)
     * @param mana     The amount of mana needed (-1 for no mana)
     * @param material The material to represent this item
     */
    public ManhuntAbility(String name, String description, int cooldown, int mana, Material material, String id) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.mana = mana;
        this.material = material;
        this.id = id;
        this.uuid = UUID.randomUUID();

        //Create our itemstack
        ItemStack itemStack = new ItemStack(this.material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = Manhunt.getAPI().getNamespacedKey();
        itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, this.uuid.toString());
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        IManhuntUtils.createItemLore(this, itemStack, this.name, this.description);
        this.itemStack = itemStack;
    }

    protected abstract void onExecute(Hunter hunter, Speedrunner speedrunner);

    /**
     * Executes the ability while accounting for mana and cooldown costs.
     *
     * @param hunter      The hunter
     * @param speedrunner The speedrunner
     */
    public void execute(Hunter hunter, Speedrunner speedrunner) {
        if (hunter.getMana() < this.mana) {
            if (hunter.getAsBukkitPlayer() == null) return;
            IManhuntUtils.debug("Hunters mana: " + hunter.getMana() + ", Ability Mana: " + this.mana);
            Component message = Component.text("You do not have enough mana!").color(TextColor.color(255, 0, 0));
            hunter.getAsBukkitPlayer().sendMessage(message);
            return;
        }
        if (hunter.getCooldownEndPeriod(this) > System.currentTimeMillis()) {
            if (hunter.getAsBukkitPlayer() == null) return;
            Component message = Component.text("You are still on cooldown!").color(TextColor.color(255, 0, 0));
            hunter.getAsBukkitPlayer().sendMessage(message);
            return;
        }
        if(hunter.getAsBukkitPlayer() == null || speedrunner.getAsBukkitPlayer() == null) return;

        PreHunterExecuteAbilityEvent event = new PreHunterExecuteAbilityEvent(this, hunter, speedrunner);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return;

        hunter.setCooldown(this, System.currentTimeMillis() + (this.cooldown * 1000L));
        hunter.setMana(hunter.getMana() - this.mana);
        speedrunner.addCoins(this.mana);
        onExecute(hunter, speedrunner);
    }

    public ItemStack getAsItemStack(){
        return itemStack;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getMana() {
        return mana;
    }

    public Material getMaterial() {
        return material;
    }

    public String name() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getUUID() {
        return uuid;
    }
    public ConfigurationSection getAttributes(){
        return Manhunt.getAPI().getConfig().getFileConfig().getConfigurationSection("ability." + this.id);
    }
}
