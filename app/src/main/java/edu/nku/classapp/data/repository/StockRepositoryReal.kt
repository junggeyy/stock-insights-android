package edu.nku.classapp.data.repository

import edu.nku.classapp.data.StockApi
import edu.nku.classapp.data.model.AuthApiResponse
import edu.nku.classapp.data.model.HomeStockApiResponse
import edu.nku.classapp.data.model.StockAnalysisApiResponse
import edu.nku.classapp.data.model.StockCandleApiResponse
import edu.nku.classapp.data.model.StockDetailApiResponse
import edu.nku.classapp.data.model.StockIndexApiResponse
import edu.nku.classapp.data.model.StockSearchApiResponse
import javax.inject.Inject

class StockRepositoryReal @Inject constructor(
    private val stockApi: StockApi
) : StockRepository {

    override suspend fun getHomeStocks(token: String): HomeStockApiResponse {
        return try {
            val response = stockApi.getHomepageStocks(token)
            return if (response.isSuccessful) {
                response.body()?.let {
                    HomeStockApiResponse.Success(it)
                } ?: HomeStockApiResponse.Error("Failed" + response.code())
            } else {
                HomeStockApiResponse.Error("Failed" + response.code())
            }
        } catch (e: Exception) {
            HomeStockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockSearch(token: String, query: String): StockSearchApiResponse {
        return try {
            val response = stockApi.getStockSearch(token, query)
            return if (response.isSuccessful) {
                response.body()?.let {
                    StockSearchApiResponse.Success(it)
                } ?: StockSearchApiResponse.Error("Failed" + response.code())
            } else {
                StockSearchApiResponse.Error("Failed" + response.code())
            }
        } catch (e: Exception) {
            StockSearchApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockIndex(token: String): StockIndexApiResponse {
        return try {
            val response = stockApi.getStockIndex(token)
            return if (response.isSuccessful) {
                response.body()?.let {
                    StockIndexApiResponse.Success(it)
                } ?: StockIndexApiResponse.Error("Failed" + response.code())
            } else {
                StockIndexApiResponse.Error("Failed" + response.code())
            }
        } catch (e: Exception) {
            StockIndexApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockDetail(token: String, symbol: String): StockDetailApiResponse {
        return try {
            val response = stockApi.getStockDetail(token, symbol)
            return if (response.isSuccessful) {
                response.body()?.let {
                    StockDetailApiResponse.Success(it)
                } ?: StockDetailApiResponse.Error("Failed" + response.code())
            } else {
                StockDetailApiResponse.Error("Failed" + response.code())
            }
        } catch (e: Exception) {
            StockDetailApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockCandle(token: String, symbol: String): StockCandleApiResponse {
        return try {
            val response = stockApi.getStockCandle(token, symbol)
            return if (response.isSuccessful) {
                response.body()?.let {
                    StockCandleApiResponse.Success(it)
                } ?: StockCandleApiResponse.Error("Failed" + response.code())
            } else {
                StockCandleApiResponse.Error("Failed" + response.code())
            }
        } catch (e: Exception) {
            StockCandleApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockAnalysis(token: String, symbol: String): StockAnalysisApiResponse {
        return try{
            val response = stockApi.getStockAnalysis(token, symbol)
            return if (response.isSuccessful) {
                response.body()?.let {
                    StockAnalysisApiResponse.Success(it)
                } ?: StockAnalysisApiResponse.Error("Failed" + response.code())
            } else {
                StockAnalysisApiResponse.Error("Failed" + response.code())
            }
        } catch (e: Exception) {
            StockAnalysisApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }
}
