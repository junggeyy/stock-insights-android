package edu.nku.classapp.data.model

data class LoginResponse (
    val token: String,
    val user: User
)