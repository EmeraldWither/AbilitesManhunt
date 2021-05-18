package me.EmeraldWither.manhunt;

import me.EmeraldWither.manhunt.Abilties.CooldownsManager;
import me.EmeraldWither.manhunt.Abilties.DamageItem.DamageItemGUIListener;
import me.EmeraldWither.manhunt.Abilties.DamageItem.DamageItemListener;
import me.EmeraldWither.manhunt.Abilties.Freeze.FreezeGUIListener;
import me.EmeraldWither.manhunt.Abilties.Freeze.FreezeListener;
import me.EmeraldWither.manhunt.Abilties.GravityBlocks.GravityGUIListener;
import me.EmeraldWither.manhunt.Abilties.GravityBlocks.GravityListener;
import me.EmeraldWither.manhunt.Abilties.LauncherListener.LaunchAbility;
import me.EmeraldWither.manhunt.Abilties.LauncherListener.LauncherGUIListener;
import me.EmeraldWither.manhunt.Abilties.PlayerTP.PlayerTPGUIListener;
import me.EmeraldWither.manhunt.Abilties.PlayerTP.PlayerTPListener;
import me.EmeraldWither.manhunt.Abilties.RandomTP.RandomTPGUIListener;
import me.EmeraldWither.manhunt.Abilties.RandomTP.RandomTPListener;
import me.EmeraldWither.manhunt.Abilties.Scrambler.ScramblerGUIListener;
import me.EmeraldWither.manhunt.Abilties.Scrambler.ScramblerListener;
import me.EmeraldWither.manhunt.Abilties.StrikeLightning.LightningGuiListener;
import me.EmeraldWither.manhunt.Abilties.StrikeLightning.LightningListener;
import me.EmeraldWither.manhunt.Abilties.TargetMobs.TargetMobGUIListener;
import me.EmeraldWither.manhunt.Abilties.TargetMobs.TargetMobListener;
import me.EmeraldWither.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.EmeraldWither.manhunt.CommandHandlers.ManhuntTabCompleter;
import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.GUI.GUIInventoryHolder;
import me.EmeraldWither.manhunt.Mana.Manacounter;
import me.EmeraldWither.manhunt.PlayerChecks.HunterChecks.*;
import me.EmeraldWither.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
import me.EmeraldWither.manhunt.PlayerChecks.SpeedrunnerChecks.EnderDragonCheck;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public Plugin plugin = this;
    @Override
    public void onEnable(){
        ManhuntGameManager manhuntGameManager = new ManhuntGameManager();
        CooldownsManager cooldownsManager = new CooldownsManager();
        Manacounter manacounter = new Manacounter(manhuntGameManager,this);

        getServer().getPluginManager().registerEvents(new LaunchAbility(manhuntGameManager, this, manacounter), this);
        getServer().getPluginManager().registerEvents(new LightningListener(manhuntGameManager, this, manacounter) ,this);
        getServer().getPluginManager().registerEvents(new GravityListener(manhuntGameManager, this, manacounter) ,this);
        getServer().getPluginManager().registerEvents(new PreventPlacing(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new CheckChest(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new DeathCheck(manhuntGameManager, this, manacounter, cooldownsManager) ,this);
        getServer().getPluginManager().registerEvents(new LightningGuiListener(manhuntGameManager, this, manacounter) ,this);
        getServer().getPluginManager().registerEvents(new LauncherGUIListener(manhuntGameManager, this, manacounter) ,this);
        getServer().getPluginManager().registerEvents(new GravityGUIListener(manhuntGameManager, this, manacounter) ,this);
        getServer().getPluginManager().registerEvents(new PreventPickingUp(manhuntGameManager, this) ,this);
        getServer().getPluginManager().registerEvents(new PreventAttacking(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new PreventDroppingItems(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new ScramblerGUIListener(manhuntGameManager, this,  manacounter),this);
        getServer().getPluginManager().registerEvents(new ScramblerListener(manhuntGameManager, this, manacounter), this);
        getServer().getPluginManager().registerEvents(new EnderDragonCheck(manhuntGameManager, this, cooldownsManager, manacounter),this);
        getServer().getPluginManager().registerEvents(new RandomTPGUIListener(manhuntGameManager, this, manacounter),this);
        getServer().getPluginManager().registerEvents(new RandomTPListener(manhuntGameManager, this, manacounter), this);
        getServer().getPluginManager().registerEvents(new PlayerTPGUIListener(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new DamageItemGUIListener(manhuntGameManager, this, manacounter),this);
        getServer().getPluginManager().registerEvents(new DamageItemListener(manhuntGameManager, this, manacounter),this);
        getServer().getPluginManager().registerEvents(new PlayerTPListener(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new PreventProjectileThrowing(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new PreventHunger(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new TargetMobListener(manhuntGameManager, this, manacounter),this);
        getServer().getPluginManager().registerEvents(new TargetMobGUIListener(manhuntGameManager, this, manacounter),this);
        getServer().getPluginManager().registerEvents(new ClearInv(manhuntGameManager, this),this);
        getServer().getPluginManager().registerEvents(new FreezeGUIListener(manhuntGameManager, this, manacounter),this);
        getServer().getPluginManager().registerEvents(new FreezeListener(manhuntGameManager, this, manacounter),this);
            this.saveDefaultConfig();



        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommandHandler(manhuntGameManager, this, manacounter, cooldownsManager));
        Objects.requireNonNull(getCommand("manhunt")).setTabCompleter(new ManhuntTabCompleter());

        getLogger().log(Level.INFO, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW ENABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                             v0.9 BETA                                  \n" +
                "|                                                                        \n" +
                "|                        BY: EMERALDWITHERYT   \n" +
                "|                                                                        \n" +
                "|              NOTE: THIS PLUGIN IS STILL UNDER DEVELOPMENT,\n" +
                "|           AS SUCH, IT WILL HAVE BUGS. PROCEED WITH CAUTION. \n" +
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
                "|                            v0.9 BETA                                    \n" +
                "|                                                                        \n" +
                "|                         BY: EMERALDWITHERYT   \n" +
                "|                                                                        \n" +
                "|              NOTE: THIS PLUGIN IS STILL UNDER DEVELOPMENT,\n" +
                "|           AS SUCH, IT WILL HAVE BUGS. PROCEED WITH CAUTION. \n" +
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


