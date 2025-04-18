package edu.nku.classapp.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock(
    val symbol: String,
    val price: Double?,
    val company: String
)

data class HomeStockResponse(
    val timestamp: String,
    val data: List<Stock>
)