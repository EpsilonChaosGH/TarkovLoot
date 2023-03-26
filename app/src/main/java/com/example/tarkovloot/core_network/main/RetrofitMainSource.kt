package com.example.tarkovloot.core_network.main

import com.example.tarkovloot.core_network.base.BaseRetrofitSource
import com.example.tarkovloot.core_network.base.RetrofitConfig
import com.example.tarkovloot.core_network.main.entity.GetItemRequestEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitMainSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), MainSource {

    private val mainApi = retrofit.create(MainApi::class.java)

    override suspend fun getItemByName(itemName: String) = wrapRetrofitExceptions {
        return@wrapRetrofitExceptions mainApi.getItemByName(
            GetItemRequestEntity(itemName)
        )
    }
}