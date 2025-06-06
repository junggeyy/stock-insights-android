package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.StockApiResponse
import edu.nku.classapp.data.model.UserApiResponse
import edu.nku.classapp.data.repository.StockRepository
import edu.nku.classapp.data.repository.UserRepository
import edu.nku.classapp.model.Candle
import edu.nku.classapp.model.StockDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _stockDetail =
        MutableStateFlow<StockDetailState>(StockDetailState.Loading)
    val stockDetail: StateFlow<StockDetailState> = _stockDetail.asStateFlow()

    private val _chartCandles = MutableStateFlow<List<Candle>>(emptyList())
    val chartCandles: StateFlow<List<Candle>> = _chartCandles.asStateFlow()

    private val _watchlistState = MutableStateFlow<String?>(null)
    val watchlistState: StateFlow<String?> = _watchlistState.asStateFlow()

    private val _isInWatchlist = MutableStateFlow<Boolean?>(null)
    val isInWatchlist: StateFlow<Boolean?> = _isInWatchlist.asStateFlow()

    fun loadStockDetails(token: String, symbol: String) = viewModelScope.launch {
        _stockDetail.value = StockDetailState.Loading
        when (val result = stockRepository.getStockDetail(token, symbol)) {
            is StockApiResponse.StockDetailSuccess -> {
                _stockDetail.value = StockDetailState.Success(result.response)
            }
            is StockApiResponse.Error -> {
                _stockDetail.value = StockDetailState.Failure
            }
            else -> {}
        }
    }

    fun loadChartData(token: String, symbol: String) = viewModelScope.launch {
        when (val result = stockRepository.getStockCandle(token, symbol)) {
            is StockApiResponse.StockCandleSuccess -> {
            _chartCandles.value = result.response.candles
        }
            is StockApiResponse.Error -> {
                _chartCandles.value = emptyList()
            }
            else-> {}
        }
    }

    fun toggleWatchlist(
        token: String,
        symbol: String,
        companyName: String,
        isCurrentlyWatchlisted: Boolean
    ) = viewModelScope.launch {
        val body = mapOf("ticker" to symbol, "name" to companyName)

        val result = if (isCurrentlyWatchlisted) {
            userRepository.removeFromWatchlist(token, body)
        } else {
            userRepository.addToWatchlist(token, body)
        }

        when (result) {
            is UserApiResponse.WatchlistEditSuccess ->{
                _watchlistState.value = result.response.detail
                _isInWatchlist.value = !isCurrentlyWatchlisted
            }
            is UserApiResponse.Error ->{
                _watchlistState.value = "Failed to update watchlist: ${result.message}"
            }
            else -> {}
        }
    }

    fun checkIfInWatchlist(
        token: String,
        ticker: String
    ) = viewModelScope.launch {
        when (val result = userRepository.isInWatchlist(token, ticker)) {
            is UserApiResponse.WatchlistCheckSuccess ->{
                _isInWatchlist.value = result.response.isInWatchlist
            }
            is UserApiResponse.Error -> {
                _isInWatchlist.value = false
            }
            else -> {}
        }
    }


    sealed class StockDetailState {
        data class Success(val stock: StockDetailResponse) : StockDetailState()
        data object Loading : StockDetailState()
        data object Failure : StockDetailState()
    }
}
