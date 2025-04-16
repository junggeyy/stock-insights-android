package edu.nku.classapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.nku.classapp.data.model.response.UserProfileResponse
import edu.nku.classapp.di.AppModule
import edu.nku.classapp.ui.state.UserProfileUiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : ViewModel() {

    private val _uiState = MutableLiveData<UserProfileUiState>()
    val uiState: LiveData<UserProfileUiState> = _uiState

    fun fetchProfile(token: String) {
        _uiState.value = UserProfileUiState.Loading

        AppModule.instance.getProfile("Token $token")
            .enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        _uiState.value = UserProfileUiState.Success(response.body()!!)
                    } else {
                        _uiState.value = UserProfileUiState.Error("Fetch failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    _uiState.value = UserProfileUiState.Error("Network error: ${t.message}")
                }
            })
    }
}

