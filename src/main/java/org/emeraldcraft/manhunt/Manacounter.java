package org.emeraldcraft.manhunt;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.Enums.ManhuntTeam;
import org.emeraldcraft.manhunt.Managers.ManhuntGameManager;

import java.util.HashMap;
import java.util.List;

public class Manacounter{

    public int id = 0;
    private HashMap<String, Integer> Mana = new HashMap<>();

    private ManhuntGameManager manhuntGameManager;
    List<String> speedrunner;
    List<String> hunter;
    boolean HasGameStarted;
    private ManhuntMain manhuntMain;

    public Manacounter(ManhuntGameManager manhuntGameManager, ManhuntMain manhuntMain) {
        this.manhuntGameManager = manhuntGameManager;
        this.speedrunner = manhuntGameManager.getTeam(ManhuntTeam.SPEEDRUNNER);
        this.hunter = manhuntGameManager.getTeam(ManhuntTeam.HUNTER);
        this.HasGameStarted = manhuntGameManager.getGameStatus();
        this.manhuntMain = manhuntMain;
    }
    public void startMana(JavaPlugin plugin, Integer delay, Integer repeat){
        Integer manaAmount = manhuntMain.getConfig().getInt("mana-amount");
        Mana.clear();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(player.getName())){
                Mana.put(player.getName(), 0);
            }
        }
        id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if (manhuntGameManager.getTeam(ManhuntTeam.HUNTER).contains(player.getName())) {
                        if (getManaList().containsKey(player.getName())) {
                            if (Mana.get(player.getName()) < 100) {
                                Mana.put(player.getName(), (Mana.get(player.getName()) + manaAmount));
                                updateActionbar(player);
                            }
                            if (Mana.get(player.getName()) >= 100) {
                                updateActionbar(player);
                            }
                        }
                    }
                }
            }
        }, delay, repeat);
    }
    public void cancelMana(){
        Bukkit.getServer().getScheduler().cancelTasks(manhuntMain.plugin);
        id = -1;
        Mana.clear();
    }
    public HashMap<String, Integer> getManaList(){
        return Mana;
    }
    public void clearMana(){
        Mana.clear();
    }

    public void updateActionbar(Player player){
        player.sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&aYou currently have &2" + Mana.get(player.getName()) + "/100 &aMana!")));
    }


}
