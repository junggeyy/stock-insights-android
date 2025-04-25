package edu.nku.classapp.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WatchlistResponse (
    @Json(name = "name")
    override val description: String,
    @Json(name = "ticker")
    override val symbol: String
): StockList