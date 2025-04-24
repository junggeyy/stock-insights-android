package edu.nku.classapp.model
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileResponse(
    val avatar: String?,
    val name: String,
    val portfolio: List<Any>,
    val watchlist: List<Any>,
    val username: String,
    val email: String
)
