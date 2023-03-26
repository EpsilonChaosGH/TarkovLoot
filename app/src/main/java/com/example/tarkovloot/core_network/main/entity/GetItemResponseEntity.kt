package com.example.tarkovloot.core_network.main.entity

import com.squareup.moshi.Json

data class GetItemResponseEntity(
    val data: Data
) {

    data class Data(
        val itemsByName: List<ItemsByName>
    )

    data class ItemsByName(
        val name: String,
        val types: List<String>,

        @Json(name = "avg24hPrice")
        val avg24HPrice: Long,

        val basePrice: Long,
        val width: Long,
        val height: Long,

        @Json(name = "changeLast48hPercent")
        val changeLast48HPercent: Double,

        val iconLink: String,
        val link: String,
        val sellFor: List<SellFor>
    )

    data class SellFor(
        val price: Long,
        val source: String
    )
}