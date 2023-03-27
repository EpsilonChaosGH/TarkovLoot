package com.example.tarkovloot.core_db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class ConfigDbEntity(
    @PrimaryKey @ColumnInfo(name = "key_config") val keyConfig: String,
    @ColumnInfo(name = "price_for_one_cell") val priceForOneCell: Boolean,
    @ColumnInfo(name = "price_with_market") val priceWithMarket: Boolean,
    @ColumnInfo(name = "sort_by_price_low_to_high") val sortByPriceLowToHigh: Boolean,
)