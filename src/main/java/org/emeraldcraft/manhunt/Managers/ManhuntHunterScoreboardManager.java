package org.emeraldcraft.manhunt.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.Ability;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;

import java.util.UUID;

public class ManhuntHunterScoreboardManager {

    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;

    public ManhuntHunterScoreboardManager(ManhuntGameManager manhuntGameManager, AbilitesManager abilitesManager) {
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = abilitesManager;
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



        //////////////////
        Team frozen = board.registerNewTeam("frozen");
        frozen.setColor(ChatColor.AQUA);
        frozen.setPrefix("[FROZEN] ");

        Team hunter = board.registerNewTeam("hunter");
        hunter.setColor(ChatColor.RED);
        hunter.setPrefix("[HUNTER] ");

        Team speedrunner = board.registerNewTeam("speedrunner");
        speedrunner.setColor(ChatColor.GREEN);
        speedrunner.setPrefix("[SPEEDRUNNER] ");

        Team dead = board.registerNewTeam("dead");
        dead.setColor(ChatColor.DARK_GRAY);
        dead.setPrefix("[DEAD] ");
        //////////////////



        Team aliveSpeedrunner = board.registerNewTeam("aliveSpeedrunner");
        aliveSpeedrunner.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
        aliveSpeedrunner.setPrefix(ChatColor.AQUA + "Speedrunners >> " + ChatColor.DARK_AQUA + manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).size() + "/" + (manhuntGameManager.getTeam(ManhuntTeam.DEAD).size() + manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).size()));
        obj.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(14);

        Team aliveHunters = board.registerNewTeam("aliveHunters");
        aliveHunters.addEntry(ChatColor.RED + "" + ChatColor.BLACK);
        aliveHunters.setPrefix(ChatColor.AQUA + "Hunters >> " + ChatColor.DARK_AQUA + manhuntGameManager.getTeam(ManhuntTeam.HUNTER).size());
        obj.getScore(ChatColor.RED + "" + ChatColor.BLACK).setScore(13);

        Score score12 = obj.getScore(ChatColor.GOLD + "");
        score12.setScore(12);

        Team lightningCooldown = board.registerNewTeam("lightCooldown");
        lightningCooldown.addEntry(ChatColor.AQUA + "" + ChatColor.DARK_BLUE);
        lightningCooldown.setPrefix(ChatColor.DARK_AQUA + "Lightning Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.LIGHTNING, abilitesManager));
        obj.getScore(ChatColor.AQUA + "" + ChatColor.DARK_BLUE).setScore(11);

        Team launcherCooldown = board.registerNewTeam("launcherCooldown");
        launcherCooldown.addEntry(ChatColor.DARK_RED + "" + ChatColor.GREEN);
        launcherCooldown.setPrefix(ChatColor.DARK_AQUA + "Launcher Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.LAUNCHER, abilitesManager));
        obj.getScore(ChatColor.DARK_RED + "" + ChatColor.GREEN).setScore(10);

        Team freezeCooldown = board.registerNewTeam("freezeCooldown");
        freezeCooldown.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD);
        freezeCooldown.setPrefix(ChatColor.DARK_AQUA + "Freeze Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.FREEZER, abilitesManager));
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD).setScore(9);

        Team damageAbility = board.registerNewTeam("damageCooldown");
        damageAbility.addEntry(ChatColor.DARK_GREEN + "" + ChatColor.BLACK);
        damageAbility.setPrefix(ChatColor.DARK_AQUA + "Damage Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.DAMAGEITEM, abilitesManager));
        obj.getScore(ChatColor.DARK_GREEN + "" + ChatColor.BLACK).setScore(8);

        Team scrambleAbility = board.registerNewTeam("scrambleCooldown");
        scrambleAbility.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.GREEN);
        scrambleAbility.setPrefix(ChatColor.DARK_AQUA + "Scramble Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.SCRAMBLE, abilitesManager));
        obj.getScore(ChatColor.DARK_GRAY + "" + ChatColor.GREEN).setScore(7);

        Team gravityAbility = board.registerNewTeam("gravityCooldown");
        gravityAbility.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.DARK_GREEN);
        gravityAbility.setPrefix(ChatColor.DARK_AQUA + "Gravity Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.GRAVITY, abilitesManager));
        obj.getScore(ChatColor.DARK_GRAY + "" + ChatColor.DARK_GREEN).setScore(6);

        Team randomTPAbility = board.registerNewTeam("randTPCooldown");
        randomTPAbility.addEntry(ChatColor.DARK_GRAY + "" + ChatColor.BLUE);
        randomTPAbility.setPrefix(ChatColor.DARK_AQUA + "RandomTP Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.RANDOMTP, abilitesManager));
        obj.getScore(ChatColor.DARK_GRAY + "" + ChatColor.BLUE).setScore(5);

        Team commandMobsAbility = board.registerNewTeam("commandCooldown");
        commandMobsAbility.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.BLUE);
        commandMobsAbility.setPrefix(ChatColor.DARK_AQUA + "Command Mobs Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.TARGETMOB, abilitesManager));
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
            if (manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(player1.getName())) {
                if (!board.getTeam("hunter").getEntries().contains(player1.getName()))
                    board.getTeam("hunter").addEntry(player1.getName());
            }
            if (manhuntGameManager.getTeam(ManhuntTeam.FROZEN).contains(player1.getName())) {
                if (!board.getTeam("frozen").getEntries().contains(player1.getName())) {
                    board.getTeam("frozen").addEntry(player1.getName());
                }
            }
            if(manhuntGameManager.getTeam(ManhuntTeam.DEAD).contains(player1.getName())) {
                if (!board.getTeam("dead").getEntries().contains(player1.getName())) {
                    board.getTeam("dead").addEntry(player1.getName());
                }
            }
            if(manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).contains(player1.getName()) && !manhuntGameManager.getTeam(ManhuntTeam.FROZEN).contains(player1.getName()) ) {
                if (!board.getTeam("speedrunner").getEntries().contains(player1.getName())) {
                    board.getTeam("speedrunner").addEntry(player1.getName());
                }
            }
        }


            int totalPlayers = manhuntGameManager.getTeam(ManhuntTeam.DEAD).size() + manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).size();

            board.getTeam("aliveSpeedrunner").setPrefix(ChatColor.AQUA + "Speedrunners >> " + ChatColor.DARK_AQUA + manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).size() + "/" + totalPlayers);
            board.getTeam("aliveHunters").setPrefix(ChatColor.AQUA + "Hunters >> " + ChatColor.DARK_AQUA + manhuntGameManager.getTeam(ManhuntTeam.HUNTER).size());
            board.getTeam("lightCooldown").setPrefix(ChatColor.DARK_AQUA + "Lightning Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.LIGHTNING, abilitesManager));
            board.getTeam("launcherCooldown").setPrefix(ChatColor.DARK_AQUA + "Launcher Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.LAUNCHER, abilitesManager));
            board.getTeam("freezeCooldown").setPrefix(ChatColor.DARK_AQUA + "Freeze Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.FREEZER, abilitesManager));
            board.getTeam("damageCooldown").setPrefix(ChatColor.DARK_AQUA + "Damage Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.DAMAGEITEM, abilitesManager));
            board.getTeam("scrambleCooldown").setPrefix(ChatColor.DARK_AQUA + "Scramble Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.SCRAMBLE, abilitesManager));
            board.getTeam("gravityCooldown").setPrefix(ChatColor.DARK_AQUA + "Gravity Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.GRAVITY, abilitesManager));
            board.getTeam("randTPCooldown").setPrefix(ChatColor.DARK_AQUA + "RandomTP Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.RANDOMTP, abilitesManager));
            board.getTeam("commandCooldown").setPrefix(ChatColor.DARK_AQUA + "Command Mobs Ability >> " + ChatColor.DARK_AQUA + manhuntGameManager.getCooldown(player, Ability.TARGETMOB, abilitesManager));
    }
}