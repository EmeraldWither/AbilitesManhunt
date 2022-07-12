package org.emeraldcraft.manhunt.shop.items.hunterdebuff;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.emeraldcraft.manhunt.Manhunt;
import org.emeraldcraft.manhunt.ManhuntMain;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.enums.ManhuntTeam;
import org.emeraldcraft.manhunt.shop.ShopItem;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class HunterDebuff extends ShopItem {
    private final HunterDebuffs debuffs = new HunterDebuffs();

    public HunterDebuff() {
        super("Remove Hunter Ability","Prevents hunter from using their abilities for 2m 30s" ,500, Material.BARRIER);
        Bukkit.getPluginManager().registerEvents(new HunterDebuffListener(debuffs), JavaPlugin.getProvidingPlugin(ManhuntMain.class));
    }

    @Override
    public void onPurchase(@NotNull Speedrunner speedrunner) {
        Hunter ranHunter = ((Hunter) Manhunt.getAPI().getTeam(ManhuntTeam.HUNTER).get(new Random().nextInt(Manhunt.getAPI().getTeam(ManhuntTeam.HUNTER).size())));
        debuffs.disableAbility(ranHunter);
        speedrunner.getAsBukkitPlayer().sendMessage("You have disabled " + ranHunter.getAsBukkitPlayer().getName() + "!");
    }
}
