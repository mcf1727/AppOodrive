package androidkotlin.kev.appoodrive.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HttpBinServiceFolder {
    @GET("/items/{id}")
    //@GET("me")
    fun getFolderContent(@Path("id") idFolder: String) : Call<List<GetDataItems>>
}