package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.HomeStockApiResponse
import edu.nku.classapp.data.model.StockIndexApiResponse
import edu.nku.classapp.data.model.StockSearchApiResponse


interface StockRepository {
    suspend fun getHomeStocks(token: String): HomeStockApiResponse
    suspend fun getStockSearch(token: String, query: String): StockSearchApiResponse
    suspend fun getStockIndex(token: String): StockIndexApiResponse
}