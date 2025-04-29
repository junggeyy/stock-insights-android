package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.StockApiResponse
import edu.nku.classapp.model.Stock
import edu.nku.classapp.data.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _stocks = MutableStateFlow<HomepageStockState>(HomepageStockState.Loading)
    val stocks = _stocks.asStateFlow()

    var hasLoaded = false
        private set

    fun fetchHomepageStocks(token: String) =
        viewModelScope.launch {
            when (val result = stockRepository.getHomeStocks(token)) {
                is StockApiResponse.HomeStockSuccess -> {
                    _stocks.value = HomepageStockState.Success(result.response.data)
                    hasLoaded = true
                }
                is StockApiResponse.Error -> {
                    _stocks.value = HomepageStockState.Failure
                }
                else -> {}
            }
        }


    sealed class HomepageStockState {
        data class Success(val stocks: List<Stock>) : HomepageStockState()
        data object Loading : HomepageStockState()
        data object Failure : HomepageStockState()
    }
}
