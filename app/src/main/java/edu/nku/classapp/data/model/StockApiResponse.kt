package edu.nku.classapp.data.model

import edu.nku.classapp.model.CandleChartResponse
import edu.nku.classapp.model.HomeStockResponse
import edu.nku.classapp.model.StockAnalysisResponse
import edu.nku.classapp.model.StockDetailResponse
import edu.nku.classapp.model.StockIndexResponse
import edu.nku.classapp.model.StockSearchResponse


sealed class HomeStockApiResponse{
    data class Success(val response: HomeStockResponse) : HomeStockApiResponse()
    data object Error : HomeStockApiResponse()
}

sealed class StockIndexApiResponse {
    data class Success(val response: StockIndexResponse) : StockIndexApiResponse()
    data object Error : StockIndexApiResponse()
}

sealed class StockSearchApiResponse {
    data class Success(val response: StockSearchResponse) : StockSearchApiResponse()
    data object Error : StockSearchApiResponse()
}

sealed class StockDetailApiResponse {
    data class Success(val response: StockDetailResponse) : StockDetailApiResponse()
    data object Error : StockDetailApiResponse()
}

sealed class StockCandleApiResponse {
    data class Success(val response: CandleChartResponse) : StockCandleApiResponse()
    data object Error : StockCandleApiResponse()
}

sealed class StockAnalysisApiResponse {
    data class Success(val response: StockAnalysisResponse) : StockAnalysisApiResponse()
    data object Error : StockAnalysisApiResponse()
}
