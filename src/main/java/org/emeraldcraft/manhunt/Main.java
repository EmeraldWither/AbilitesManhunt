package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Abilties.DamageItem.DamageItemGUIListener;
import org.emeraldcraft.manhunt.Abilties.DamageItem.DamageItemListener;
import org.emeraldcraft.manhunt.Abilties.Freeze.FreezeGUIListener;
import org.emeraldcraft.manhunt.Abilties.Freeze.FreezeListener;
import org.emeraldcraft.manhunt.Abilties.GravityBlocks.GravityGUIListener;
import org.emeraldcraft.manhunt.Abilties.GravityBlocks.GravityListener;
import org.emeraldcraft.manhunt.Abilties.LauncherListener.LaunchAbility;
import org.emeraldcraft.manhunt.Abilties.LauncherListener.LauncherGUIListener;
import org.emeraldcraft.manhunt.Abilties.PlayerTP.PlayerTPGUIListener;
import org.emeraldcraft.manhunt.Abilties.PlayerTP.PlayerTPListener;
import org.emeraldcraft.manhunt.Abilties.RandomTP.RandomTPGUIListener;
import org.emeraldcraft.manhunt.Abilties.RandomTP.RandomTPListener;
import org.emeraldcraft.manhunt.Abilties.Scrambler.ScramblerGUIListener;
import org.emeraldcraft.manhunt.Abilties.Scrambler.ScramblerListener;
import org.emeraldcraft.manhunt.Abilties.StrikeLightning.LightningGuiListener;
import org.emeraldcraft.manhunt.Abilties.StrikeLightning.LightningListener;
import org.emeraldcraft.manhunt.Abilties.TargetMobs.TargetMobGUIListener;
import org.emeraldcraft.manhunt.Abilties.TargetMobs.TargetMobListener;
import org.emeraldcraft.manhunt.CommandHandlers.ManhuntCommandHandler;
import org.emeraldcraft.manhunt.CommandHandlers.ManhuntTabCompleter;
import org.emeraldcraft.manhunt.Enums.Team;
import org.emeraldcraft.manhunt.GUI.GUIInventoryHolder;
import org.emeraldcraft.manhunt.Mana.Manacounter;
import org.emeraldcraft.manhunt.PlayerChecks.HunterChecks.*;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.EnderDragonCheck;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public Plugin plugin = this;
    @Override
    public void onEnable(){
        ManhuntGameManager manhuntGameManager = new ManhuntGameManager();
        Manacounter manacounter = new Manacounter(manhuntGameManager,this);
        AbilitesManager abilitesManager = new AbilitesManager(manhuntGameManager);

        getServer().getPluginManager().registerEvents(new LaunchAbility(manhuntGameManager, this, manacounter, abilitesManager), this);
        getServer().getPluginManager().registerEvents(new LightningListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new GravityListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new PreventPlacing(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new CheckChest(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new DeathCheck(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new LightningGuiListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new LauncherGUIListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new GravityGUIListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new PreventPickingUp(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new PreventAttacking(manhuntGameManager),this);
        getServer().getPluginManager().registerEvents(new PreventDroppingItems(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new ScramblerGUIListener(manhuntGameManager, this, manacounter,abilitesManager),this);
        getServer().getPluginManager().registerEvents(new ScramblerListener(manhuntGameManager, this, manacounter, abilitesManager), this);
        getServer().getPluginManager().registerEvents(new EnderDragonCheck(manhuntGameManager, this, abilitesManager, manacounter),this);
        getServer().getPluginManager().registerEvents(new RandomTPGUIListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new RandomTPListener(manhuntGameManager, this, manacounter, abilitesManager), this);
        getServer().getPluginManager().registerEvents(new PlayerTPGUIListener(manhuntGameManager, this, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new DamageItemGUIListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new DamageItemListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new PlayerTPListener(manhuntGameManager, this, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new PreventProjectileThrowing(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new PreventHunger(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new TargetMobListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new TargetMobGUIListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new ClearInv(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new FreezeGUIListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new FreezeListener(manhuntGameManager, this, manacounter, abilitesManager),this);
        getServer().getPluginManager().registerEvents(new PreventAdvancements(manhuntGameManager, this), this);
        this.saveDefaultConfig();



        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommandHandler(manhuntGameManager, this, manacounter, abilitesManager));
        Objects.requireNonNull(getCommand("manhunt")).setTabCompleter(new ManhuntTabCompleter());

        getLogger().log(Level.INFO, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW ENABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                            v1.2 RELEASE                                  \n" +
                "|                                                                        \n" +
                "|                        BY: EMERALDWITHERYT   \n" +
                "--------------------------------------------------------------");


    }

    @Override
    public void onDisable() {

        ManhuntGameManager manhuntGameManager = new ManhuntGameManager();


        List<String> speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        List<String> hunter = manhuntGameManager.getTeam(Team.HUNTER);


        getLogger().log(Level.WARNING, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW DISABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                            v1.2 RELEASE                                  \n" +
                "|                                                                        \n" +
                "|                         BY: EMERALDWITHERYT   \n" +
                "--------------------------------------------------------------");


        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getOpenInventory() != null) {
                if (player.getOpenInventory().getTopInventory().getHolder() instanceof GUIInventoryHolder) {
                    player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
                }
            }
            for(ItemStack item : player.getInventory().getStorageContents()){
                ManHuntInventory inv = new ManHuntInventory();
                if(item != null) {
                    if (item.isSimilar(inv.getDamageItem())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getGravity())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getLauncher())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getLightning())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getrandomTP())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getPlayerTP())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getScrambler())) {
                        player.getInventory().remove(item);
                    }
                    if (item.isSimilar(inv.getMobTargeter())) {
                        player.getInventory().remove(item);
                    }
                }
            }
            if(hunter.contains(player.getName())){
                player.setGlowing(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.setInvulnerable(false);
                player.closeInventory();
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setSaturation(5);
                for(PotionEffect potionEffect: player.getActivePotionEffects()){
                    player.removePotionEffect(potionEffect.getType());
                }
            }
            if(speedrunner.contains(player.getName())){
                player.setGlowing(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.setInvulnerable(false);
                player.closeInventory();
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setSaturation(5);
                for(PotionEffect potionEffect: player.getActivePotionEffects()){
                    player.removePotionEffect(potionEffect.getType());
                }
            }
            if(manhuntGameManager.getTeam(Team.DEAD).contains(player.getName())){
                player.setGlowing(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.setInvulnerable(false);
                player.closeInventory();
                player.setFlying(false);
                player.setAllowFlight(false);
                player.setSaturation(5);
            }

        }

    }

    private Plugin getPlugin(){
        Plugin plugin = this.getPlugin();
        return plugin;
    }

}

