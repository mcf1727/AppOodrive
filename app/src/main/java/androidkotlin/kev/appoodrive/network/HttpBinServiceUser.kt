package androidkotlin.kev.appoodrive.network

import androidkotlin.kev.appoodrive.network.GetDataItems
import androidkotlin.kev.appoodrive.network.GetUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HttpBinServiceUser {
    @GET("me")
    fun getFolderContent(): Call<GetUser>
}