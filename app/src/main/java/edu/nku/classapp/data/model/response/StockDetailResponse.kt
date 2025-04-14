package edu.nku.classapp.data.model.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockDetailResponse(
    @Json(name = "company_name")
    val companyName: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "currency")
    val currency: String,
    @Json(name = "current_price")
    val currentPrice: Double,
    @Json(name = "exchange")
    val exchange: String,
    @Json(name = "industry")
    val industry: String,
    @Json(name = "ipo")
    val ipo: String,
    @Json(name = "market_cap")
    val marketCap: Double,
    @Json(name = "recommendation")
    val recommendation: Recommendation,
    @Json(name = "share_outstanding")
    val shareOutstanding: Double,
    @Json(name = "symbol")
    val symbol: String,
    @Json(name = "website")
    val website: String
) {
    @JsonClass(generateAdapter = true)
    data class Recommendation(
        @Json(name = "buy")
        val buy: Int,
        @Json(name = "hold")
        val hold: Int,
        @Json(name = "sell")
        val sell: Int
    ){
        val total: Int
            get() = buy + hold + sell
        val max: Int
            get() = maxOf(buy, hold, sell)
    }
}