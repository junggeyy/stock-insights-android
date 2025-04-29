package edu.nku.classapp.data.repository

import edu.nku.classapp.data.UserApi
import edu.nku.classapp.data.model.AuthApiResponse
import edu.nku.classapp.data.model.UserProfileApiResponse
import edu.nku.classapp.data.model.WatchlistApiResponse
import edu.nku.classapp.data.model.WatchlistEditApiResponse
import edu.nku.classapp.data.model.WatchlistCheckApiResponse
import javax.inject.Inject

class UserRepositoryReal @Inject constructor(
    private val userApi: UserApi
) : UserRepository{

    override suspend fun login(email: String, password: String): AuthApiResponse {
        return try {
            val response = userApi.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful && response.body() != null) {
                AuthApiResponse.Success(response.body()!!)
            } else {
                AuthApiResponse.Error("Login failed: ${response.code()}")
            }
        } catch (e: Exception) {
            AuthApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun signup(data: Map<String, String>): AuthApiResponse {
        return try {
            val response = userApi.signup(data)
            if (response.isSuccessful && response.body() != null) {
                AuthApiResponse.Success(response.body()!!)
            } else {
                AuthApiResponse.Error("Signup failed: ${response.code()}")
            }
        } catch (e: Exception) {
            AuthApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getProfile(token: String): UserProfileApiResponse {
        return try {
            val response = userApi.getProfile("Token $token")
            if (response.isSuccessful && response.body() != null) {
                UserProfileApiResponse.Success(response.body()!!)
            } else {
                UserProfileApiResponse.Error("Failed to fetch: ${response.code()}")
            }
        } catch (e: Exception) {
            UserProfileApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun getWatchlist(token: String): WatchlistApiResponse {
        return try {
            val response = userApi.getWatchlist(token)
            if (response.isSuccessful && response.body() != null) {
                WatchlistApiResponse.Success(response.body()!!)
            } else {
                WatchlistApiResponse.Error("Failed to fetch: ${response.code()}")
            }
        } catch (e: Exception) {
            WatchlistApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun addToWatchlist(
        token: String,
        body: Map<String, String>
    ): WatchlistEditApiResponse {
        return try{
            val response = userApi.addToWatchlist(token, body)
            if(response.isSuccessful && response.body() != null){
                WatchlistEditApiResponse.Success(response.body()!!)
            } else{
                WatchlistEditApiResponse.Error("Failed to add: ${response.code()}")
            }
        }catch (e: Exception){
            WatchlistEditApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun removeFromWatchlist(
        token: String,
        body: Map<String, String>
    ): WatchlistEditApiResponse {
        return try{
            val response = userApi.removeFromWatchlist(token, body)
            if(response.isSuccessful && response.body() != null){
                WatchlistEditApiResponse.Success(response.body()!!)
            } else{
                WatchlistEditApiResponse.Error("Failed to add: ${response.code()}")
            }
        }catch (e: Exception){
            WatchlistEditApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun isInWatchlist(
        token: String, symbol: String
    ): WatchlistCheckApiResponse {
        return try{
            val response = userApi.isInWatchlist(token, symbol)
            if(response.isSuccessful && response.body() != null){
                WatchlistCheckApiResponse.Success(response.body()!!)
            } else{
                WatchlistCheckApiResponse.Error("Failed to check: ${response.code()}")
            }
        } catch (e: Exception){
            WatchlistCheckApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun logout(token: String): Boolean {
        return try {
            userApi.logout("Token $token")
            true
        } catch (e: Exception) {
            false
        }
    }
}