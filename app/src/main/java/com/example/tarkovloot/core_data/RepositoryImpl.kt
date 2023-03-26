package com.example.tarkovloot.core_data

import com.example.tarkovloot.app.model.Item
import com.example.tarkovloot.core_data.mapper.toItem
import com.example.tarkovloot.core_network.base.wrapBackendExceptions
import com.example.tarkovloot.core_network.main.MainSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val mainSource: MainSource
) : Repository {

    private val list = listOf(
        "42 Signature Blend English Tea",
        "Apollo Soyuz cigarettes",
        "Aramid fiber fabric",
        "Battered antique book",
        "BEAR Buddy plush toy",
        "Pack of Arseniy buckwheat",
        "Can of Dr. Lupo's coffee beans",
        "Can of Majaica coffee beans",
        "Cordura polyamide fabric",
        "Fleece fabric",
        "FP-100 filter absorber",
        "Gas mask air filter",
//        "Gunpowder \"Eagle\"",
//        "Gunpowder \"Hawk\"",
//        "Gunpowder \"Hawk\"",
        "Loot Lord plushie",
        "Malboro Cigarettes",
        "OFZ 30x160mm shell",
        "Press pass (issued for NoiceGuy)",
        "Ripstop fabric",
        "Strike Cigarettes",
        "UZRGM grenade fuze",
        "Water filter",
        "Weapon parts",
        "Wilston cigarettes"
    )




    override suspend fun getItemList(): List<Item> =
        withContext(Dispatchers.IO){
            val favoritesDef = mutableListOf<Deferred<Item>>()
            list.map {
                val response = async {
                    return@async mainSource.getItemByName(it).toItem()
                }
                favoritesDef.add(response)
            }
            val items = favoritesDef.map { it.await() }.toMutableList()
            items.sortBy { it.basePrice }
            return@withContext items

//            list.map { mainSource.getItemByName(it).toItem() }
        }

//        Log.e("aaa", x.name)

//        return@wrapBackendExceptions listOf(mainSource.getItemByName("Majaica").toItem())


}