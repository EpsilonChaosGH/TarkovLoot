package com.example.tarkovloot.core_data.mapper

import com.example.tarkovloot.app.model.Config
import com.example.tarkovloot.app.model.Const
import com.example.tarkovloot.core_db.entity.ConfigDbEntity

fun ConfigDbEntity.toConfig() = Config(
    keyConfig = Const.KEY_CONFIG,
    priceForOneCell = priceForOneCell,
    priceWithMarket = priceWithMarket,
    sortByPriceLowToHigh = sortByPriceLowToHigh
)

fun Config.toConfigDbEntity() = ConfigDbEntity(
    keyConfig = Const.KEY_CONFIG,
    priceForOneCell = priceForOneCell,
    priceWithMarket = priceWithMarket,
    sortByPriceLowToHigh = sortByPriceLowToHigh
)