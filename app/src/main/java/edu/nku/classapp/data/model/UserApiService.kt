package edu.nku.classapp.data.model

import retrofit2.Call
import retrofit2.http.*

interface UserApiService {

    @POST("user/signup/")
    fun signup(@Body user: Map<String, String>): Call<SignupResponse>

    @POST("user/login/")
    fun login(@Body credentials: Map<String, String>): Call<LoginResponse>

    @GET("test_token")
    fun testToken(@Header("Authorization") token: String): Call<String>

    @POST("logout")
    fun logout(@Header("Authorization") token: String): Call<Void>
}
