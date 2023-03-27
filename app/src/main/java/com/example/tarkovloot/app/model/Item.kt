package com.example.tarkovloot.app.model

data class Item(
    val name: String,
    var basePrice: Long,
    val width: Long,
    val height: Long,
    val iconLink: String,
    //val sellFor: List<SellFor>
) {

    data class SellFor(
        val price: Long,
        val source: String
    )
}