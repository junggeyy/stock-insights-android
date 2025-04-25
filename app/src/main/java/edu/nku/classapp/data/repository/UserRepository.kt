package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.AuthApiResponse
import edu.nku.classapp.data.model.UserProfileApiResponse
import edu.nku.classapp.data.model.WatchlistApiResponse
import edu.nku.classapp.data.model.WatchlistEditApiResponse
import edu.nku.classapp.data.model.WatchlistCheckApiResponse

interface UserRepository {
    suspend fun getProfile(token: String): UserProfileApiResponse
    suspend fun logout(token: String): Boolean
    suspend fun login(email: String, password: String): AuthApiResponse
    suspend fun signup(data: Map<String, String>): AuthApiResponse
    suspend fun getWatchlist(token: String): WatchlistApiResponse
    suspend fun addToWatchlist(token: String, body: Map<String, String>): WatchlistEditApiResponse
    suspend fun removeFromWatchlist(token: String, body: Map<String, String>): WatchlistEditApiResponse
    suspend fun isInWatchlist(token: String, symbol: String): WatchlistCheckApiResponse
}