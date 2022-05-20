package org.emeraldcraft.manhunt.events.speedrunner;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.jetbrains.annotations.NotNull;

public class SpeedrunnerDeathEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Speedrunner speedrunner;
    private final PlayerDeathEvent event;
    private boolean cancelled;

    public SpeedrunnerDeathEvent(@NotNull Speedrunner speedrunner, @NotNull PlayerDeathEvent event) {
        this.speedrunner = speedrunner;
        this.event = event;
    }

    /**
     * Managed by Bukkit.
     * @return A list of handlers
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * @return The Speedrunner that died.
     */
    @NotNull
    public Speedrunner getSpeedrunner() {
        return this.speedrunner;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * @return The PlayerDeathEvent that caused this event to be fired.
     */
    public PlayerDeathEvent getEvent() {
        return event;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
