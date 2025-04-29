package edu.nku.classapp.data.repository

import edu.nku.classapp.data.UserApi
import edu.nku.classapp.data.model.UserApiResponse
import javax.inject.Inject

class UserRepositoryReal @Inject constructor(
    private val userApi: UserApi
) : UserRepository{

    override suspend fun login(email: String, password: String): UserApiResponse {
        return try {
            val response = userApi.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful && response.body() != null) {
                UserApiResponse .UserAuthSuccess(response.body()!!)
            } else {
                UserApiResponse.Error("Login failed: ${response.code()}")
            }
        } catch (e: Exception) {
            UserApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun signup(data: Map<String, String>): UserApiResponse  {
        return try {
            val response = userApi.signup(data)
            if (response.isSuccessful && response.body() != null) {
                UserApiResponse.UserAuthSuccess(response.body()!!)
            } else {
                UserApiResponse.Error("Signup failed: ${response.code()}")
            }
        } catch (e: Exception) {
            UserApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getProfile(token: String): UserApiResponse  {
        return try {
            val response = userApi.getProfile(token)
            if (response.isSuccessful && response.body() != null) {
                UserApiResponse.UserProfileSuccess(response.body()!!)
            } else {
                UserApiResponse.Error("Failed to fetch: ${response.code()}")
            }
        } catch (e: Exception) {
            UserApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun getWatchlist(token: String): UserApiResponse {
        return try {
            val response = userApi.getWatchlist(token)
            if (response.isSuccessful && response.body() != null) {
                UserApiResponse.UserWatchlistStocksSuccess(response.body()!!)
            } else {
                UserApiResponse.Error("Failed to fetch: ${response.code()}")
            }
        } catch (e: Exception) {
            UserApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun addToWatchlist(
        token: String,
        body: Map<String, String>
    ): UserApiResponse  {
        return try{
            val response = userApi.addToWatchlist(token, body)
            if(response.isSuccessful && response.body() != null){
                UserApiResponse.WatchlistEditSuccess(response.body()!!)
            } else{
                UserApiResponse.Error("Failed to add: ${response.code()}")
            }
        }catch (e: Exception){
            UserApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun removeFromWatchlist(
        token: String,
        body: Map<String, String>
    ): UserApiResponse {
        return try{
            val response = userApi.removeFromWatchlist(token, body)
            if(response.isSuccessful && response.body() != null){
                UserApiResponse.WatchlistEditSuccess(response.body()!!)
            } else{
                UserApiResponse.Error("Failed to add: ${response.code()}")
            }
        }catch (e: Exception){
            UserApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun isInWatchlist(
        token: String, symbol: String
    ): UserApiResponse  {
        return try{
            val response = userApi.isInWatchlist(token, symbol)
            if(response.isSuccessful && response.body() != null){
                UserApiResponse.WatchlistCheckSuccess(response.body()!!)
            } else{
                UserApiResponse.Error("Failed to check: ${response.code()}")
            }
        } catch (e: Exception){
            UserApiResponse.Error("Network Error: ${e.localizedMessage}")
        }
    }

    override suspend fun logout(token: String): Boolean {
        return try {
            userApi.logout(token)
            true
        } catch (e: Exception) {
            false
        }
    }
}