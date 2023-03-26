package com.example.tarkovloot.core_data.mapper

import com.example.tarkovloot.app.model.Item
import com.example.tarkovloot.core_network.main.entity.GetItemResponseEntity

fun GetItemResponseEntity.toItem() = Item(
    name = data.itemsByName.firstOrNull()?.name ?: "",
    basePrice = data.itemsByName.firstOrNull()?.basePrice ?: 0,
    width = data.itemsByName.firstOrNull()?.width ?: 0,
    height = data.itemsByName.firstOrNull()?.height ?: 0,
    iconLink = data.itemsByName.firstOrNull()?.iconLink ?: "",
)