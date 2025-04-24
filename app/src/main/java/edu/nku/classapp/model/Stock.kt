package edu.nku.classapp.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock(
    val symbol: String,
    val price: Double?,
    val company: String
)

@JsonClass(generateAdapter = true)
data class HomeStockResponse(
    val timestamp: String,
    val data: List<Stock>
)