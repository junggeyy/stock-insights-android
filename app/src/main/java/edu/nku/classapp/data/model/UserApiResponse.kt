package edu.nku.classapp.data.model

import edu.nku.classapp.model.LoginSignupResponse
import edu.nku.classapp.model.UserProfileResponse

sealed class UserProfileApiResponse {
    data class Success(val profile: UserProfileResponse) : UserProfileApiResponse()
    data class Error(val message: String) : UserProfileApiResponse()
}

sealed class AuthApiResponse {
    data class Success(val response: LoginSignupResponse) : AuthApiResponse()
    data class Error(val message: String) : AuthApiResponse()
}
