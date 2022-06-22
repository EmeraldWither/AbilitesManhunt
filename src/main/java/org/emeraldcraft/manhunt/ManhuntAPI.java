package org.emeraldcraft.manhunt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.ManhuntBackgroundTask;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntSpeedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.gui.ManhuntGUIManager;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

/**
 * The API class which provides the control of the plugin.
 *
 * The entrypoint is {@link Manhunt#getAPI()}
 */
public class ManhuntAPI {
    private final ManhuntMain main;
    private final ManhuntConfigValues configValues;
    private final List<ManhuntPlayer> players = new ArrayList<>();
    private final List<ManhuntAbility> abilities = new ArrayList<>();
    private final ManhuntGUIManager guiManager = new ManhuntGUIManager();
    private final ArrayList<ManhuntBackgroundTask> gameTasks = new ArrayList<>();
    private boolean isRunning = false;

    public ManhuntAPI(ManhuntMain main){
        this.main = main;
        configValues = new ManhuntConfigValues(main.getConfig());
    }

    /**
     * @return The configuration values of the plugin
     */
    public ManhuntConfigValues getConfig(){
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
    public void start(){
        List<Hunter> hunters = this.players.stream().filter(player -> player.getTeam() == ManhuntTeam.HUNTER).map(player -> (Hunter) player).toList();

        for(Hunter hunter : hunters){
            constructInventory(hunter);
            hunter.setMana(100);
            debug("Constructed inventory ");
        }
        gameTasks.forEach(ManhuntBackgroundTask::end);
        gameTasks.forEach(ManhuntBackgroundTask::start);
        isRunning = true;
        debug("Started the game");

    }
    public void registerBackgroundTask(ManhuntBackgroundTask task) {
    	gameTasks.add(task);
    }

    public ManhuntGUIManager getGUIManager(){
        return this.guiManager;
    }

    public void end(){
        final Component gameEnd = Component.text("The game has ended!").color(TextColor.color(255, 0, 0));
        for(ManhuntPlayer player : this.getTeam(ManhuntTeam.HUNTER)){
            ManhuntHunter hunter = (ManhuntHunter) player;
            if(hunter.getAsBukkitPlayer() == null) continue;
            Player bukkitPlayer = hunter.getAsBukkitPlayer();
            bukkitPlayer.getInventory().clear();
            bukkitPlayer.sendMessage(gameEnd);
        }        
        gameTasks.forEach(ManhuntBackgroundTask::end);
        this.players.clear();
        this.guiManager.getGUIs().forEach(guiManager::processManhuntGUI);
        isRunning = false;
        debug("Ended the game");
    }
    public void registerAbility(ManhuntAbility ability){
        if(this.abilities.contains(ability)) throw new IllegalArgumentException("Ability is already registered");
        this.abilities.add(ability);
        debug("Registered ability " + ability.name());
    }

    public List<ManhuntAbility> getAbilities() {
        return new ArrayList<>(abilities);
    }

    private void constructInventory(Hunter hunter){
        if (hunter.getAsBukkitPlayer() != null) {
            Inventory inventory = IManhuntUtils.constructInventory(hunter, this.abilities);
            debug("Created inventory for " + hunter.getUUID());
            if (inventory != null) {
                hunter.getAsBukkitPlayer().getInventory().setContents(inventory.getContents());
                debug("Set contents for " + hunter.getUUID());
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
        for (ManhuntAbility ability:this.abilities) {
            if(ability.name().equalsIgnoreCase(name)) return ability;
        }
        return null;
    }


    public boolean isRunning() {
        return isRunning;
    }
}
