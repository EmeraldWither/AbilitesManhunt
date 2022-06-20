package org.emeraldcraft.manhunt.abilites;

import static org.bukkit.Material.AIR;
import static org.emeraldcraft.manhunt.Manhunt.getAPI;
import static org.emeraldcraft.manhunt.utils.IManhuntUtils.debug;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.emeraldcraft.manhunt.entities.ManhuntAbility;
import org.emeraldcraft.manhunt.entities.players.Hunter;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.utils.IManhuntUtils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class ItemDeleterAbility extends ManhuntAbility {
    private final boolean includesAir;

    public ItemDeleterAbility() {
        super("Item Deleter",
                "Deletes 1 random item.",
                getAPI().getConfig().getFileConfig().getInt("ability.itemdeleter.cooldown"),
                getAPI().getConfig().getFileConfig().getInt("ability.itemdeleter.mana"),
                Material.getMaterial(getAPI().getConfig().getFileConfig().getString("ability.itemdeleter.material")));
        includesAir = getAPI().getConfig().getFileConfig().getBoolean("ability.itemdeleter.include-air");
    }

    @Override
    protected void onExecute(Hunter hunter, Speedrunner speedrunner) {
        if (speedrunner.getAsBukkitPlayer() == null) return;
        Player player = speedrunner.getAsBukkitPlayer();
        ItemStack[] items = player.getInventory().getStorageContents();
        debug("Items: " + items.length);
        ItemStack item;
        int itemSlot;
        if (includesAir) {
            itemSlot = new Random().nextInt(items.length);
            item = items[itemSlot];
        }
        else{
            do {
                itemSlot = new Random().nextInt(items.length);
                item = items[itemSlot];
            } while (item == null || item.getType() == AIR);
        }
        player.getInventory().setItem(itemSlot, new ItemStack(AIR));
        if(item == null) item = new ItemStack(AIR);
        //Minimessage start parsing config
        String msgStr = getAPI().getConfig().getFileConfig().getString("ability.itemdeleter.msg");
        if (msgStr == null) return;
        Component msg = MiniMessage.miniMessage().deserialize(
                IManhuntUtils.parseBasicMessage(msgStr
                                .replaceAll("%item-amount%", item.getAmount() + "")
                                .replaceAll("%item-type%", item.getType().toString()),
                        this,
                        speedrunner,
                        hunter)
        );
        player.sendMessage(msg);
    }
}