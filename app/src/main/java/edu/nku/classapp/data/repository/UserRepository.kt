package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.AuthApiResponse
import edu.nku.classapp.data.model.UserProfileApiResponse

interface UserRepository {
    suspend fun getProfile(token: String): UserProfileApiResponse
    suspend fun logout(token: String): Boolean
    suspend fun login(email: String, password: String): AuthApiResponse
    suspend fun signup(data: Map<String, String>): AuthApiResponse
}