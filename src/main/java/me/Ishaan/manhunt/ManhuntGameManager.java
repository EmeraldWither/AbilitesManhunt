package me.Ishaan.manhunt;

import me.Ishaan.manhunt.Enums.Team;

import java.util.ArrayList;
import java.util.List;

public class ManhuntGameManager {

    List<String> hunter = new ArrayList<>();
    List<String> speedrunner = new ArrayList<>();
    List<String> deadSpeedrunners = new ArrayList<>();
    List<String> frozenPlayers = new ArrayList<String>();
    private boolean hasGameStarted = false;

    public List<String> getTeam(Team team) {
        if (team == Team.HUNTER) {
            return hunter;
        }
        if (team == Team.FROZEN) {
            return frozenPlayers;
        }
        if (team == Team.DEAD) {
                return deadSpeedrunners;
        }
        if (team == Team.SPEEDRUNNER) {
            return speedrunner;
        }
        return null;
    }
    public boolean getGameStatus(){
        return hasGameStarted;
    }
    public void setGameStatus(boolean b){
        hasGameStarted = b;
    }

}
