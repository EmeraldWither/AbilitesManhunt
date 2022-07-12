package org.emeraldcraft.manhunt.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.jetbrains.annotations.NotNull;

public class ManhuntGameEndEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final ManhuntTeam winner;

    public ManhuntGameEndEvent(ManhuntTeam winner) {
        this.winner = winner;
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

    public ManhuntTeam getWinningTeam(){
        return winner;
    }
}
