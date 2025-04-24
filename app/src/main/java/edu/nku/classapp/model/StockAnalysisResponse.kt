package edu.nku.classapp.model
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockAnalysisResponse(
    val symbol: String,
    val forecast: List<ForecastPoint>,
    val recommendation: String,
    val message: String,
    @Json(name = "current_price")
    val currentPrice: Double,
    @Json(name = "predicted_price")
    val predictedPrice: Double,
    @Json(name = "change_percent")
    val changePercent: Double
)

@JsonClass(generateAdapter = true)
data class ForecastPoint(
    val ds: String,
    val yhat: Double,
    @Json(name = "yhat_lower")
    val yhatLower: Double,
    @Json(name = "yhat_upper")
    val yhatUpper: Double
)
