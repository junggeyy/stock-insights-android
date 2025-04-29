package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.UserApiResponse
import edu.nku.classapp.data.repository.UserRepository
import edu.nku.classapp.model.UserProfileResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userState = MutableStateFlow<UserProfileState>(UserProfileState.Loading)
    val userState: StateFlow<UserProfileState> = _userState.asStateFlow()

    fun fetchProfile(token: String) = viewModelScope.launch {
        when (val result = userRepository.getProfile(token)) {
            is UserApiResponse.Error -> {
                _userState.value = UserProfileState.Error
            }
            is UserApiResponse.UserProfileSuccess -> {
                _userState.value = UserProfileState.Success(result.response)
            }
            else -> {}
        }
    }

    fun logout(token: String, onResult: (Boolean) -> Unit) =
        viewModelScope.launch {
            onResult(userRepository.logout(token))
        }

    sealed class UserProfileState {
        data object Loading : UserProfileState()
        data class Success(val profile: UserProfileResponse) : UserProfileState()
        data object Error : UserProfileState()
    }
}

