package edu.nku.classapp.data.model

import edu.nku.classapp.model.CandleChartResponse
import edu.nku.classapp.model.HomeStockResponse
import edu.nku.classapp.model.StockAnalysisResponse
import edu.nku.classapp.model.StockDetailResponse
import edu.nku.classapp.model.StockIndexResponse
import edu.nku.classapp.model.StockSearchResponse

sealed class StockApiResponse {
    data class HomeStockSuccess(val response: HomeStockResponse) : StockApiResponse()
    data class StockIndexSuccess(val response: StockIndexResponse) : StockApiResponse()
    data class StockSearchSuccess(val response: StockSearchResponse) : StockApiResponse()
    data class StockDetailSuccess(val response: StockDetailResponse) : StockApiResponse()
    data class StockCandleSuccess(val response: CandleChartResponse) : StockApiResponse()
    data class StockAnalysisSuccess(val response: StockAnalysisResponse) : StockApiResponse()

    data class Error(val message: String) : StockApiResponse()
}
