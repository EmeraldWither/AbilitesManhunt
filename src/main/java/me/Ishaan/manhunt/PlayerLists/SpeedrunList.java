package me.Ishaan.manhunt.PlayerLists;

import java.util.ArrayList;
import java.util.List;

public class SpeedrunList {
    public static List<String> speedrunners = new ArrayList<String>();

    public SpeedrunList() {
        speedrunners = new ArrayList<String>();
    }

    public static List<String> getSpeedruners() {
        return speedrunners;
    }

    public void addSpeedrunner(String speedrunner) {
        speedrunners.add(speedrunner);
    }
}
