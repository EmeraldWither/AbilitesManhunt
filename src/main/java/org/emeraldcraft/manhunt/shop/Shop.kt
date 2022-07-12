package org.emeraldcraft.manhunt.shop

import java.util.*
import javax.annotation.Nullable

class Shop(private val name: String) {
    private val items: MutableList<ShopItem> = ArrayList()
    private var shop = ShopInventory(this)
    fun getItems(): List<ShopItem> {
        return items
    }
    @Nullable
    fun getItem(uuid: UUID): ShopItem? {
        return items.find {shopItem -> shopItem.getUUID() == uuid}
    }
    fun getName(): String {
        return name
    }
    fun addItem(item: ShopItem) {
        items.add(item)
        shop = ShopInventory(this)
    }
    fun getShopInventory(): ShopInventory {
        return shop
    }
}