package com.example.tarkovloot.core_network.main

import com.example.tarkovloot.core_network.main.entity.GetItemResponseEntity

interface MainSource {

    suspend fun getItemByName(itemName: String): GetItemResponseEntity

}