package edu.nku.classapp.data.model

import edu.nku.classapp.data.model.response.LoginSignupResponse
import retrofit2.Call
import retrofit2.http.*

interface UserApiService {

    @POST("user/signup/")
    fun signup(@Body user: Map<String, String>): Call<LoginSignupResponse>

    @POST("user/login/")
    fun login(@Body credentials: Map<String, String>): Call<LoginSignupResponse>

    @GET("user/test_token/")
    fun testToken(@Header("Authorization") token: String): Call<String>

    @POST("user/logout/")
    fun logout(@Header("Authorization") token: String): Call<Void>
}
