package org.emeraldcraft.manhunt.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import org.emeraldcraft.manhunt.Abilties.AbilitesManager;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;

import java.util.UUID;

public class ManhuntSpeedrunnerScoreboardManager {

    private ManhuntGameManager manhuntGameManager;
    private AbilitesManager abilitesManager;

    public ManhuntSpeedrunnerScoreboardManager(ManhuntGameManager manhuntGameManager, AbilitesManager abilitesManager){
        this.manhuntGameManager = manhuntGameManager;
        this.abilitesManager = abilitesManager;
    }
    public int id = 0;
    public void showSpeedrunnerScoreboard(UUID uuid, Plugin plugin){
        setSpeedrunnerScoreboard(uuid);
        id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateSpeedrunnerScoreBoard(uuid);
            }
        }, 0, 1L);
    }

    public void setSpeedrunnerScoreboard(UUID uuid){
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


        Team emptySpace = board.registerNewTeam("emptySpace");
        emptySpace.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD + "" + ChatColor.BLACK);
        emptySpace.setPrefix(ChatColor.GRAY + "");
        obj.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.GOLD + "" + ChatColor.BLACK).setScore(12);


        Team locText = board.registerNewTeam("locText");
        locText.addEntry(ChatColor.LIGHT_PURPLE + "" + ChatColor.GOLD + "" + ChatColor.DARK_PURPLE);
        locText.setPrefix(ChatColor.GOLD + "Your Location");
        obj.getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.GOLD + "" + ChatColor.DARK_PURPLE).setScore(11);

        Team speedrunnerloc = board.registerNewTeam("speedLoc");
        speedrunnerloc.addEntry(ChatColor.RED + "" + ChatColor.DARK_GRAY + "" + ChatColor.BLACK);
        speedrunnerloc.setPrefix(ChatColor.DARK_AQUA + "X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ());
        obj.getScore(ChatColor.RED + "" + ChatColor.DARK_GRAY + "" + ChatColor.BLACK).setScore(10);


        Score creditspace = obj.getScore(ChatColor.RED + "");
        creditspace.setScore(2);

        Team credit = board.registerNewTeam("credit");
        credit.addEntry(ChatColor.RED + "" + ChatColor.GOLD + "" + ChatColor.BLACK);
        credit.setPrefix(ChatColor.GRAY + "Made by EmeraldWither");
        obj.getScore(ChatColor.RED + "" + ChatColor.GOLD + "" + ChatColor.BLACK).setScore(1);


        assert player != null;
        player.setScoreboard(board);
    }

    public void updateSpeedrunnerScoreBoard(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        assert player != null;
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
        board.getTeam("locText").setPrefix(ChatColor.GREEN + "Your Location");
        board.getTeam("speedLoc").setPrefix(ChatColor.DARK_AQUA + "X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ());
    }

}
