package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.UserApiResponse
import edu.nku.classapp.data.repository.UserRepository
import edu.nku.classapp.model.LoginSignupResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState>  = _authState.asStateFlow()

    fun login(email: String, password: String) =
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = userRepository.login(email, password)) {
                is UserApiResponse.UserAuthSuccess -> {
                    _authState.value = AuthState.Success(result.response)
                }
                is UserApiResponse.Error ->{
                    _authState.value = AuthState.Failure(result.message)
                }
                else -> {}
            }
        }

    fun signup(data: Map<String, String>) =
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = userRepository.signup(data)) {
                is UserApiResponse.UserAuthSuccess -> {
                    _authState.value = AuthState.Success(result.response)
                }
                is UserApiResponse.Error -> {
                    _authState.value = AuthState.Failure(result.message)
                }
                else -> {}
            }
        }

    sealed class AuthState {
        data object Loading : AuthState()
        data class Success(val response: LoginSignupResponse) : AuthState()
        data class Failure(val message: String) : AuthState()
    }
}
