package org.emeraldcraft.manhunt.entities.players;

import org.emeraldcraft.manhunt.entities.Waypoint;

import javax.annotation.Nullable;
import java.util.List;

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
     * It will eliminate the player from the game.
     * The method DOES NOT kill the player, it simply marks them as eliminated
     * If you wish for them to be killed, do that yourself
     */
    void eliminate();
    boolean isEliminated();
    int getCoins();
    void setCoins(int amount);
    void addCoins(int amount);
    void removeCoins(int amount);

}
