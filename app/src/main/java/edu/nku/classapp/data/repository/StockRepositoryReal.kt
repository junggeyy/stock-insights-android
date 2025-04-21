package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.HomeStockApiResponse
import edu.nku.classapp.data.model.response.StockIndexApiResponse
import edu.nku.classapp.data.model.response.StockSearchApiResponse
import javax.inject.Inject

class StockRepositoryReal @Inject constructor(
    private val stockApiService: StockApiService
) : StockRepository {

    override suspend fun getHomeStocks(token: String): HomeStockApiResponse {
        val response = stockApiService.getHomepageStocks(token)
        return if (response.isSuccessful) {
            response.body()?.let {
                HomeStockApiResponse.Success(it)
            } ?: HomeStockApiResponse.Error
        } else {
            HomeStockApiResponse.Error
        }
    }

    override suspend fun getStockSearch(token: String, query: String): StockSearchApiResponse {
        val response = stockApiService.getStockSearch(token, query)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockSearchApiResponse.Success(it)
            } ?: StockSearchApiResponse.Error
        } else {
            StockSearchApiResponse.Error
        }
    }

    override suspend fun getStockIndex(token: String): StockIndexApiResponse {
        val response = stockApiService.getStockIndex(token)
        return if (response.isSuccessful) {
            response.body()?.let {
                StockIndexApiResponse.Success(it)
            } ?: StockIndexApiResponse.Error
        } else {
            StockIndexApiResponse.Error
        }
    }
}
