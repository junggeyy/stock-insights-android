package edu.nku.classapp.data.repository

import edu.nku.classapp.data.StockApi
import edu.nku.classapp.data.model.StockApiResponse
import javax.inject.Inject

class StockRepositoryReal @Inject constructor(
    private val stockApi: StockApi
) : StockRepository {

    override suspend fun getHomeStocks(token: String): StockApiResponse {
        return try {
            val response = stockApi.getHomepageStocks(token)
            return if (response.isSuccessful && response.body() != null) {
                StockApiResponse.HomeStockSuccess(response.body()!!)
            } else {
                StockApiResponse.Error("Failed to load:" + response.code())
            }
        } catch (e: Exception) {
            StockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockSearch(token: String, query: String): StockApiResponse {
        return try {
            val response = stockApi.getStockSearch(token, query)
            return if (response.isSuccessful && response.body() != null) {
                StockApiResponse.StockSearchSuccess(response.body()!!)
            } else {
                StockApiResponse.Error("Failed to load:" + response.code())
            }
        } catch (e: Exception) {
            StockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockIndex(token: String): StockApiResponse {
        return try {
            val response = stockApi.getStockIndex(token)
            return if (response.isSuccessful && response.body() != null) {
                StockApiResponse.StockIndexSuccess(response.body()!!)
            } else {
                StockApiResponse.Error("Failed to load:" + response.code())
            }
        } catch (e: Exception) {
            StockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockDetail(token: String, symbol: String): StockApiResponse {
        return try {
            val response = stockApi.getStockDetail(token, symbol)
            return if (response.isSuccessful && response.body() != null) {
                StockApiResponse.StockDetailSuccess(response.body()!!)
            } else {
                StockApiResponse.Error("Failed to load:" + response.code())
            }
        } catch (e: Exception) {
            StockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockCandle(token: String, symbol: String): StockApiResponse {
        return try {
            val response = stockApi.getStockCandle(token, symbol)
            return if (response.isSuccessful && response.body() != null) {
                StockApiResponse.StockCandleSuccess(response.body()!!)
            } else {
                StockApiResponse.Error("Failed to load:" + response.code())
            }
        } catch (e: Exception) {
            StockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }

    override suspend fun getStockAnalysis(token: String, symbol: String): StockApiResponse {
        return try{
            val response = stockApi.getStockAnalysis(token, symbol)
            return if (response.isSuccessful && response.body() != null) {
                StockApiResponse.StockAnalysisSuccess(response.body()!!)
            } else {
                StockApiResponse.Error("Failed to load:" + response.code())
            }
        } catch (e: Exception) {
            StockApiResponse.Error("Network error: ${e.localizedMessage}")
        }
    }
}
