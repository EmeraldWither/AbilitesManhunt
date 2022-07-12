package org.emeraldcraft.manhunt.shop.speedrunneritems.hunterdebuff;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.emeraldcraft.manhunt.events.ManhuntGameEndEvent;
import org.emeraldcraft.manhunt.events.hunter.HunterClickAbilityEvent;

public class HunterDebuffListener implements Listener {
    private final HunterDebuffs debuffs;

    public HunterDebuffListener(HunterDebuffs debuffs) {
        this.debuffs = debuffs;
    }
    @EventHandler
    public void onPreHunterExecuteAbilityEvent(HunterClickAbilityEvent event) {
        if(debuffs.isDisabled(event.getHunter())){
            event.getHunter().getAsBukkitPlayer().sendMessage("You are disabled!");
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEnd(ManhuntGameEndEvent event){
        debuffs.clear();
    }
}
