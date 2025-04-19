package edu.nku.classapp.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StockSearchResponse(
    val description: String,
    val symbol: String
)