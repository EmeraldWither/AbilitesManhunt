package org.emeraldcraft.manhunt.events.hunter;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.jetbrains.annotations.NotNull;

/**
 * Called before a hunter executes an ability.
 */
public class PreHunterExecuteAbilityEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ManhuntAbility ability;
    private final Hunter hunter;
    private final Speedrunner target;
    private boolean cancelled;

    public PreHunterExecuteAbilityEvent(ManhuntAbility ability, Hunter hunter, Speedrunner target) {
        this.ability = ability;
        this.hunter = hunter;
        this.target = target;
    }

    /**
     * Managed by Bukkit.
     * @return A list of handlers
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * @return The ability that was executed
     */
    public ManhuntAbility getAbility() {
        return ability;
    }

    /**
     * @return The hunter who executed the ability
     */
    public Hunter getHunter() {
        return hunter;
    }

    /**
     * @return The target of the ability (speedrunner)
     */
    public Speedrunner getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
