package com.example.tarkovloot.core_data

import android.util.Log
import com.example.tarkovloot.app.model.Config
import com.example.tarkovloot.app.model.Const
import com.example.tarkovloot.app.model.Item
import com.example.tarkovloot.app.model.MainState
import com.example.tarkovloot.core_data.mapper.toConfig
import com.example.tarkovloot.core_data.mapper.toConfigDbEntity
import com.example.tarkovloot.core_data.mapper.toItem
import com.example.tarkovloot.core_data.mapper.toOneCellPrise
import com.example.tarkovloot.core_db.AppDatabase
import com.example.tarkovloot.core_network.base.wrapBackendExceptions
import com.example.tarkovloot.core_network.base.wrapSQLiteException
import com.example.tarkovloot.core_network.main.MainSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val mainSource: MainSource,
    private val appDatabase: AppDatabase
) : Repository {

    private val list = listOf(
        "42 Signature Blend English Tea",
        "Apollo Soyuz",
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
        "Gunpowder \\\"Eagle\\\"",
        "Gunpowder \\\"Hawk\\\"",
        "Gunpowder \\\"Kite\\\"",
        "Loot Lord plushie",
        "Malboro Cigarettes",
        "OFZ 30x160mm shell",
        "Press pass (issued for NoiceGuy)",
        "Ripstop fabric",
        "Strike Cigarettes",
        "UZRGM grenade fuze",
        "Water filter",
        "Weapon parts",
        "Wilston cigarettes",
        "Analog thermometer",
        "Bolts",
        "Corrugated hose",
        "Duct tape",
        "Insulating tape",
        "KEKTAPE duct tape",
        "Metal spare parts",
        "Military corrugated tube",
        "Pack of nails",
        "Pack of screws",
        "Piece of plexiglass",
        "Pressure gauge",
        "Screw nuts",
        "Shustrilo sealing foam",
        "Silicone tube",
        "Tube of Poxeram cold welding",
        "Xenomorph sealing foam",
        "Broken GPhone X smartphone",
        "Broken GPhone smartphone",
        "Broken LCD",
        "Bundle of wires",
        "Capacitors",
        "CPU fan",
        "Damaged hard drive",
        "DVD drive",
        "Electric drill",
        "Electric motor",
        "Electronic components",
        "Energy-saving lamp",
        "Far-forward current converter",
        "Far-forward GPS Signal Amplifier Unit",
        "Gas analyzer",
        "Geiger-Muller counter",
        "Golden 1GPhone smartphone",
        "Graphics card",
        "Iridium military thermal vision module",
        "Light bulb",
        "Magnet",
        "Microcontroller board",
        "Military cable",
        "Military circuit board",
        "Military COFDM Wireless Signal Transmitter",
        "Military gyrotachometer",
        "Military power filter",
        "NIXXOR lens",
        "PC CPU",
        "Phase control relay",
        "Phased array element",
        "Power cord",
        "Power supply unit",
        "Printed circuit board",
        "Radiator helix",
        "RAM",
        "Spark plug",
        "T-Shaped plug",
        "Tetriz portable game console",
        "UHF RFID Reader",
        "Ultraviolet lamp",
        "USB Adapter",
        "Virtex programmable processor",
        "VPX Flash Storage Module",
        "Working LCD",
        "6-STEN-140-M military battery",
        "AA Battery",
        "Car battery",
        "Cyclon rechargeable battery",
        "D Size battery",
        "GreenBat lithium battery",
        "Portable Powerbank",
        "Rechargeable battery",
        "Can of thermite",
        "Classic matches",
        "Crickent lighter",
        "Dry fuel",
        "Expeditionary fuel tank",
        "FireKlean gun lube",
        "Fuel conditioner",
        "Hunting matches",
        "Metal fuel tank",
        "Propane tank (5L)",
        "SurvL Survivor Lighter",
        "TP-200 TNT brick",
        "WD-40 (400ml)",
        "WD-40 (100ml)",
        "Zibbo lighter",
        "Alkaline cleaner for heat exchangers",
        "Can of white salt",
        "Clin window cleaner",
        "Deadlyslob's beard oil",
        "LVNDMARK's rat poison",
        "Ortodontox toothpaste",
        "Ox bleach",
        "Pack of chlorine",
        "Pack of sodium bicarbonate",
        "PAID AntiRoach spray",
        "Printer paper",
        "Repellent",
        "Schaman shampoo",
        "Smoked Chimney drain cleaner",
        "Soap",
        "Toilet paper",
        "Toothpaste",
        "Aquapeps water purification tablets",
        "Bottle of hydrogen peroxide",
        "Bottle of OLOLO Multivitamins",
        "Bottle of saline solution",
        "Disposable syringe",
        "LEDX Skin Transilluminator",
        "Medical bloodset",
        "Medical tools",
        "Ophthalmoscope",
        "Pile of meds",
        "Portable defibrillator",
        "Awl",
        "Bulbex cable cutter",
        "Construction measuring tape",
        "Fierce Blow sledgehammer",
        "Flat screwdriver",
        "Flat screwdriver (Long)",
        "Hand drill",
        "Metal cutting scissors",
        "Nippers",
        "Old firesteel",
        "Pipe grip wrench",
        "Pliers",
        "Pliers Elite",
        "Ratchet wrench",
        "Round pliers",
        "Screwdriver",
        "Set of files \\\"Master\\\"",
        "Sewing kit",
        "Toolset",
        "Wrench",
        "Antique teapot",
        "Antique vase",
        "Axel parrot figurine",
        "Bronze lion",
        "Cat figurine",
        "Chain with Prokill medallion",
        "Chainlet",
        "Christmas tree ornament (Red)",
        "Christmas tree ornament (Silver)",
        "Christmas tree ornament (Violet)",
        "Gold skull ring",
        "Golden egg",
        "Golden neck chain",
        "Golden rooster",
        "GP coin",
        "Horse figurine",
        "Physical bitcoin",
        "Raven figurine",
        "Roler Submariner gold wrist watch",
        "Silver Badge",
        "Veritas guitar pick",
        "Wooden clock",
        "Advanced Electronic Materials textbook",
        "BakeEzy cook book",
        "Diary",
        "Intelligence folder",
        "Military flash drive",
        "SAS drive",
        "Secure Flash drive",
        "Secured magnetic tape cassette",
        "Silicon Optoelectronic Integrated Circuits textbook",
        "Slim diary",
        "SSD drive",
        "Tech manual",
        "TerraGroup \\\"Blue Folders\\\" materials",
        "Topographic survey maps",
        "Video cassette with the Cyborg Killer movie",
        "Body armor repair kit",
        "Digital secure DSP radio transmitter",
        "EYE MK.2 professional hand-held compass",
        "Leatherman Multitool",
        "MS2000 Marker",
        "Radio repeater",
        "Signal Jammer",
        "Vortex Ranger 1500 rangefinder",
        "WI-FI Camera",
        "Weapon repair kit",
    )
    private var currentItems: List<Item> = listOf()

    override suspend fun refreshItems() {
        currentItems = getItemList()
    }

    override suspend fun getConfigFlow(): Flow<Config> = wrapSQLiteException(Dispatchers.IO) {
        appDatabase.configDao().getConfigFlow(Const.KEY_CONFIG).map { it.toConfig() }
    }

    override suspend fun saveConfig(config: Config) = wrapSQLiteException(Dispatchers.IO) {
        appDatabase.configDao().insertConfig(config.toConfigDbEntity())
    }

    override suspend fun getItemsFlow(): Flow<List<Item>> = wrapSQLiteException(Dispatchers.IO) {
        appDatabase.configDao().getConfigFlow(Const.KEY_CONFIG).map { config ->

            val items = currentItems.ifEmpty { getItemList() }.toMutableList()

            val sortedItems = if (config.priceForOneCell) {
                items.map { it.toOneCellPrise() }.toMutableList()
            } else {
                items
            }

            if (config.sortByPriceLowToHigh) sortedItems.sortBy { it.basePrice }
            else {
                sortedItems.sortByDescending { it.basePrice }
            }
            return@map sortedItems
        }
    }

    private suspend fun getItemList(): List<Item> =
        withContext(Dispatchers.IO) {
            val favoritesDef = mutableListOf<Deferred<Item>>()
            list.map {
                val response = async {
                    return@async mainSource.getItemByName(it).toItem()
                }
                favoritesDef.add(response)
            }
            val items = favoritesDef.map { it.await() }.toMutableList()
            currentItems = items
            return@withContext items
        }
}