package org.emeraldcraft.manhunt.shop.speedrunneritems;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.shop.ShopItem;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;
import org.jetbrains.annotations.NotNull;

public class UndyingShopItem extends ShopItem {
    public UndyingShopItem() {
        super("Totem of Undying","Grants you a totem of undying!" ,500, Material.TOTEM_OF_UNDYING);
    }

    @Override
    public void onPurchase(@NotNull Speedrunner speedrunner) {
        Player player = speedrunner.getAsBukkitPlayer();
        if(player == null) return;
        ManhuntUtils.addOrDropItem(player, new ItemStack(Material.TOTEM_OF_UNDYING));
    }
}
