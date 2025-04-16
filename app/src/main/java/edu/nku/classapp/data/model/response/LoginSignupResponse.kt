package edu.nku.classapp.data.model.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginSignupResponse(
    val token: String,
    val user: User
)
