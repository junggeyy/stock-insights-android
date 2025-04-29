package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.UserApiResponse
import edu.nku.classapp.data.repository.UserRepository
import edu.nku.classapp.model.WatchlistResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _watchlist = MutableStateFlow<WatchlistState>(WatchlistState.Loading)
    val watchlist: StateFlow<WatchlistState> = _watchlist.asStateFlow()

    fun fetchWatchlist(token: String) = viewModelScope.launch {
        _watchlist.value = WatchlistState.Loading
        when (val result = userRepository.getWatchlist(token)) {
            is UserApiResponse.UserWatchlistStocksSuccess -> {
                _watchlist.value = WatchlistState.Success(result.response)
            }
            is UserApiResponse.Error -> {
                _watchlist.value = WatchlistState.Failure(result.message)
            }
            else -> {}
        }
    }

    sealed class WatchlistState {
        data class Success(val stocks: List<WatchlistResponse>) : WatchlistState()
        data object Loading : WatchlistState()
        data class Failure(val error: String) : WatchlistState()
    }
}
