package org.emeraldcraft.manhunt.shop

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.emeraldcraft.manhunt.Manhunt
import org.emeraldcraft.manhunt.entities.players.Speedrunner
import java.util.*

abstract class ShopItem( private val name: String,private val description: String, private val price: Int, material: Material) {

    private val uuid: UUID = UUID.randomUUID()
    private var shopItem:ItemStack = ItemStack(material)

    init {
        val meta = shopItem.itemMeta
        meta.persistentDataContainer.set(Manhunt.getAPI().namespacedKey!!, PersistentDataType.STRING, uuid.toString())
        val list = ArrayList<Component>()
        list.add(Component.text(description))
        list.add(Component.text("§aPrice: §f$price"))
        meta.lore(list)
        meta.displayName(Component.text(name))
        shopItem.itemMeta = meta
    }
    fun getUUID(): UUID {
        return uuid
    }
    fun getName(): String {
        return name
    }
    fun getPrice(): Int {
        return price
    }
    fun getShopItem(): ItemStack {
        return shopItem
    }
    fun getDescription(): String {
        return description
    }
    abstract fun onPurchase(speedrunner: Speedrunner)

}