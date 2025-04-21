package edu.nku.classapp.data.model

import edu.nku.classapp.data.model.response.LoginSignupResponse
import edu.nku.classapp.data.model.response.UserProfileResponse
import edu.nku.classapp.data.model.response.WatchListResponse
import edu.nku.classapp.data.model.response.WatchListCheckResponse
import retrofit2.Response
import retrofit2.Call
import retrofit2.http.*

interface UserApiService {

    @POST("user/signup/")
    fun signup(@Body user: Map<String, String>): Call<LoginSignupResponse>

    @POST("user/login/")
    fun login(@Body credentials: Map<String, String>): Call<LoginSignupResponse>

    @POST("user/logout/")
    fun logout(@Header("Authorization") token: String): Call<Void>

    @GET("user/profile/")
    fun getProfile(@Header("Authorization") token: String): Call<UserProfileResponse>

    @POST("user/watchlist/add/")
    suspend fun addToWatchlist(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<WatchListResponse>

    @POST("user/watchlist/remove/")
    suspend fun removeFromWatchlist(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<WatchListResponse>

    @GET("user/watchlist/contains/{ticker}/")
    suspend fun isInWatchlist(
        @Header("Authorization") token: String,
        @Path("ticker") ticker: String
    ): Response<WatchListCheckResponse>

}
