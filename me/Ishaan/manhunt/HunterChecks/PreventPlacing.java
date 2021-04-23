package me.Ishaan.manhunt.HunterChecks;

import me.Ishaan.manhunt.PlayerLists.HunterList;
import me.Ishaan.manhunt.PlayerLists.SpeedrunList;
import net.minecraft.server.v1_16_R3.SoundEffectType;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public class PreventPlacing implements Listener {

    List<String> speedrunner = SpeedrunList.speedrunners;

    //Hunters
    List<String> hunter = HunterList.hunters;


    @EventHandler
    public void PlayerPlace (BlockPlaceEvent event){
        if(hunter.contains(event.getPlayer().getName())){

            event.setBuild(false);
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void PlayerBreak (BlockBreakEvent event){
        if(hunter.contains(event.getPlayer().getName())){
            event.setCancelled(true);
            event.setDropItems(false);
            event.setExpToDrop(0);

        }
    }



}
