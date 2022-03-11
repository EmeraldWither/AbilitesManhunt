package org.emeraldcraft.manhunt.entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.emeraldcraft.manhunt.entities.players.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntSpeedrunner;

import java.util.HashMap;
import java.util.UUID;

/**
 * Represents a simple ManhuntAbility. Ability execution logic is handled by {@link org.emeraldcraft.manhunt.entities.players.ManhuntHunter}.
 */
public abstract class ManhuntAbility {
    private final UUID uuid;
    private final String name;
    private final String description;
    private final int cooldown;
    private final int mana;
    private final Material material;
    private final HashMap<ManhuntAbility, Long> cooldowns = new HashMap<>();

    /**
     * @param cooldown The cooldown for the item (-1 for no cooldown)
     * @param mana     The amount of mana needed (-1 for no mana)
     * @param material The material to represent this item
     */
    public ManhuntAbility(String name, String description, int cooldown, int mana, Material material) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.mana = mana;
        this.material = material;
        this.uuid = UUID.randomUUID();
    }

    protected abstract void onExecute(ManhuntHunter hunter, ManhuntSpeedrunner speedrunner);

    /**
     * Executes the ability while accounting for mana and cooldown costs.
     *
     * @param hunter      The hunter
     * @param speedrunner The speedrunner
     */
    public void execute(ManhuntHunter hunter, ManhuntSpeedrunner speedrunner) {
        if (hunter.getMana() < this.mana) {
            if (hunter.getAsBukkitPlayer() == null) return;

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
        onExecute(hunter, speedrunner);
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getUuid() {
        return uuid;
    }
}
