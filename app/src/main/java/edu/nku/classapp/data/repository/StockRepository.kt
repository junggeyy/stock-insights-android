package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.response.HomeStockApiResponse
import edu.nku.classapp.data.model.response.StockIndexApiResponse
import edu.nku.classapp.data.model.response.StockSearchApiResponse


interface StockRepository {
    suspend fun getHomeStocks(token: String): HomeStockApiResponse
    suspend fun getStockSearch(token: String, query: String): StockSearchApiResponse
    suspend fun getStockIndex(token: String): StockIndexApiResponse
}