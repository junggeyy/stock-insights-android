package edu.nku.classapp.data.model

import com.squareup.moshi.JsonClass
import edu.nku.classapp.model.Stock

@JsonClass(generateAdapter = true)
data class UserProfileResponse(
    val avatar: String?,
    val name: String,
    val portfolio: List<Stock>,
    val watchlist: List<Stock>,
    val username: String,
    val email: String
)
