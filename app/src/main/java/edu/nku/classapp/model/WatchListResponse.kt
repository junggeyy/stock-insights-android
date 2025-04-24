package edu.nku.classapp.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WatchListResponse (
    val detail: String
)