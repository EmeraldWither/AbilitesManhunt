package me.Ishaan.manhunt;

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
import me.Ishaan.manhunt.PlayerChecks.HunterChecks.*;
import me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
import me.Ishaan.manhunt.PlayerChecks.SpeedrunnerChecks.EnderDragonCheck;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    Plugin plugin = this;
    JavaPlugin javaPlugin = (JavaPlugin) this.plugin;

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


        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommandHandler());

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
    }

    private Plugin getPlugin(){
        Plugin plugin = this.getPlugin();
        return plugin;
    }

}


