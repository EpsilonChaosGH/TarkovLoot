package com.example.tarkovloot.core_db.dao

import androidx.room.*
import com.example.tarkovloot.core_db.entity.ConfigDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {

    @Query("SELECT * FROM config WHERE key_config = :keyParams")
    fun getConfigFlow(keyParams: String): Flow<ConfigDbEntity>

    @Query("SELECT * FROM config WHERE key_config = :keyParams")
    fun getConfig(keyParams: String): ConfigDbEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfig(config: ConfigDbEntity)

}