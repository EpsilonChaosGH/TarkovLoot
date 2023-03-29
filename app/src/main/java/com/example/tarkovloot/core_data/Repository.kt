package com.example.tarkovloot.core_data

import com.example.tarkovloot.app.model.Config
import com.example.tarkovloot.app.model.Item
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun refreshItems()

    suspend fun getConfigFlow(): Flow<Config>

    suspend fun saveConfig(config: Config)

    suspend fun getItemsFlow(): Flow<List<Item>>

}