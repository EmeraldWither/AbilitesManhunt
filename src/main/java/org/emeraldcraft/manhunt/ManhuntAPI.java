package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.abilites.LaunchAbility;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntSpeedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

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
            if(player.getUUID().toString().equals(uuid.toString())) return player;
        }
        return null;
    }
    public void registerPlayer(Player player, ManhuntTeam team){
        ManhuntPlayer manhuntPlayer;
        switch (team){
            case HUNTER -> manhuntPlayer = new ManhuntHunter(player);
            case SPEEDRUNNER -> manhuntPlayer = new ManhuntSpeedrunner(player);
            default -> throw new IllegalArgumentException("Team is not HUNTER or SPEEDRUNNER");
        }
        this.players.add(manhuntPlayer);
    }
    public void testStart(){
        IManhuntUtils.debug("Started the gamemode. ");
        registerAbility(new LaunchAbility());
        List<Hunter> hunters = this.players.stream().filter(player -> player.getTeam() == ManhuntTeam.HUNTER).map(player -> (Hunter) player).toList();
        List<Speedrunner> speedrunners = this.players.stream().filter(player -> player.getTeam() == ManhuntTeam.SPEEDRUNNER).map(player -> (Speedrunner) player).toList();

        for(Hunter hunter : hunters){
            constructInventory(hunter);
            IManhuntUtils.debug("Constructed inventory ");
        }
    }
    public void registerAbility(ManhuntAbility ability){
        if(this.abilites.contains(ability)) throw new IllegalArgumentException("Ability is already registered");
        this.abilites.add(ability);
    }
    private void constructInventory(Hunter hunter){
        if (hunter.getAsBukkitPlayer() != null) {
            Inventory inventory = IManhuntUtils.constructInventory(hunter, this.abilites);
            IManhuntUtils.debug("Created inventory for " + hunter.getUUID());
            if (inventory != null) {
                hunter.getAsBukkitPlayer().getInventory().setContents(inventory.getContents());
                IManhuntUtils.debug("Set contents for " + hunter.getUUID());
            }
        }
    }
    public List<ManhuntPlayer> getTeam(ManhuntTeam team){
        final List<ManhuntPlayer> players = new ArrayList<>();
        this.players.forEach(manhuntPlayer -> {
            if(manhuntPlayer.getTeam() == team) players.add(manhuntPlayer);
        });
        return players;
    }

    @Nullable
    public ManhuntAbility getAbility(String name) {
        for (ManhuntAbility ability:this.abilites) {
            if(ability.getName().equalsIgnoreCase(name)) return ability;
        }
        return null;
    }
}
