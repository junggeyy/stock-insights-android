package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.StockIndexApiResponse
import edu.nku.classapp.model.StockIndexResponse
import edu.nku.classapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockIndexViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _indexData = MutableStateFlow<StockIndexState>(StockIndexState.Loading)
    val indexData: StateFlow<StockIndexState> = _indexData.asStateFlow()

    fun fetchIndex(token: String) = viewModelScope.launch {
        when (val result = stockRepository.getStockIndex(token)) {
            StockIndexApiResponse.Error -> _indexData.value = StockIndexState.Failure
            is StockIndexApiResponse.Success ->
                _indexData.value = StockIndexState.Success(result.response)
        }
    }

    sealed class StockIndexState {
        data class Success(val results: StockIndexResponse) : StockIndexState()
        data object Loading : StockIndexState()
        data object Failure : StockIndexState()
    }
}