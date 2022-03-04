package org.emeraldcraft.manhunt;

public class Manhunt {
    private static ManhuntAPI API;

    /**
     * Entrypoint to the manhunt API
     * @return The API
     */
    public static ManhuntAPI getAPI(){
        return Manhunt.API;
    }
    public void setAPI(ManhuntAPI api){
        if(Manhunt.API == null){
            Manhunt.API = api;
        }
    }
}
