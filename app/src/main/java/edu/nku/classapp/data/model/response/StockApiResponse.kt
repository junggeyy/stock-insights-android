package edu.nku.classapp.data.model.response


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
