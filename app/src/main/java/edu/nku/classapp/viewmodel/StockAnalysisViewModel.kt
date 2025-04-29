package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import edu.nku.classapp.model.StockAnalysisResponse
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.StockApiResponse
import edu.nku.classapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockAnalysisViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _analysis = MutableStateFlow<StockAnalysisState>(StockAnalysisState.Loading)
    val analysis: StateFlow<StockAnalysisState> = _analysis.asStateFlow()

    fun fetchAnalysis(token: String, symbol: String) = viewModelScope.launch {
        when(val result = stockRepository.getStockAnalysis(token, symbol)){
            is StockApiResponse.StockAnalysisSuccess ->{
                _analysis.value = StockAnalysisState.Success(result.response)
            }

            is StockApiResponse.Error -> {
                _analysis.value = StockAnalysisState.Failure
            }
            else -> {}
        }
    }

    sealed class StockAnalysisState {
        data class Success(val response: StockAnalysisResponse) : StockAnalysisState()
        data object Loading : StockAnalysisState()
        data object Failure : StockAnalysisState()
    }
}



