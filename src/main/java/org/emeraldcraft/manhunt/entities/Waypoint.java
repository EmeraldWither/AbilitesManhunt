package org.emeraldcraft.manhunt.entities;

import org.bukkit.Location;

/**
 * No implementation has been made yet for supporting waypoints.
 * Future task though 
 */
@Deprecated
public class Waypoint {
    private final String name;
    private final Location location;

    public Waypoint(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
