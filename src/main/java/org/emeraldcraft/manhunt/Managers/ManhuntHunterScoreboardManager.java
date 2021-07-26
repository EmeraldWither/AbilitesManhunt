package org.emeraldcraft.manhunt.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import org.emeraldcraft.manhunt.Abilties.Abilites;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.UUID;

public class ManhuntHunterScoreboardManager {

    private Manhunt manhunt;
    private Abilites abilites;
    private ManhuntMain manhuntMain;

    public ManhuntHunterScoreboardManager(Manhunt manhunt, Abilites abilites, ManhuntMain manhuntMain) {
        this.manhunt = manhunt;
        this.abilites = abilites;
        this.manhuntMain = manhuntMain;
    }

    public int id = 0;

    public void showHunterScoreboard(UUID uuid, Plugin plugin) {
        setHunterScoreboard(uuid);
        id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateHunterScoreBoard(uuid);
            }
        }, 0, 1L);
    }

    public void setHunterScoreboard(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("ECManhunt-Hunter", "dummy", ChatColor.translateAlternateColorCodes('&', "&e&lMANHUNT"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        String hunterColor = manhuntMain.getConfig().get("scoreboard.hunter-color").toString();
        String speedrunnerColor = manhuntMain.getConfig().get("scoreboard.speedrunner-color").toString();
        String deadColor = manhuntMain.getConfig().get("scoreboard.dead-color").toString();
        String frozenColor = manhuntMain.getConfig().get("scoreboard.frozen-color").toString();

        String hunterPrefix = manhuntMain.getConfig().getString("scoreboard.hunter-prefix");
        String speedrunnerPrefix = manhuntMain.getConfig().getString("scoreboard.speedrunner-prefix");
        String deadPrefix = manhuntMain.getConfig().getString("scoreboard.dead-prefix");
        String frozenPrefix = manhuntMain.getConfig().getString("scoreboard.frozen-prefix");

        //////////////////
        Team frozen = board.registerNewTeam("003frozen");
        frozen.setColor(ChatColor.valueOf(frozenColor));
        frozen.setPrefix(frozenPrefix);

        Team hunter = board.registerNewTeam("001hunter");
        hunter.setColor(ChatColor.valueOf(hunterColor));
        hunter.setPrefix(hunterPrefix);

        Team speedrunner = board.registerNewTeam("002speedrunner");
        speedrunner.setColor(ChatColor.valueOf(speedrunnerColor));
        speedrunner.setPrefix(speedrunnerPrefix);

        Team dead = board.registerNewTeam("004dead");
        dead.setColor(ChatColor.valueOf(deadColor));
        dead.setPrefix(deadPrefix);
        //////////////////



        Team aliveSpeedrunner = board.registerNewTeam("aliveSpeedrunner");
        aliveSpeedrunner.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        aliveSpeedrunner.setPrefix(ChatColor.AQUA + "Speedrunners >> " + ChatColor.DARK_AQUA + manhunt.getTeam(ManhuntTeam.SPEEDRUNNER).size() + "/" + (manhunt.getTeam(ManhuntTeam.DEAD).size() + manhunt.getTeam(ManhuntTeam.SPEEDRUNNER).size()));
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(14);

        Team aliveHunters = board.registerNewTeam("aliveHunters");
        aliveHunters.addEntry(ChatColor.RED + "" + ChatColor.BLACK);
        aliveHunters.setPrefix(ChatColor.AQUA + "Hunters >> " + ChatColor.DARK_AQUA + manhunt.getTeam(ManhuntTeam.HUNTER).size());
        obj.getScore(ChatColor.RED + "" + ChatColor.BLACK).setScore(13);

        Score score12 = obj.getScore(ChatColor.GOLD + "");
        score12.setScore(12);

        Team lightningCooldown = board.registerNewTeam("lightCooldown");
        lightningCooldown.addEntry(ChatColor.AQUA + "" + ChatColor.DARK_BLUE);
        lightningCooldown.setPrefix(ChatColor.DARK_AQUA + "Lightning Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.LIGHTNING, abilites));
        obj.getScore(ChatColor.AQUA + "" + ChatColor.DARK_BLUE).setScore(11);

        Team launcherCooldown = board.registerNewTeam("launcherCooldown");
        launcherCooldown.addEntry(ChatColor.DARK_RED + "" + ChatColor.GREEN);
        launcherCooldown.setPrefix(ChatColor.DARK_AQUA + "Launcher Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.LAUNCHER, abilites));
        obj.getScore(ChatColor.DARK_RED + "" + ChatColor.GREEN).setScore(10);

        Team freezeCooldown = board.registerNewTeam("freezeCooldown");
        freezeCooldown.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD);
        freezeCooldown.setPrefix(ChatColor.DARK_AQUA + "Freeze Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.FREEZER, abilites));
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD).setScore(9);

        Team damageAbility = board.registerNewTeam("damageCooldown");
        damageAbility.addEntry(ChatColor.DARK_GREEN + "" + ChatColor.BLACK);
        damageAbility.setPrefix(ChatColor.DARK_AQUA + "Damage Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.DAMAGEITEM, abilites));
        obj.getScore(ChatColor.DARK_GREEN + "" + ChatColor.BLACK).setScore(8);

        Team scrambleAbility = board.registerNewTeam("scrambleCooldown");
        scrambleAbility.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.GREEN);
        scrambleAbility.setPrefix(ChatColor.DARK_AQUA + "Scramble Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.SCRAMBLE, abilites));
        obj.getScore(ChatColor.DARK_GRAY + "" + ChatColor.GREEN).setScore(7);

        Team gravityAbility = board.registerNewTeam("gravityCooldown");
        gravityAbility.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.DARK_GREEN);
        gravityAbility.setPrefix(ChatColor.DARK_AQUA + "Gravity Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.GRAVITY, abilites));
        obj.getScore(ChatColor.DARK_GRAY + "" + ChatColor.DARK_GREEN).setScore(6);

        Team randomTPAbility = board.registerNewTeam("randTPCooldown");
        randomTPAbility.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.BLUE);
        randomTPAbility.setPrefix(ChatColor.DARK_AQUA + "RandomTP Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.RANDOMTP, abilites));
        obj.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BLUE).setScore(5);

        Team commandMobsAbility = board.registerNewTeam("commandCooldown");
        commandMobsAbility.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.BLUE);
        commandMobsAbility.setPrefix(ChatColor.DARK_AQUA + "Command Mobs Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.TARGETMOB, abilites));
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.BLUE).setScore(4);


        Score creditspace = obj.getScore(ChatColor.RED + "");
        creditspace.setScore(2);
        Team credit = board.registerNewTeam("credit");
        credit.addEntry(ChatColor.RED + "" + ChatColor.GOLD + "" + ChatColor.BLACK);
        credit.setPrefix(ChatColor.GRAY + "Made by EmeraldWither");
        obj.getScore(ChatColor.RED + "" + ChatColor.GOLD + "" + ChatColor.BLACK).setScore(1);

        player.setScoreboard(board);
    }

    public void updateHunterScoreBoard(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        Scoreboard board = player.getScoreboard();

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (manhunt.getTeam(ManhuntTeam.HUNTER).contains(player1.getUniqueId())) {
                if (!board.getTeam("001hunter").getEntries().contains(player1.getUniqueId()))
                    board.getTeam("001hunter").addEntry(player1.getName());
            }
            if (manhunt.getTeam(ManhuntTeam.FROZEN).contains(player1.getUniqueId())) {
                if (!board.getTeam("003frozen").getEntries().contains(player1.getUniqueId())) {
                    board.getTeam("003frozen").addEntry(player1.getName());
                }
            }
            if(manhunt.getTeam(ManhuntTeam.DEAD).contains(player1.getUniqueId())) {
                if (!board.getTeam("004dead").getEntries().contains(player1.getUniqueId())) {
                    board.getTeam("004dead").addEntry(player1.getName());
                }
            }
            if(manhunt.getTeam(ManhuntTeam.SPEEDRUNNER).contains(player1.getUniqueId()) && !manhunt.getTeam(ManhuntTeam.FROZEN).contains(player1.getUniqueId()) ) {
                if (!board.getTeam("002speedrunner").getEntries().contains(player1.getUniqueId())) {
                    board.getTeam("002speedrunner").addEntry(player1.getName());
                }
            }
        }


            int totalPlayers = manhunt.getTeam(ManhuntTeam.DEAD).size() + manhunt.getTeam(ManhuntTeam.SPEEDRUNNER).size();

            board.getTeam("aliveSpeedrunner").setPrefix(ChatColor.AQUA + "Speedrunners >> " + ChatColor.DARK_AQUA + manhunt.getTeam(ManhuntTeam.SPEEDRUNNER).size() + "/" + totalPlayers);
            board.getTeam("aliveHunters").setPrefix(ChatColor.AQUA + "Hunters >> " + ChatColor.DARK_AQUA + manhunt.getTeam(ManhuntTeam.HUNTER).size());
            board.getTeam("lightCooldown").setPrefix(ChatColor.DARK_AQUA + "Lightning Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.LIGHTNING, abilites));
            board.getTeam("launcherCooldown").setPrefix(ChatColor.DARK_AQUA + "Launcher Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.LAUNCHER, abilites));
            board.getTeam("freezeCooldown").setPrefix(ChatColor.DARK_AQUA + "Freeze Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.FREEZER, abilites));
            board.getTeam("damageCooldown").setPrefix(ChatColor.DARK_AQUA + "Damage Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.DAMAGEITEM, abilites));
            board.getTeam("scrambleCooldown").setPrefix(ChatColor.DARK_AQUA + "Scramble Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.SCRAMBLE, abilites));
            board.getTeam("gravityCooldown").setPrefix(ChatColor.DARK_AQUA + "Gravity Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.GRAVITY, abilites));
            board.getTeam("randTPCooldown").setPrefix(ChatColor.DARK_AQUA + "RandomTP Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.RANDOMTP, abilites));
            board.getTeam("commandCooldown").setPrefix(ChatColor.DARK_AQUA + "Command Mobs Ability >> " + ChatColor.DARK_AQUA + manhunt.getCooldown(player, Ability.TARGETMOB, abilites));
    }
}