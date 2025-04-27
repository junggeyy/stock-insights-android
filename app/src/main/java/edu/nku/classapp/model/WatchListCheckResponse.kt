package edu.nku.classapp.model
import com.squareup.moshi.Json

data class WatchListCheckResponse(
    @Json(name="is_in_watchlist")
    val isInWatchlist: Boolean
)

