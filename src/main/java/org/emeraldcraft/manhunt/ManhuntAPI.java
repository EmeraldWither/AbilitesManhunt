package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;
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
    private BukkitTask bukkitTask;
    private boolean isRunning = false;

    public ManhuntAPI(ManhuntMain main){
        this.main = main;
        configValues = new ManhuntConfigValues(main.getConfig());
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
        List<Hunter> hunters = this.players.stream().filter(player -> player.getTeam() == ManhuntTeam.HUNTER).map(player -> (Hunter) player).toList();
        List<Speedrunner> speedrunners = this.players.stream().filter(player -> player.getTeam() == ManhuntTeam.SPEEDRUNNER).map(player -> (Speedrunner) player).toList();

        for(Hunter hunter : hunters){
            constructInventory(hunter);
            ((ManhuntHunter) hunter).setMana(100);
            IManhuntUtils.debug("Constructed inventory ");
        }
        startManaScheduler();
        isRunning = true;
        IManhuntUtils.debug("Started the gamemode. ");
    }
    public void end(){
        this.bukkitTask.cancel();
        for(ManhuntPlayer player : this.getTeam(ManhuntTeam.HUNTER)){
            ManhuntHunter hunter = (ManhuntHunter) player;
            if(hunter.getAsBukkitPlayer() == null) continue;
            hunter.getAsBukkitPlayer().getInventory().clear();
        }
        isRunning = false;
    }
    public void registerAbility(ManhuntAbility ability){
        if(this.abilites.contains(ability)) throw new IllegalArgumentException("Ability is already registered");
        this.abilites.add(ability);
    }

    public List<ManhuntAbility> getAbilities() {
        return new ArrayList<>(abilites);
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
    private void startManaScheduler(){
        bukkitTask = Bukkit.getScheduler().runTaskTimer(main, () -> {
            Manhunt.getAPI().getTeam(ManhuntTeam.HUNTER).forEach(player -> {
                ((ManhuntHunter) player).addMana();
                if(player.getAsBukkitPlayer() == null) return;
                player.getAsBukkitPlayer().sendActionBar(ChatColor.GREEN + "Mana: " + ChatColor.GRAY + ((Hunter) player).getMana());
            });

        }, 0, 20L);
    }

    public boolean isRunning() {
        return isRunning;
    }
}
