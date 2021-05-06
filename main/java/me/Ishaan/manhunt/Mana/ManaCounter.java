package me.Ishaan.manhunt.Mana;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class ManaCounter extends BukkitRunnable {

    HashMap<String, Integer> Mana = new HashMap<String, Integer>();
    List<String> hunter = HunterList.hunters;
    Server server = Bukkit.getServer();

    @Override
    public void run() {
        for (String hunter : hunter) {
            if (Mana.containsKey(hunter)) {
                Mana.put(hunter, Mana.get(hunter) + 5);
                Bukkit.broadcastMessage("Your mana is now " + Mana.get(hunter));
            } else {
                Mana.put(hunter, 0);
            }
        }
    }

}
