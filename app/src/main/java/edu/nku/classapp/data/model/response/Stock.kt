package edu.nku.classapp.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Stock(
    val symbol: String,
    val price: Double
)
