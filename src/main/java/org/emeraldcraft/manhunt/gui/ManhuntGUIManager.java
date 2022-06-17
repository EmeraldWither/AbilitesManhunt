package org.emeraldcraft.manhunt.gui;

import java.util.ArrayList;

import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

public class ManhuntGUIManager {
    private final ArrayList<ManhuntGUI> guis = new ArrayList<>();
    public void registerManhuntGUI(ManhuntGUI gui){
        debug("Processed GUI: " + gui);
        guis.add(gui);
    }
    public ArrayList<ManhuntGUI> getGUIs() {
        return new ArrayList<>(guis);
    }
    public void processManhuntGUI(ManhuntGUI gui){
        debug("Processed GUI: " + gui);
        guis.remove(gui);
    }
}
