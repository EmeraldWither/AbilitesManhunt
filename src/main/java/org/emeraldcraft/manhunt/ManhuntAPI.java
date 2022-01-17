package org.emeraldcraft.manhunt;

import org.jetbrains.annotations.NotNull;

public class ManhuntAPI {
    private static Manhunt manhunt;

    public static Manhunt getAPI(){
        return manhunt;
    }

    public static void setAPI(@NotNull Manhunt manhunt){
        if(ManhuntAPI.manhunt == null){
            ManhuntAPI.manhunt = manhunt;
        }
    }
}
