package edu.nku.classapp.data.repository

import edu.nku.classapp.data.StockApi
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
        val response = stockApi.getHomepageStocks(token)
        return if (response.isSuccessful) {
            response.body()?.let {
                HomeStockApiResponse.Success(it)
            } ?: HomeStockApiResponse.Error
        } else {
            HomeStockApiResponse.Error
        }
    }

    override suspend fun getStockSearch(token: String, query: String): StockSearchApiResponse {
        val response = stockApi.getStockSearch(token, query)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockSearchApiResponse.Success(it)
            } ?: StockSearchApiResponse.Error
        } else {
            StockSearchApiResponse.Error
        }
    }

    override suspend fun getStockIndex(token: String): StockIndexApiResponse {
        val response = stockApi.getStockIndex(token)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockIndexApiResponse.Success(it)
            } ?: StockIndexApiResponse.Error
        } else {
            StockIndexApiResponse.Error
        }
    }

    override suspend fun getStockDetail(token: String, symbol: String): StockDetailApiResponse {
        val response = stockApi.getStockDetail(token, symbol)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockDetailApiResponse.Success(it)
            } ?: StockDetailApiResponse.Error
        } else {
            StockDetailApiResponse.Error
        }
    }

    override suspend fun getStockCandle(token: String, symbol: String): StockCandleApiResponse {
        val response = stockApi.getStockCandle(token, symbol)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockCandleApiResponse.Success(it)
            } ?: StockCandleApiResponse.Error
        } else {
            StockCandleApiResponse.Error
        }
    }

    override suspend fun getStockAnalysis(token: String, symbol: String): StockAnalysisApiResponse {
        val response = stockApi.getStockAnalysis(token, symbol)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockAnalysisApiResponse.Success(it)
            } ?: StockAnalysisApiResponse.Error
        } else {
            StockAnalysisApiResponse.Error
        }
    }


}
