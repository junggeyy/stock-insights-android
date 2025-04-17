package edu.nku.classapp.data.model.response

import com.squareup.moshi.JsonClass
import edu.nku.classapp.data.model.response.Stock

@JsonClass(generateAdapter = true)
data class UserProfileResponse(
    val avatar: String?,
    val name: String,
    val portfolio: List<Any>,
    val watchlist: List<Any>,
    val username: String,
    val email: String
)
