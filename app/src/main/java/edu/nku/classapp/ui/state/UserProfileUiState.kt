package edu.nku.classapp.ui.state

import edu.nku.classapp.data.model.response.UserProfileResponse

sealed class UserProfileUiState {
    object Loading : UserProfileUiState()
    data class Success(val profile: UserProfileResponse) : UserProfileUiState()
    data class Error(val message: String) : UserProfileUiState()
}