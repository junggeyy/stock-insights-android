package edu.nku.classapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandleChartResponse(
    val symbol: String,
    val candles: List<Candle>
)
