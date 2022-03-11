package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.internal.IManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.IManhuntSpeedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * The API class which provides the control of the plugin.
 *
 * The entrypoint is {@link Manhunt#getAPI()}
 */
public class ManhuntAPI {
    private final ManhuntMain main;
    private final ManhuntConfigValues configValues;
    private final List<ManhuntPlayer> players = new ArrayList<>();
    private final List<ManhuntAbility> abilites = new ArrayList<>();

    public ManhuntAPI(ManhuntMain main){
        this.main = main;
        configValues = new ManhuntConfigValues(main.getConfig());
    }
    public void debug(String message){
        if(configValues.isDebugging()) Bukkit.getLogger().log(Level.INFO, "[MANHUNT DEBUG] " + message);
    }

    /**
     * @return The configuration values of the plugin
     */
    public ManhuntConfigValues getConfigValues(){
        return this.configValues;
    }

    @Nullable
    public ManhuntPlayer getPlayer(UUID uuid){
        for(ManhuntPlayer player : this.players){
            if(player.getUUID() == uuid) return player;
        }
        return null;
    }
    public void registerPlayer(Player player, ManhuntTeam team){
        ManhuntPlayer manhuntPlayer;
        switch (team){
            case HUNTER -> manhuntPlayer = new IManhuntHunter(player);
            case SPEEDRUNNER -> manhuntPlayer = new IManhuntSpeedrunner(player);
            default -> throw new IllegalArgumentException("Team is not HUNTER or SPEEDRUNNER");
        }
        this.players.add(manhuntPlayer);
    }
    public void start(){

    }
    public void registerAbility(ManhuntAbility ability){
        if(this.abilites.contains(ability)) throw new IllegalArgumentException("Ability is already registered");
        this.abilites.add(ability);
    }
    private void constructInventory(ManhuntHunter hunter){
        if (hunter.getAsBukkitPlayer() != null) {

        }
    }
    public List<ManhuntPlayer> getTeam(ManhuntTeam team){
        final List<ManhuntPlayer> players = new ArrayList<>();
        this.players.forEach(manhuntPlayer -> {
            if(manhuntPlayer.getTeam() == team) this.players.add(manhuntPlayer);
        });
        return players;
    }


}
