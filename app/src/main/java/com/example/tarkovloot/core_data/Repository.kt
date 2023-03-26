package com.example.tarkovloot.core_data

import com.example.tarkovloot.app.model.Item

interface Repository {

    suspend fun getItemList(): List<Item>

}