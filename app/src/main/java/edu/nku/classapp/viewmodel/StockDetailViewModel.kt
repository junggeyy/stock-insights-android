package edu.nku.classapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.Candle
import edu.nku.classapp.data.model.response.CandleChartResponse
import edu.nku.classapp.data.model.response.StockDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stockApi: StockApiService
) : ViewModel() {

    private val _stockDetails = MutableLiveData<StockDetailResponse?>()
    val stockDetails: LiveData<StockDetailResponse?> = _stockDetails

    private val _chartCandles = MutableLiveData<List<Candle>>()
    val chartCandles: LiveData<List<Candle>> = _chartCandles

    fun loadStockDetails(token: String, symbol: String) {

        stockApi.getStockDetails(token, symbol).enqueue(object :
            Callback<StockDetailResponse> {
            override fun onResponse(call: Call<StockDetailResponse>, response: Response<StockDetailResponse>) {
                _stockDetails.value = response.body()
            }

            override fun onFailure(call: Call<StockDetailResponse>, t: Throwable) {
                _stockDetails.value = null
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
                    _chartCandles.value = response.body()?.candles
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

}
