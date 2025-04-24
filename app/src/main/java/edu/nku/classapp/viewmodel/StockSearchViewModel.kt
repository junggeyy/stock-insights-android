package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.StockSearchApiResponse
import edu.nku.classapp.model.StockSearchResponse
import edu.nku.classapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockSearchViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow<StockSearchState>(StockSearchState.Loading)
    val searchState: StateFlow<StockSearchState> = _searchState.asStateFlow()

    fun stockSearch(token: String, query: String) {
        viewModelScope.launch {
            _searchState.value = StockSearchState.Loading
            when (val result = stockRepository.getStockSearch(token, query)) {
                is StockSearchApiResponse.Success -> {
                    _searchState.value = StockSearchState.Success(listOf(result.response))
                }
                is StockSearchApiResponse.Error -> {
                    _searchState.value = StockSearchState.Failure
                }
            }
        }
    }

    sealed class StockSearchState {
        data class Success(val results: List<StockSearchResponse>) : StockSearchState()
        data object Loading : StockSearchState()
        data object Failure : StockSearchState()
    }
}