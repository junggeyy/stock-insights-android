package edu.nku.classapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.nku.classapp.data.model.response.UserProfileResponse
import edu.nku.classapp.di.AppModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : ViewModel() {

    private val _profile = MutableLiveData<UserProfileResponse>()
    val profile: LiveData<UserProfileResponse> = _profile

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchProfile(token: String) {
        AppModule.instance.getProfile("Token $token")
            .enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        _profile.postValue(response.body())
                    } else {
                        _error.postValue("Profile fetch failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    _error.postValue("Network error: ${t.message}")
                }
            })
    }
}
