package edu.nku.classapp.data.model

import com.squareup.moshi.Json

data class User(
    val id: Int,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    val username: String,
    val email: String?,
    val password: String?
)
