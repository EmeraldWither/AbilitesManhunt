package org.emeraldcraft.manhunt.shop

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.sound.Sound.sound
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType
import org.emeraldcraft.manhunt.Manhunt
import org.emeraldcraft.manhunt.entities.players.Speedrunner
import org.emeraldcraft.manhunt.enums.ManhuntTeam
import java.util.*

class ShopListener : Listener {

    @EventHandler
    fun onShopInteract(event: InventoryClickEvent) {
        val shop = Manhunt.getAPI().speedrunnerShop
        Bukkit.getLogger().info("Shop interact detected")
        if(Manhunt.getAPI().getPlayer(event.whoClicked.uniqueId) == null) {
            return
        }
        if(Manhunt.getAPI().getPlayer(event.whoClicked.uniqueId)!!.team != ManhuntTeam.SPEEDRUNNER){
            return
        }

        val item = event.currentItem
        if (item != null) {
            Bukkit.getLogger().info("Item is not null")
            if(item.itemMeta == null) return
            if(item.itemMeta.persistentDataContainer.get(Manhunt.getAPI().namespacedKey, PersistentDataType.STRING) == null) {
                return
            }
            Bukkit.getLogger().info("Item has namespace.")
            val uuid:String = item.itemMeta.persistentDataContainer.get(Manhunt.getAPI().namespacedKey, PersistentDataType.STRING)!!
            Bukkit.getLogger().info("Item has uuid: $uuid")
            if(shop.getItem(UUID.fromString(uuid)) == null) {
                return
            }
            Bukkit.getLogger().info("Item is in shop.")


            val shopItem = shop.getItem(UUID.fromString(uuid))
            val coins = shopItem!!.getPrice()
            if((Manhunt.getAPI().getPlayer(event.whoClicked.uniqueId)!! as Speedrunner).coins < coins) {
                event.whoClicked.sendMessage("You don't have enough coins.")
                event.isCancelled = true
                return
            }
            event.whoClicked.playSound(
                sound(Key.key("minecraft:entity.arrow.hit_player"), Source.MASTER, 1F, 1F)
            )
            (Manhunt.getAPI().getPlayer(event.whoClicked.uniqueId)!! as Speedrunner).removeCoins(coins)
            shopItem.onPurchase(speedrunner = Manhunt.getAPI().getPlayer(event.whoClicked.uniqueId)!! as Speedrunner)
            event.whoClicked.sendMessage("You bought ${shopItem.getName()}. You now have ${(Manhunt.getAPI().getPlayer(event.whoClicked.uniqueId)!! as Speedrunner).coins} coins!")
            event.isCancelled = true
            event.whoClicked.closeInventory()
        }

    }
}