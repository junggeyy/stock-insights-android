package edu.nku.classapp.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CandleChartResponse(
    val symbol: String,
    val candles: List<Candle>
)
