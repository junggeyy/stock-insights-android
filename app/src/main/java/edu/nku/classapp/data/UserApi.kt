package edu.nku.classapp.data

import edu.nku.classapp.model.LoginSignupResponse
import edu.nku.classapp.model.UserProfileResponse
import edu.nku.classapp.model.WatchlistResponse
import edu.nku.classapp.model.WatchListCheckResponse
import edu.nku.classapp.model.WatchlistEditResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("user/signup/")
    suspend fun signup(@Body user: Map<String, String>): Response<LoginSignupResponse>

    @POST("user/login/")
    suspend fun login(@Body credentials: Map<String, String>): Response<LoginSignupResponse>

    @POST("user/logout/")
    suspend fun logout(@Header("Authorization") token: String): Response<Void>

    @GET("user/profile/")
    suspend fun getProfile(@Header("Authorization") token: String): Response<UserProfileResponse>

    @GET("user/watchlist/")
    suspend fun getWatchlist(
        @Header("Authorization") token: String
    ): Response<List<WatchlistResponse>>

    @POST("user/watchlist/add/")
    suspend fun addToWatchlist(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<WatchlistEditResponse>

    @POST("user/watchlist/remove/")
    suspend fun removeFromWatchlist(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<WatchlistEditResponse>

    @GET("user/watchlist/contains/{ticker}/")
    suspend fun isInWatchlist(
        @Header("Authorization") token: String,
        @Path("ticker") ticker: String
    ): Response<WatchListCheckResponse>

}
