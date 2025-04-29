package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.UserApiResponse

interface UserRepository {
    suspend fun getProfile(token: String): UserApiResponse
    suspend fun logout(token: String): Boolean
    suspend fun login(email: String, password: String): UserApiResponse
    suspend fun signup(data: Map<String, String>): UserApiResponse
    suspend fun getWatchlist(token: String): UserApiResponse
    suspend fun addToWatchlist(token: String, body: Map<String, String>): UserApiResponse
    suspend fun removeFromWatchlist(token: String, body: Map<String, String>): UserApiResponse
    suspend fun isInWatchlist(token: String, symbol: String): UserApiResponse
}