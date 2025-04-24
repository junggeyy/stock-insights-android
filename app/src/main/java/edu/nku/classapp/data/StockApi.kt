package edu.nku.classapp.data

import edu.nku.classapp.model.CandleChartResponse
import edu.nku.classapp.model.HomeStockResponse
import edu.nku.classapp.model.StockAnalysisResponse
import edu.nku.classapp.model.StockDetailResponse
import edu.nku.classapp.model.StockIndexResponse
import edu.nku.classapp.model.StockSearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface StockApi {
    @GET("stocks/home/")
    suspend fun getHomepageStocks(
        @Header("Authorization") token: String
    ): Response<HomeStockResponse>

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

    @GET("stocks/index/")
    suspend fun getStockIndex(
        @Header("Authorization") token: String,
    ): Response<StockIndexResponse>
}


