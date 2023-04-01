package com.example.tarkovloot.core_data.mapper

import com.example.tarkovloot.app.model.Item
import com.example.tarkovloot.app.model.ItemSellFor
import com.example.tarkovloot.core_network.main.entity.GetItemResponseEntity

fun GetItemResponseEntity.toItem(): Item {
    val sellFor =
        getMaxPrice(data.itemsByName.firstOrNull()?.sellFor?.map { it.toItemSellFor() } ?: listOf(
            ItemSellFor(0, "...")
        ))
    return Item(
        name = data.itemsByName.firstOrNull()?.name ?: "",
        width = data.itemsByName.firstOrNull()?.width ?: 0,
        height = data.itemsByName.firstOrNull()?.height ?: 0,
        iconLink = data.itemsByName.firstOrNull()?.iconLink ?: "",
        basePrice = data.itemsByName.firstOrNull()?.basePrice ?: 0,
        bestSourcePrice = sellFor.price,
        source = sellFor.source
    )
}

fun GetItemResponseEntity.SellFor.toItemSellFor() = ItemSellFor(
    price = price,
    source = source
)

fun Item.toOneCellPrise() = Item(
    name = name,
    width = width,
    height = height,
    iconLink = iconLink,
    basePrice = (basePrice / (width * height)),
    bestSourcePrice = bestSourcePrice,
    source = source
)

fun Item.toPriceWithMarket() = Item(
    name = name,
    width = width,
    height = height,
    iconLink = iconLink,
    basePrice = bestSourcePrice,
    bestSourcePrice = bestSourcePrice,
    source = "fleaMarket"
)

fun getMaxPrice(sellFor: List<ItemSellFor>): ItemSellFor {
    val x = sellFor.map {
        if (it.source != "fleaMarket") {
            ItemSellFor(it.price, it.source)
        } else {
            ItemSellFor(0, "...")
        }
    }
    return x.maxBy { it.price }
}
