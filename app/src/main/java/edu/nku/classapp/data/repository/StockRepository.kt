package edu.nku.classapp.data.repository
import edu.nku.classapp.data.model.response.StockSearchResponse

interface StockRepository {
    suspend fun getStockSearch(token: String, query: String): StockSearchResponse?
}