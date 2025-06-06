package edu.nku.classapp.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockSearchResponse(
    override val description: String,
    override val symbol: String
): StockList