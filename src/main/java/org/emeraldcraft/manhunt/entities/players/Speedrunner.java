package org.emeraldcraft.manhunt.entities.players;

import org.emeraldcraft.manhunt.entities.Waypoint;

import javax.annotation.Nullable;
import java.util.List;

public interface Speedrunner extends ManhuntPlayer{
    @Nullable
    Waypoint getWaypoint(String name);
    List<Waypoint> getWaypoints();
    void addWaypoint(Waypoint waypoint);
    void eliminate();
    boolean isEliminated();

}
