package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.StockApiResponse

interface StockRepository {
    suspend fun getHomeStocks(token: String): StockApiResponse
    suspend fun getStockSearch(token: String, query: String): StockApiResponse
    suspend fun getStockIndex(token: String): StockApiResponse
    suspend fun getStockDetail(token: String, symbol: String): StockApiResponse
    suspend fun getStockCandle(token: String, symbol: String): StockApiResponse
    suspend fun getStockAnalysis(token: String, symbol: String): StockApiResponse
}