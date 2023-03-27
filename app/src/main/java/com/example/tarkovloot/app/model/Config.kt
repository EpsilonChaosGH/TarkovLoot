package com.example.tarkovloot.app.model


data class Config(
    val keyConfig: String,
    val priceForOneCell: Boolean,
    val priceWithMarket: Boolean,
    val sortByPriceLowToHigh: Boolean,
)