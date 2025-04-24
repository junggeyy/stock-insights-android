package edu.nku.classapp.data.repository

import edu.nku.classapp.data.StockApi
import edu.nku.classapp.data.model.HomeStockApiResponse
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
}
