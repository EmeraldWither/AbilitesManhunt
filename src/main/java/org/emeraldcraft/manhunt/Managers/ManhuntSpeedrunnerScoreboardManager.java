package org.emeraldcraft.manhunt.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.ManhuntMain;

import java.util.HashMap;
import java.util.UUID;

public class ManhuntSpeedrunnerScoreboardManager {

    private ManhuntGameManager manhuntGameManager;
    private ManhuntMain manhuntMain;
    public ManhuntSpeedrunnerScoreboardManager(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain){
        this.manhuntGameManager = manhuntGameManager;
        this.manhuntMain = manhuntMain;
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

        Score healthSpace = obj.getScore(ChatColor.DARK_PURPLE + "");
        healthSpace.setScore(9);

        Team healthText = board.registerNewTeam("healthText");
        healthText.addEntry(ChatColor.GRAY + "" + ChatColor.RED + "" + ChatColor.DARK_PURPLE);
        healthText.setPrefix(ChatColor.GOLD + "Your Health >> " + player.getHealth() + "/20!");
        obj.getScore(ChatColor.GRAY + "" + ChatColor.RED + "" + ChatColor.DARK_PURPLE).setScore(8);

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
                if (!board.getTeam("001hunter").getEntries().contains(player1.getName()))
                    board.getTeam("001hunter").addEntry(player1.getName());
                board.getTeam("001hunter").setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
            }
            if (manhuntGameManager.getTeam(ManhuntTeam.FROZEN).contains(player1.getName())) {
                if (!board.getTeam("003frozen").getEntries().contains(player1.getName())) {
                    board.getTeam("003frozen").addEntry(player1.getName());
                }
            }
            if(manhuntGameManager.getTeam(ManhuntTeam.DEAD).contains(player1.getName())) {
                if (!board.getTeam("004dead").getEntries().contains(player1.getName())) {
                    board.getTeam("004dead").addEntry(player1.getName());
                }
            }
            if(manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).contains(player1.getName()) && !manhuntGameManager.getTeam(ManhuntTeam.FROZEN).contains(player1.getName()) ) {
                if (!board.getTeam("002speedrunner").getEntries().contains(player1.getName())) {
                    board.getTeam("002speedrunner").addEntry(player1.getName());
                }
            }
        }


        int totalPlayers = manhuntGameManager.getTeam(ManhuntTeam.DEAD).size() + manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).size();

        board.getTeam("aliveSpeedrunner").setPrefix(ChatColor.AQUA + "Speedrunners >> " + ChatColor.DARK_AQUA + manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER).size() + "/" + totalPlayers);
        board.getTeam("aliveHunters").setPrefix(ChatColor.AQUA + "Hunters >> " + ChatColor.DARK_AQUA + manhuntGameManager.getTeam(ManhuntTeam.HUNTER).size());
        board.getTeam("locText").setPrefix(ChatColor.AQUA + "Location >>");
        board.getTeam("speedLoc").setPrefix(ChatColor.DARK_AQUA + "X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ());
        board.getTeam("healthText").setPrefix(ChatColor.AQUA + "Health >> " + ChatColor.DARK_AQUA + Math.round(player.getHealth()) + "/20");
        if(manhuntGameManager.getWaypoints().containsKey(player.getUniqueId())){
            HashMap<String, Location> waypoints= manhuntGameManager.getWaypoints().get(player.getUniqueId());
            String name = "";
            for(String s : waypoints.keySet()) {
                name = s;
                break;
            }
            Location loc = waypoints.get(name);
            if(board.getTeam("waypoint") == null || board.getTeam("waypointLoc") == null){
                if(board.getTeam("waypoint") == null) {

                    Team waypointSpace = board.registerNewTeam("waypointSpace");
                    waypointSpace.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.BLUE + "" + ChatColor.DARK_GRAY);
                    waypointSpace.setPrefix(ChatColor.GRAY + "");
                    board.getObjective("ECManhunt-Hunter").getScore(ChatColor.DARK_PURPLE + "" + ChatColor.BLUE + "" + ChatColor.DARK_GRAY).setScore(7);

                    Team waypoint = board.registerNewTeam("waypoint");
                    waypoint.addEntry(ChatColor.LIGHT_PURPLE + "" + ChatColor.BLUE + "" + ChatColor.DARK_AQUA);
                    waypoint.setPrefix(ChatColor.AQUA + "Waypoint \"" + name + "\"");
                    board.getObjective("ECManhunt-Hunter").getScore(ChatColor.LIGHT_PURPLE + "" + ChatColor.BLUE + "" + ChatColor.DARK_AQUA).setScore(6);

                    Team waypointLoc = board.registerNewTeam("waypointLoc");
                    waypointLoc.addEntry(ChatColor.GOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.GREEN);
                    waypointLoc.setPrefix(ChatColor.DARK_AQUA + "X: " + loc.getBlockX() + ", Y: " + loc.getBlockY() + ", Z: " + loc.getBlockZ());
                    board.getObjective("ECManhunt-Hunter").getScore(ChatColor.GOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.GREEN).setScore(5);
                }
                if(board.getTeam("waypointLoc") == null){

                }
            }
        }
        else {
            if(board.getTeam("waypoint") != null){
                board.resetScores(ChatColor.DARK_PURPLE + "" + ChatColor.BLUE + "" + ChatColor.DARK_GRAY);
                board.resetScores(ChatColor.LIGHT_PURPLE + "" + ChatColor.BLUE + "" + ChatColor.DARK_AQUA);
                board.resetScores(ChatColor.GOLD + "" + ChatColor.DARK_AQUA + "" + ChatColor.GREEN);
                board.getTeam("waypointSpace").unregister();
                board.getTeam("waypoint").unregister();
                board.getTeam("waypointLoc").unregister();
            }
        }
    }

}