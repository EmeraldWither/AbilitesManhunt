package org.emeraldcraft.manhunt.entities.players;

import java.util.List;

import javax.annotation.Nullable;

import org.emeraldcraft.manhunt.entities.Waypoint;

public interface Speedrunner extends ManhuntPlayer{
    @Nullable
    /**
     * No implementation has been made yet for supporting waypoints.
     */
    Waypoint getWaypoint(String name);
    /**
     * No implementation has been made yet for supporting waypoints.
     */
    List<Waypoint> getWaypoints();
    /**
     * No implementation has been made yet for supporting waypoints.
     */
    void addWaypoint(Waypoint waypoint);
    /**
     * It will elimate the player from the game. 
     * The method DOES NOT kill the player, it simply marks them as eliminated
     * If you wish for them to be killed, do that yourself
     */
    void eliminate();
    boolean isEliminated();

}
