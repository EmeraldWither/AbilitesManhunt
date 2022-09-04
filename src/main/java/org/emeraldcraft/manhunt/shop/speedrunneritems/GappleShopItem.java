package org.emeraldcraft.manhunt.shop.speedrunneritems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.emeraldcraft.manhunt.entities.players.Speedrunner;
import org.emeraldcraft.manhunt.shop.ShopItem;
import org.emeraldcraft.manhunt.utils.ManhuntUtils;
import org.jetbrains.annotations.NotNull;

public class GappleShopItem extends ShopItem {
    public GappleShopItem() {
        super("5x Golden Apple","Gives you 5 golden apples!" ,200, Material.GOLDEN_APPLE);
    }
    @Override
    public void onPurchase(@NotNull Speedrunner speedrunner) {
        ManhuntUtils.addOrDropItem(speedrunner.getAsBukkitPlayer(), new ItemStack(Material.GOLDEN_APPLE, 5));
    }
}
