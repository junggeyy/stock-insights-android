package edu.nku.classapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.nku.classapp.data.model.StockApiService
import edu.nku.classapp.data.model.response.StockAnalysisResponse
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockAnalysisViewModel @Inject constructor(
    private val stockApi: StockApiService
) : ViewModel() {

    private val _analysis = MutableLiveData<Result<StockAnalysisResponse>>()
    val analysis: LiveData<Result<StockAnalysisResponse>> = _analysis

    fun fetchAnalysis(token: String, symbol: String) {
        Log.d("AnalysisPage", "Calling API with token=$token and symbol=$symbol")

        viewModelScope.launch {
            try {
                val response = stockApi.getStockAnalysis(token, symbol)
                _analysis.value = Result.success(response)
            } catch (e: Exception) {
                _analysis.value = Result.failure(e)
            }
        }
    }
}

