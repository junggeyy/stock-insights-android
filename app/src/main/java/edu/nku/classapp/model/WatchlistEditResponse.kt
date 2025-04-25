package edu.nku.classapp.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WatchlistEditResponse(
    val detail: String
)
