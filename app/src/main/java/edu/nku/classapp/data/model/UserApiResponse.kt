package edu.nku.classapp.data.model

import edu.nku.classapp.model.LoginSignupResponse
import edu.nku.classapp.model.UserProfileResponse
import edu.nku.classapp.model.WatchListCheckResponse
import edu.nku.classapp.model.WatchlistEditResponse
import edu.nku.classapp.model.WatchlistResponse

sealed class UserProfileApiResponse {
    data class Success(val profile: UserProfileResponse) : UserProfileApiResponse()
    data class Error(val message: String) : UserProfileApiResponse()
}

sealed class AuthApiResponse {
    data class Success(val response: LoginSignupResponse) : AuthApiResponse()
    data class Error(val message: String) : AuthApiResponse()
}

sealed class WatchlistEditApiResponse {
    data class Success(val response: WatchlistEditResponse) : WatchlistEditApiResponse()
    data class Error(val message: String) : WatchlistEditApiResponse()
}

sealed class WatchlistApiResponse {
    data class Success(val response: List<WatchlistResponse>) : WatchlistApiResponse()
    data class Error(val message: String) : WatchlistApiResponse()
}

sealed class WatchlistCheckApiResponse {
    data class Success(val response: WatchListCheckResponse) : WatchlistCheckApiResponse()
    data class Error(val message: String) : WatchlistCheckApiResponse()
}



