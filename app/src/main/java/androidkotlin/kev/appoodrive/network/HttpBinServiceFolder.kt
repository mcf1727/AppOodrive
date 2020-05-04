package androidkotlin.kev.appoodrive.network

import androidkotlin.kev.appoodrive.network.GetDataItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//interface HttpBinServiceJson {
//    @GET("items/435790b2e8674ca20cd39519ad3219ba664887fe")
//    //@GET("me")
//    fun getFolderContent() : Call<List<GetDataItems>>
//}

interface HttpBinServiceFolder {
    @GET("/items/{id}")
    //@GET("me")
    fun getFolderContent(@Path("id") idFolder: String) : Call<List<GetDataItems>>
}