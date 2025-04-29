package edu.nku.classapp.data.model

import edu.nku.classapp.model.LoginSignupResponse
import edu.nku.classapp.model.UserProfileResponse
import edu.nku.classapp.model.WatchListCheckResponse
import edu.nku.classapp.model.WatchlistEditResponse
import edu.nku.classapp.model.WatchlistResponse

sealed class UserApiResponse{
    data class UserProfileSuccess(val response: UserProfileResponse): UserApiResponse()
    data class UserAuthSuccess(val response: LoginSignupResponse): UserApiResponse()
    data class WatchlistEditSuccess(val response: WatchlistEditResponse): UserApiResponse()
    data class UserWatchlistStocksSuccess(val response: List<WatchlistResponse>): UserApiResponse()
    data class WatchlistCheckSuccess(val response: WatchListCheckResponse) : UserApiResponse()

    data class Error(val message: String): UserApiResponse()
}
