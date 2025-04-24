package edu.nku.classapp.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockSearchResponse(
    val description: String,
    val symbol: String
)