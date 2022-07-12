package org.emeraldcraft.manhunt.shop

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

class ShopInventory(private val shop: Shop) {
    private val inventory: Inventory = Bukkit.createInventory(null, 9 * 6, "Shop")
    init{
        for (i in 0 until shop.getItems().size) {
            inventory.addItem(shop.getItems()[i].getShopItem())
        }

    }
    fun getInventory(): Inventory {
        return inventory
    }
}