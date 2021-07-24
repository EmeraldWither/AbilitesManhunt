package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
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
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.DataManager;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;
import org.emeraldcraft.manhunt.Managers.ManhuntHunterScoreboardManager;
import org.emeraldcraft.manhunt.PlayerChecks.HunterChecks.*;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.EnderDragonCheck;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.GiveSpeedrunnerScoreboard;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.PushAwayHunter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;

import static java.util.logging.Level.INFO;

public class ManhuntMain extends JavaPlugin {

    public DataManager data;
    ManhuntGameManager manhuntGameManager;
    Manacounter manacounter;
    AbilitesManager abilitesManager;
    ManhuntHunterScoreboardManager manhuntScoreboardManager;


    @Override
    public void onEnable(){
        long time = System.currentTimeMillis();
        manhuntGameManager = new ManhuntGameManager();
        this.data = new DataManager(this);
        this.abilitesManager = new AbilitesManager(manhuntGameManager);
        this.manacounter = new Manacounter(manhuntGameManager,this);
        this.manhuntScoreboardManager = new ManhuntHunterScoreboardManager(manhuntGameManager, abilitesManager, this);
        registerListeners();
        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommandHandler(manhuntGameManager, this, manacounter, abilitesManager));
        Objects.requireNonNull(getCommand("manhunt")).setTabCompleter(new ManhuntTabCompleter(manhuntGameManager));

        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.admin"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.setmana"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.addhunter"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.addspeedrunner"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.removeplayer"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.forceend"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.reload"));
        this.getPlugin().getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.start"));
        this.saveDefaultConfig();




        getLogger().log(INFO, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW ENABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                            v1.3 RELEASE                                  \n" +
                "|                                                                        \n" +
                "|                        BY: EMERALDWITHERYT   \n" +
                "--------------------------------------------------------------");
        getLogger().log(INFO, "The plugin started up in " + (System.currentTimeMillis() - time) + " ms!");
    }

    @Override
    public void onDisable() {
        List<UUID> speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        List<UUID> hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);


        getLogger().log(Level.WARNING, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW DISABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                            v1.3 RELEASE                                  \n" +
                "|                                                                        \n" +
                "|                         BY: EMERALDWITHERYT   \n" +
                "--------------------------------------------------------------");


        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getOpenInventory() != null) {
                player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
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
            if(hunter.contains(player.getUniqueId())){
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
            if(speedrunner.contains(player.getUniqueId())){
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
            if(manhuntGameManager.getTeam(ManhuntTeam.DEAD).contains(player.getUniqueId())){
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

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new LaunchAbility(manhuntGameManager, this, manacounter, abilitesManager), this);
        getServer().getPluginManager().registerEvents(new LightningListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new GravityListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new PreventPlacing(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new CheckChest(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new DeathCheck(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new LightningGuiListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new LauncherGUIListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new GravityGUIListener(manhuntGameManager, this, manacounter, abilitesManager) ,this);
        getServer().getPluginManager().registerEvents(new PreventPickingUp(manhuntGameManager) ,this);
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
        getServer().getPluginManager().registerEvents(new GiveHunterScoreboard(manhuntGameManager, this, abilitesManager), this);
        getServer().getPluginManager().registerEvents(new GiveSpeedrunnerScoreboard(manhuntGameManager, this), this);
        getServer().getPluginManager().registerEvents(new PreventInteraction(manhuntGameManager), this);
        getServer().getPluginManager().registerEvents(new PreventDamage(manhuntGameManager), this);
        getServer().getPluginManager().registerEvents(new PreventGettingClose(manhuntGameManager, this), this);
        getServer().getPluginManager().registerEvents(new PushAwayHunter(manhuntGameManager, this), this);
        getServer().getPluginManager().registerEvents(new ResourcePackListener(manhuntGameManager), this);
    }
    public Plugin getPlugin(){
        return this;
    }

    public DataManager getDataConfig() {
        return data;
    }
}


