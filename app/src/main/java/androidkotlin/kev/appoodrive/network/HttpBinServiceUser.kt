package androidkotlin.kev.appoodrive.network

import retrofit2.Call
import retrofit2.http.GET

interface HttpBinServiceUser {
    @GET("me")
    fun getFolderContent(): Call<GetUser>
}