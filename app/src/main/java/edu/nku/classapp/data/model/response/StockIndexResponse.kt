package edu.nku.classapp.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockIndexResponse(
    @Json(name = "market_status")
    val marketStatus: Boolean,
    @Json(name = "indices_data")
    val indices: Map<String, IndexData>
)

@JsonClass(generateAdapter = true)
data class IndexData(
    @Json(name = "price")
    val price: Double,
    @Json(name = "change_percent")
    val changePercent: Double
)
