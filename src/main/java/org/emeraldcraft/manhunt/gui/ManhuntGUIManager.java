package org.emeraldcraft.manhunt.gui;

import java.util.ArrayList;

import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

public class ManhuntGUIManager {
    private final ArrayList<ManhuntPlayerSelector> guis = new ArrayList<>();
    public void registerManhuntGUI(ManhuntPlayerSelector gui){
        debug("Processed GUI: " + gui);
        guis.add(gui);
    }
    public ArrayList<ManhuntPlayerSelector> getGUIs() {
        return new ArrayList<>(guis);
    }
    public void processManhuntGUI(ManhuntPlayerSelector gui){
        debug("Processed GUI: " + gui);
        guis.remove(gui);
    }
}
