package edu.nku.classapp.data.model

import edu.nku.classapp.data.model.response.CandleChartResponse
import edu.nku.classapp.data.model.response.StockDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface StockApiService {
    @GET("stocks/{symbol}/details")
    fun getStockDetails(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String
    ): Call<StockDetailResponse>

    @GET("stocks/{symbol}/chart/")
    suspend fun getStockCandles(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String): CandleChartResponse
}


