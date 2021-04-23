package me.Ishaan.manhunt.PlayerLists;

import java.util.ArrayList;
import java.util.List;

public class HunterList {
    public static List<String> hunters;

    public HunterList() {
        hunters = new ArrayList<String>();
    }

    public List<String> getHunters() {
        return hunters;
    }

    public void addHunter(String hunter) {
        hunters.add(hunter);
    }
}
