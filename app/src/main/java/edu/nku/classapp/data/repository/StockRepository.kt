package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.HomeStockApiResponse
import edu.nku.classapp.data.model.StockAnalysisApiResponse
import edu.nku.classapp.data.model.StockCandleApiResponse
import edu.nku.classapp.data.model.StockDetailApiResponse
import edu.nku.classapp.data.model.StockIndexApiResponse
import edu.nku.classapp.data.model.StockSearchApiResponse


interface StockRepository {
    suspend fun getHomeStocks(token: String): HomeStockApiResponse
    suspend fun getStockSearch(token: String, query: String): StockSearchApiResponse
    suspend fun getStockIndex(token: String): StockIndexApiResponse
    suspend fun getStockDetail(token: String, symbol: String): StockDetailApiResponse
    suspend fun getStockCandle(token: String, symbol: String): StockCandleApiResponse
    suspend fun getStockAnalysis(token: String, symbol: String): StockAnalysisApiResponse
}