package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import edu.nku.classapp.data.StockApi
import edu.nku.classapp.model.StockAnalysisResponse
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockAnalysisViewModel @Inject constructor(
    private val stockApi: StockApi  // change to use repository
) : ViewModel() {

    private val _analysis = MutableStateFlow<StockAnalysisState>(StockAnalysisState.Loading)
    val analysis: StateFlow<StockAnalysisState> = _analysis.asStateFlow()

    // try to use when branch and also '=' method
    fun fetchAnalysis(token: String, symbol: String) {
        viewModelScope.launch {
            _analysis.value = StockAnalysisState.Loading
            try {
                val response = stockApi.getStockAnalysis(token, symbol)
                _analysis.value = StockAnalysisState.Success(response)
            } catch (e: Exception) {
                _analysis.value = StockAnalysisState.Failure
            }
        }
    }
    sealed class StockAnalysisState {
        data class Success(val response: StockAnalysisResponse) : StockAnalysisState()
        data object Loading : StockAnalysisState()
        data object Failure : StockAnalysisState()
    }
}



