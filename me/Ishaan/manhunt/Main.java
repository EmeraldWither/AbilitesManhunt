package me.Ishaan.manhunt;

//
//LMAO THE IMPORT LIST
//
import me.Ishaan.manhunt.Abilties.DamageItem.DamageItemGUIListener;
import me.Ishaan.manhunt.Abilties.DamageItem.DamageItemListener;
import me.Ishaan.manhunt.Abilties.GravityBlocks.GravityGUIListener;
import me.Ishaan.manhunt.Abilties.GravityBlocks.GravityListener;
import me.Ishaan.manhunt.Abilties.LauncherListener.LaunchAbility;
import me.Ishaan.manhunt.Abilties.LauncherListener.LauncherGUIListener;
import me.Ishaan.manhunt.Abilties.PlayerTP.PlayerTPGUIListener;
import me.Ishaan.manhunt.Abilties.PlayerTP.PlayerTPListener;
import me.Ishaan.manhunt.Abilties.RandomTP.RandomTPGUIListener;
import me.Ishaan.manhunt.Abilties.RandomTP.RandomTPListener;
import me.Ishaan.manhunt.Abilties.Scrambler.ScramblerGUIListener;
import me.Ishaan.manhunt.Abilties.Scrambler.ScramblerListener;
import me.Ishaan.manhunt.Abilties.StrikeLightning.LightningGuiListener;
import me.Ishaan.manhunt.Abilties.StrikeLightning.LightningListener;
import me.Ishaan.manhunt.CommandHandlers.ManhuntCommandHandler;
import me.Ishaan.manhunt.CommandHandlers.ManhuntTabCompleter;
import me.Ishaan.manhunt.GUI.GUIInventoryHolder;
import me.Ishaan.manhunt.PlayerChecks.HunterChecks.*;
import me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
import me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks.EnderDragonCheck;
import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    Plugin plugin = this;
    JavaPlugin javaPlugin = (JavaPlugin) this.plugin;
    List<String> speedrunner = SpeedrunList.speedrunners;
    List<String> hunter = HunterList.hunters;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LaunchAbility(), this);
        getServer().getPluginManager().registerEvents(new LightningListener() ,this);
        getServer().getPluginManager().registerEvents(new GravityListener() ,this);
        getServer().getPluginManager().registerEvents(new PreventPlacing() ,this);
        getServer().getPluginManager().registerEvents(new CheckChest() ,this);
        getServer().getPluginManager().registerEvents(new DeathCheck() ,this);
        getServer().getPluginManager().registerEvents(new LightningGuiListener() ,this);
        getServer().getPluginManager().registerEvents(new LauncherGUIListener() ,this);
        getServer().getPluginManager().registerEvents(new GravityGUIListener() ,this);
        getServer().getPluginManager().registerEvents(new PreventPickingUp() ,this);
        getServer().getPluginManager().registerEvents(new PreventAttacking(),this);
        getServer().getPluginManager().registerEvents(new PreventDroppingItems(),this);
        getServer().getPluginManager().registerEvents(new ScramblerGUIListener(),this);
        getServer().getPluginManager().registerEvents(new ScramblerListener(),this);
        getServer().getPluginManager().registerEvents(new EnderDragonCheck(),this);
        getServer().getPluginManager().registerEvents(new RandomTPGUIListener(),this);
        getServer().getPluginManager().registerEvents(new RandomTPListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerTPGUIListener(),this);
        getServer().getPluginManager().registerEvents(new DamageItemGUIListener(),this);
        getServer().getPluginManager().registerEvents(new DamageItemListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerTPListener(),this);
        getServer().getPluginManager().registerEvents(new PreventProjectileThrowing(),this);
        getServer().getPluginManager().registerEvents(new PreventHunger(),this);



        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommandHandler());
        Objects.requireNonNull(getCommand("manhunt")).setTabCompleter(new ManhuntTabCompleter());

        getLogger().log(Level.INFO, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW ENABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                             v0.8 BETA                                  \n" +
                "|                                                                        \n" +
                "|                         BY: EMERALDWITHERYT   \n" +
                "|                                                                        \n" +
                "|             NOTE: THIS PLUGIN IS STILL UNDER DEVELOPMENT,\n" +
                "|           AS SUCH, IT WILL HAVE BUGS. PROCEED WITH CAUTION. \n" +
                "--------------------------------------------------------------");


    }

    @Override
    public void onDisable() {
        getLogger().log(Level.WARNING, "\n" +
                "--------------------------------------------------------------\n" +
                "|                            NOW DISABLING:                              \n" +
                "|                                                                        \n" +
                "|        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITES    \n" +
                "|                            v0.8 BETA                                    \n" +
                "|                                                                        \n" +
                "|                         BY: EMERALDWITHERYT   \n" +
                "|                                                                        \n" +
                "|             NOTE: THIS PLUGIN IS STILL UNDER DEVELOPMENT,\n" +
                "|           AS SUCH, IT WILL HAVE BUGS. PROCEED WITH CAUTION. \n" +
                "--------------------------------------------------------------");


        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getOpenInventory() != null) {
                if (player.getOpenInventory().getTopInventory().getHolder() instanceof GUIInventoryHolder) {
                    player.closeInventory(InventoryCloseEvent.Reason.UNLOADED);
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
            }
            if(speedrunner.contains(player.getName())){
                player.setGlowing(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.setInvulnerable(false);
                player.closeInventory();
                player.setFlying(false);
                player.setAllowFlight(false);
            }
            if(new DeathCheck().getDeadSpeedrunner(player.getName())){
                player.setGlowing(false);
                player.getInventory().clear();
                player.setGameMode(GameMode.SURVIVAL);
                player.setInvulnerable(false);
                player.closeInventory();
                player.setFlying(false);
                player.setAllowFlight(false);
            }
        }

    }

    private Plugin getPlugin(){
        Plugin plugin = this.getPlugin();
        return plugin;
    }

}


