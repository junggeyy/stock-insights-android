package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.StockSearchResponse
import javax.inject.Inject

class StockRepositoryReal @Inject constructor(
    private val stockApiService: StockApiService
) : StockRepository {
    override suspend fun getStockSearch(token: String, query: String): StockSearchResponse? {
        val response = stockApiService.getStockSearch(token, query)
        return if (response.isSuccessful) response.body() else null
    }
}
