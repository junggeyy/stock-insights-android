package edu.nku.classapp.data.model.response

data class Candle(
    val date: String,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float,
    val volume: Float
)
