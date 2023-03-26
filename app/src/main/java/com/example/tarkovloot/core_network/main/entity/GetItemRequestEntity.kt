package com.example.tarkovloot.core_network.main.entity

import com.squareup.moshi.Json

data class GetItemRequestEntity(private val item: String) {
    @field:Json(name = "query")
    val queryString = """
{
  itemsByName(name: "$item") {
    name
    types
    avg24hPrice
    basePrice
    width
    height
    changeLast48hPercent
    iconLink
    link
    sellFor {
      price
      source
    }
  }
}
        """.trimIndent()
}