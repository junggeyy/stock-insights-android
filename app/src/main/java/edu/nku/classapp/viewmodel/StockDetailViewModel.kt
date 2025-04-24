package edu.nku.classapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.StockApi
import edu.nku.classapp.data.UserApi
import edu.nku.classapp.model.Candle
import edu.nku.classapp.model.CandleChartResponse
import edu.nku.classapp.model.StockDetailResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockApi: StockApi,     // change to use repository
    private val userApi: UserApi
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

    fun loadStockDetails(token: String, symbol: String) {
        _stockDetail.value = StockDetailState.Loading

        stockApi.getStockDetails(token, symbol).enqueue(object :
            Callback<StockDetailResponse> {
            override fun onResponse(call: Call<StockDetailResponse>,
                                    response: Response<StockDetailResponse>) {
                response.body()?.let {
                    _stockDetail.value = StockDetailState.Success(it)
                } ?: run {
                    _stockDetail.value = StockDetailState.Failure
                }
            }

            override fun onFailure(call: Call<StockDetailResponse>, t: Throwable) {
                _stockDetail.value = StockDetailState.Failure
            }
        })
    }

    fun loadChartData(token: String, symbol: String) {
        stockApi.getStockCandles(token, symbol).enqueue(object : Callback<CandleChartResponse> {
            override fun onResponse(
                call: Call<CandleChartResponse>,
                response: Response<CandleChartResponse>
            ) {
                if (response.isSuccessful) {
                    _chartCandles.value = response.body()?.candles ?: emptyList()
                } else {
                    Log.e("StockDetailVM", "Chart API error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CandleChartResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("StockDetailVM", "Chart API failure: ${t.message}")
            }
        })
    }

    fun toggleWatchlist(token: String, symbol: String, companyName: String, isCurrentlyWatchlisted: Boolean) {
        viewModelScope.launch {
            try {
                val body = mapOf("ticker" to symbol, "name" to companyName)

                val response = if (isCurrentlyWatchlisted) {
                    userApi.removeFromWatchlist(token, body)
                } else {
                    userApi.addToWatchlist(token, body)
                }

                if (response.isSuccessful) {
                    _watchlistState.value = response.body()?.detail
                } else {
                    _watchlistState.value = "Failed to update watchlist."
                }

            } catch (e: Exception) {
                _watchlistState.value = "An error occurred: ${e.message}"
            }
        }
    }

    fun checkIfInWatchlist(token: String, ticker: String) {
        viewModelScope.launch {
            try {
                val response = userApi.isInWatchlist(token, ticker)
                if (response.isSuccessful) {
                    _isInWatchlist.value = response.body()?.isInWatchlist
                } else {
                    _isInWatchlist.value = false
                }
            } catch (e: Exception) {
                _isInWatchlist.value = false
            }
        }
    }

    sealed class StockDetailState {
        data class Success(val stock: StockDetailResponse) : StockDetailState()
        data object Loading : StockDetailState()
        data object Failure : StockDetailState()
    }
}
