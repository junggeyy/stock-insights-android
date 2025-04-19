package edu.nku.classapp.data.model

import edu.nku.classapp.data.model.response.CandleChartResponse
import edu.nku.classapp.data.model.response.HomeStockResponse
import edu.nku.classapp.data.model.response.StockAnalysisResponse
import edu.nku.classapp.data.model.response.StockDetailResponse
import edu.nku.classapp.data.model.response.StockSearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface StockApiService {
    @GET("stocks/home/")
    suspend fun getHomepageStocks(
        @Header("Authorization") token: String
    ): HomeStockResponse

    @GET("stocks/{symbol}/details")
    fun getStockDetails(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String
    ): Call<StockDetailResponse>

    @GET("stocks/{symbol}/chart/")
    fun getStockCandles(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String): Call<CandleChartResponse>

    @GET("stocks/{symbol}/analysis/")
    suspend fun getStockAnalysis(
        @Header("Authorization") token: String,
        @Path("symbol") symbol: String
    ): StockAnalysisResponse

    @GET("stocks/{symbol}/search/")
    suspend fun getStockSearch(
        @Header("Authorization") token: String,
        @Path("symbol") query: String
    ): Response<StockSearchResponse>
}


