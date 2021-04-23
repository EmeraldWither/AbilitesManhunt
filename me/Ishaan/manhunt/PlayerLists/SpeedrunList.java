package me.Ishaan.manhunt.PlayerLists;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpeedrunList {
        public static List<String> speedrunners;

        public SpeedrunList() {
            speedrunners = new ArrayList<String>();
        }

        public List<String> getList() {
            return speedrunners;
        }
}
