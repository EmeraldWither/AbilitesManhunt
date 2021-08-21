package org.emeraldcraft.manhunt;

import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.Abilties.Abilites;
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
import org.emeraldcraft.manhunt.Managers.DataManager;
import org.emeraldcraft.manhunt.Managers.ManhuntHunterScoreboardManager;
import org.emeraldcraft.manhunt.PlayerChecks.ClearStragglers;
import org.emeraldcraft.manhunt.PlayerChecks.HunterChecks.*;
import org.emeraldcraft.manhunt.PlayerChecks.ResourcePackListener;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.DeathCheck;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.EnderDragonCheck;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.GiveSpeedrunnerScoreboard;
import org.emeraldcraft.manhunt.PlayerChecks.SpeedrunnerChecks.PushAwayHunter;

import java.util.Objects;
import java.util.logging.Level;

import static java.util.logging.Level.INFO;

public class ManhuntMain extends JavaPlugin {

    private DataManager data;
    private Manhunt manhunt;
    private Manacounter manacounter;
    private Abilites abilites;
    private DataBase dataBase;

    @Override
    public void onEnable(){
        long time = System.currentTimeMillis();
        manhunt = new Manhunt(this);

        String url = getConfig().getString("mysql.database-url");
        Integer port = getConfig().getInt("mysql.database-port");
        String dbname = getConfig().getString("mysql.database-name");
        String username = getConfig().getString("mysql.database-username");
        String password = getConfig().getString("mysql.database-password");

        this.dataBase = new DataBase(url, port, dbname, username, password);
        this.data = new DataManager(this);
        this.abilites = new Abilites(manhunt);
        this.manacounter = new Manacounter(manhunt,this);
        new ManhuntHunterScoreboardManager(manhunt, abilites, this);

        registerListeners();

        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManhuntCommandHandler(manhunt, this, manacounter, abilites));
        Objects.requireNonNull(getCommand("manhunt")).setTabCompleter(new ManhuntTabCompleter(manhunt, this));

        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.admin"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.setmana"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.addhunter"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.addspeedrunner"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.removeplayer"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.forceend"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.reload"));
        this.getServer().getPluginManager().addPermission(new Permission("abilitiesmanhunt.start"));

        this.saveDefaultConfig();
        manhunt.reloadConfig();


        getLogger().log(INFO, """

                --------------------------------------------------------------
                |                            NOW ENABLING:                             \s
                |                                                                       \s
                |        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITIES   \s
                |                            v1.4 RELEASE                                 \s
                |               THIS IS A DEVELOPER RELEASE, BUGS WILL OCCUR              \s
                |                        BY: EmerqldWither  \s
                --------------------------------------------------------------""");
        getLogger().log(INFO, "The plugin started up in " + (System.currentTimeMillis() - time) + " ms!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.WARNING, """

                --------------------------------------------------------------
                |                            NOW DISABLING:                             \s
                |                                                                       \s
                |        MINECRAFT MANHUNT, BUT THE HUNTER HAS SPECIAL ABILITIES   \s
                |                            v1.4 RELEASE                                 \s
                |               THIS IS A DEVELOPER RELEASE, BUGS WILL OCCUR              \s
                |                         BY: EmerqldWither  \s
                --------------------------------------------------------------""");
        getDataBase().closeConnection();
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new LaunchAbility(manhunt, this, manacounter, abilites), this);
        getServer().getPluginManager().registerEvents(new LightningListener(manhunt, this, manacounter, abilites) ,this);
        getServer().getPluginManager().registerEvents(new GravityListener(manhunt, this, manacounter, abilites) ,this);
        getServer().getPluginManager().registerEvents(new PreventPlacing(manhunt, this) ,this);
        getServer().getPluginManager().registerEvents(new CheckChest(manhunt, this) ,this);
        getServer().getPluginManager().registerEvents(new DeathCheck(manhunt, this, manacounter, abilites) ,this);
        getServer().getPluginManager().registerEvents(new LightningGuiListener(manhunt, this, manacounter, abilites) ,this);
        getServer().getPluginManager().registerEvents(new LauncherGUIListener(manhunt, this, manacounter, abilites) ,this);
        getServer().getPluginManager().registerEvents(new GravityGUIListener(manhunt, this, manacounter, abilites) ,this);
        getServer().getPluginManager().registerEvents(new PreventPickingUp(manhunt) ,this);
        getServer().getPluginManager().registerEvents(new PreventAttacking(manhunt),this);
        getServer().getPluginManager().registerEvents(new PreventDroppingItems(manhunt, this),this);
        getServer().getPluginManager().registerEvents(new ScramblerGUIListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new ScramblerListener(manhunt, this, manacounter, abilites), this);
        getServer().getPluginManager().registerEvents(new EnderDragonCheck(manhunt, this, abilites, manacounter),this);
        getServer().getPluginManager().registerEvents(new RandomTPGUIListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new RandomTPListener(manhunt, this, manacounter, abilites), this);
        getServer().getPluginManager().registerEvents(new PlayerTPGUIListener(manhunt, this, abilites),this);
        getServer().getPluginManager().registerEvents(new DamageItemGUIListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new DamageItemListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new PlayerTPListener(manhunt, this, abilites),this);
        getServer().getPluginManager().registerEvents(new PreventProjectileThrowing(manhunt),this);
        getServer().getPluginManager().registerEvents(new PreventHunger(manhunt, this),this);
        getServer().getPluginManager().registerEvents(new TargetMobListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new TargetMobGUIListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new ClearStragglers(manhunt),this);
        getServer().getPluginManager().registerEvents(new FreezeGUIListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new FreezeListener(manhunt, this, manacounter, abilites),this);
        getServer().getPluginManager().registerEvents(new PreventAdvancements(manhunt, this), this);
        getServer().getPluginManager().registerEvents(new GiveHunterScoreboard(manhunt, this, abilites), this);
        getServer().getPluginManager().registerEvents(new GiveSpeedrunnerScoreboard(manhunt, this), this);
        getServer().getPluginManager().registerEvents(new PreventInteraction(manhunt), this);
        getServer().getPluginManager().registerEvents(new PreventDamage(manhunt), this);
        getServer().getPluginManager().registerEvents(new PreventGettingClose(manhunt, this), this);
        getServer().getPluginManager().registerEvents(new PushAwayHunter(manhunt, this), this);
        getServer().getPluginManager().registerEvents(new ResourcePackListener(manhunt), this);
        getServer().getPluginManager().registerEvents(new PreventEXP(manhunt), this);
        getServer().getPluginManager().registerEvents(new GiveFly(manhunt), this);
    }
    public DataManager getDataConfig() {
        return data;
    }
    public DataBase getDataBase() {
        return dataBase;
    }
}


