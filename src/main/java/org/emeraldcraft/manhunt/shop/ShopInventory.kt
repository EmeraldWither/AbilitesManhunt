package org.emeraldcraft.manhunt.shop

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

class ShopInventory(shop: Shop) {
    private val inventory: Inventory = Bukkit.createInventory(null, 9 * 6, MiniMessage.miniMessage().deserialize("<b><aqua><i>${shop.getName()}</i></aqua></b>"))
    init{
        for (i in 0 until shop.getItems().size) {
            inventory.addItem(shop.getItems()[i].getShopItem())
        }

    }
    fun getInventory(): Inventory {
        return inventory
    }
}