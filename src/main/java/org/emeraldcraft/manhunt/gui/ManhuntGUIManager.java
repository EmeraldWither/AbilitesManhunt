package org.emeraldcraft.manhunt.gui;

import java.util.ArrayList;

public class ManhuntGUIManager {
    private final ArrayList<ManhuntGUI> guis = new ArrayList<>();
    public void registerManhuntGUI(ManhuntGUI gui){
        guis.add(gui);
    }
    public ArrayList<ManhuntGUI> getGUIs() {
        return new ArrayList<>(guis);
    }
    public void processManhuntGUI(ManhuntGUI gui){
        guis.remove(gui);
    }
}
