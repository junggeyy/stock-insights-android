package edu.nku.classapp.model
import com.google.gson.annotations.SerializedName

data class WatchListCheckResponse(
    @SerializedName("is_in_watchlist")
    val isInWatchlist: Boolean
)

