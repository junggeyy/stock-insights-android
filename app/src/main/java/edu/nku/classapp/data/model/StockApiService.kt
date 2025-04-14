package edu.nku.classapp.data.model;

import retrofit2.Call
import retrofit2.http.*

interface StockApiService {
    @GET("stocks/{symbol}/details")
    fun stockDetail(@Header("Authorization") token: String): Call<String>
}
