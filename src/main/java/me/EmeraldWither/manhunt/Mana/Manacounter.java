package me.EmeraldWither.manhunt.Mana;

import me.EmeraldWither.manhunt.Enums.Team;
import me.EmeraldWither.manhunt.Main;
import me.EmeraldWither.manhunt.ManhuntGameManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public class Manacounter{

    public int id = 0;
    private HashMap<String, Integer> Mana = new HashMap<>();

    private ManhuntGameManager manhuntGameManager;
    List<String> speedrunner;
    List<String> hunter;
    boolean HasGameStarted;
    private Main main;

    public Manacounter(ManhuntGameManager manhuntGameManager, Main main) {
        this.manhuntGameManager = manhuntGameManager;
        this.speedrunner = manhuntGameManager.getTeam(Team.SPEEDRUNNER);
        this.hunter = manhuntGameManager.getTeam(Team.HUNTER);
        this.HasGameStarted = manhuntGameManager.getGameStatus();
        this.main = main;
    }
    public void startMana(JavaPlugin plugin, Integer delay, Integer repeat){
        Integer manaAmount = main.getConfig().getInt("mana-amount");
        Mana.clear();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (manhuntGameManager.getTeam(Team.HUNTER).contains(player.getName())){
                Mana.put(player.getName(), 0);
            }
        }
        id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if (manhuntGameManager.getTeam(Team.HUNTER).contains(player.getName())) {
                        if (getManaList().containsKey(player.getName())) {
                            if (Mana.get(player.getName()) < 100) {
                                Mana.put(player.getName(), (Mana.get(player.getName()) + manaAmount));
                                updateActionbar(player);
                            }
                            if (Mana.get(player.getName()) == 100) {
                                updateActionbar(player);
                            }
                        }
                    }
                }
            }
        }, delay, repeat);
    }
    public void cancelMana(){
        Bukkit.getServer().getScheduler().cancelTasks(main.plugin);
        id = -1;
        Mana.clear();
    }
    public HashMap<String, Integer> getManaList(){
        return Mana;
    }

    public void updateActionbar(Player player){
        player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&aYou currently have &2" + Mana.get(player.getName()) + "/100 &aMana!")));
    }


}
