package edu.nku.classapp.data.model

import edu.nku.classapp.model.CandleChartResponse
import edu.nku.classapp.model.HomeStockResponse
import edu.nku.classapp.model.StockAnalysisResponse
import edu.nku.classapp.model.StockDetailResponse
import edu.nku.classapp.model.StockIndexResponse
import edu.nku.classapp.model.StockSearchResponse


sealed class HomeStockApiResponse{
    data class Success(val response: HomeStockResponse) : HomeStockApiResponse()
    data class Error(val message: String) : HomeStockApiResponse()
}

sealed class StockIndexApiResponse {
    data class Success(val response: StockIndexResponse) : StockIndexApiResponse()
    data class Error(val message: String) : StockIndexApiResponse()
}

sealed class StockSearchApiResponse {
    data class Success(val response: StockSearchResponse) : StockSearchApiResponse()
    data class Error(val message: String) : StockSearchApiResponse()
}

sealed class StockDetailApiResponse {
    data class Success(val response: StockDetailResponse) : StockDetailApiResponse()
    data class Error(val message: String) : StockDetailApiResponse()
}

sealed class StockCandleApiResponse {
    data class Success(val response: CandleChartResponse) : StockCandleApiResponse()
    data class Error(val message: String) : StockCandleApiResponse()
}

sealed class StockAnalysisApiResponse {
    data class Success(val response: StockAnalysisResponse) : StockAnalysisApiResponse()
    data class Error(val message: String) : StockAnalysisApiResponse()
}
