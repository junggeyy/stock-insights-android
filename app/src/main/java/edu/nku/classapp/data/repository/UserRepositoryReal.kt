package edu.nku.classapp.data.repository

import edu.nku.classapp.data.UserApi
import edu.nku.classapp.data.model.AuthApiResponse
import edu.nku.classapp.data.model.UserProfileApiResponse
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

    override suspend fun logout(token: String): Boolean {
        return try {
            userApi.logout("Token $token")
            true
        } catch (e: Exception) {
            false
        }
    }
}
