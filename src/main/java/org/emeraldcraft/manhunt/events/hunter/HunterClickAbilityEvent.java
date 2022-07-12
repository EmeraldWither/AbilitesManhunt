package org.emeraldcraft.manhunt.events.hunter;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.jetbrains.annotations.NotNull;

public class HunterClickAbilityEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ManhuntAbility ability;
    private final Hunter hunter;
    private boolean cancelled = false;

    public HunterClickAbilityEvent(ManhuntAbility ability, Hunter hunter) {
        this.ability = ability;
        this.hunter = hunter;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public ManhuntAbility getAbility() {
        return ability;
    }

    public Hunter getHunter() {
        return hunter;
    }
}
