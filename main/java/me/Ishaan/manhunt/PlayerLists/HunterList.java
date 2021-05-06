package me.Ishaan.manhunt.PlayerLists;

import java.util.ArrayList;
import java.util.List;

public class HunterList {
    public static List<String> hunters = new ArrayList<String>();

    public HunterList() {
        hunters = new ArrayList<String>();
    }

    public static List<String> getHunters() {
        return hunters;
    }

    public void addHunter(String hunter) {
        hunters.add(hunter);
    }
}
