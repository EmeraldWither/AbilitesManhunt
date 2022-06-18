package org.emeraldcraft.manhunt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.emeraldcraft.manhunt.background.ManaDisplayTask;
import org.emeraldcraft.manhunt.background.ManaUpdaterTask;
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
    private final ArrayList<ManhuntBackgroundTask> tasks = new ArrayList<>();
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
            ((ManhuntHunter) hunter).setMana(100);
            IManhuntUtils.debug("Constructed inventory ");
        }
        //Start running mana tasks
        registerManaTasks();

        //Start running background tasks
        tasks.forEach(ManhuntBackgroundTask::start);
        isRunning = true;
        IManhuntUtils.debug("Started the game-mode.");
    }

    private void registerManaTasks() {
        tasks.add(new ManaDisplayTask(this.main));
        tasks.add(new ManaUpdaterTask(this.main));
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
        tasks.forEach(ManhuntBackgroundTask::end);
        tasks.clear();
        this.players.clear();
        this.guiManager.getGUIs().forEach(guiManager::processManhuntGUI);
        isRunning = false;
    }
    public void registerAbility(ManhuntAbility ability){
        if(this.abilities.contains(ability)) throw new IllegalArgumentException("Ability is already registered");
        this.abilities.add(ability);
    }

    public List<ManhuntAbility> getAbilities() {
        return new ArrayList<>(abilities);
    }

    private void constructInventory(Hunter hunter){
        if (hunter.getAsBukkitPlayer() != null) {
            Inventory inventory = IManhuntUtils.constructInventory(hunter, this.abilities);
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
        for (ManhuntAbility ability:this.abilities) {
            if(ability.getName().equalsIgnoreCase(name)) return ability;
        }
        return null;
    }


    public boolean isRunning() {
        return isRunning;
    }
}
