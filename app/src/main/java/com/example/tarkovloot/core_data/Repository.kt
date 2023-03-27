package com.example.tarkovloot.core_data

import com.example.tarkovloot.app.model.Config
import com.example.tarkovloot.app.model.MainState
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun saveConfig(config: Config)

    suspend fun getItemsFlow(): Flow<MainState>

}