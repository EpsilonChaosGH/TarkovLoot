package com.example.tarkovloot.app.model

data class Item(
    val name: String,
    val width: Long,
    val height: Long,
    val iconLink: String,
    var basePrice: Long,
    val bestSourcePrice: Long,
    val source: String
)