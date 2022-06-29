package org.emeraldcraft.manhunt;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.ManhuntBackgroundTask;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.ManhuntPlayer;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntHunter;
import org.emeraldcraft.manhunt.entities.players.internal.ManhuntSpeedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.gui.ManhuntGUIManager;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.bukkit.GameMode.ADVENTURE;
import static org.bukkit.GameMode.SURVIVAL;
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
    private final ArrayList<BukkitRunnable> tasks = new ArrayList<>();
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
        for (ManhuntPlayer p : this.players) {
            if(p.getUUID().toString().equals(player.getUniqueId().toString())) throw new IllegalArgumentException("Player is already registered!");
        }
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
        List<Speedrunner> speedrunners = this.players.stream().filter(player -> player.getTeam() == ManhuntTeam.SPEEDRUNNER).map(player -> (Speedrunner) player).toList();

        for(Hunter hunter : hunters){
            //Apply hunter effects
            constructInventory(hunter);
            //TODO REMOVE THIS  | FOR TESTING ONLY!!!
            hunter.setMana(100);
            debug("Constructed inventory ");
            Player player = hunter.getAsBukkitPlayer();
            assert player != null;
            player.setGameMode(ADVENTURE);
            player.setAllowFlight(true);
            player.setFlying(true);
            player.setGlowing(true);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(1000);
            player.setInvulnerable(true);
        }
        for(Speedrunner speedrunner : speedrunners){
            Player player = speedrunner.getAsBukkitPlayer();
            assert player != null;
            player.setGameMode(SURVIVAL);
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(2000);
            //Give cool items
            player.getInventory().clear();
            player.getInventory().addItem(
                    new ItemStack(Material.WATER_BUCKET, 1),
                    new ItemStack(Material.WATER_BUCKET, 1),
                    new ItemStack(Material.GOLDEN_APPLE, 5),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE),
                    new ItemStack(Material.COOKED_BEEF, 64));
            //Add cool potion effects
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
            final Collection<PotionEffect> effects = new ArrayList<>();
            effects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
            effects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
            effects.add(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0));
            effects.add(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
            player.addPotionEffects(effects);

        }
        gameTasks.forEach(task -> {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    task.run();
                }
            };
            runnable.runTaskTimer(main, task.getDelay(), task.getPeriod());
            tasks.add(runnable);
        });
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
        for(ManhuntPlayer player : this.players){
            Player bukkitPlayer = player.getAsBukkitPlayer();
            if(bukkitPlayer == null) continue;
            bukkitPlayer.getActivePotionEffects().forEach(potionEffect -> bukkitPlayer.removePotionEffect(potionEffect.getType()));

            bukkitPlayer.getInventory().clear();
            bukkitPlayer.sendMessage(gameEnd);
            bukkitPlayer.setAllowFlight(false);
            bukkitPlayer.setFlying(false);
            bukkitPlayer.setGlowing(false);
        }
        tasks.forEach(BukkitRunnable::cancel);
        tasks.clear();
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
