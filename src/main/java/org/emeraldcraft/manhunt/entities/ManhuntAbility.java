package org.emeraldcraft.manhunt.entities;

import org.bukkit.inventory.ItemStack;

/**
 * Represents a simple ManhuntAbility. Ability execution logic is handled by {@link org.emeraldcraft.manhunt.entities.players.ManhuntHunter}.
 */
public abstract class ManhuntAbility {
    private final String name;
    private final String description;
    private final int cooldown;
    private final int mana;
    private final ItemStack itemStack;
    /**
     * @param cooldown The cooldown for the item (-1 for no cooldown)
     * @param mana The amount of mana needed (-1 for no mana)
     * @param itemStack The itemstack to represent this item
     */
    public ManhuntAbility(String name, String description, int cooldown, int mana, ItemStack itemStack){
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.mana = mana;
        this.itemStack = itemStack;
    }
    protected abstract void onExecute();


    public int getCooldown() {
        return cooldown;
    }

    public int getMana() {
        return mana;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
