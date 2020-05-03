package androidkotlin.kev.appoodrive.detailFolder

import androidkotlin.kev.appoodrive.Internet.GetDataItems
import retrofit2.Call
import retrofit2.http.GET

//interface HttpBinServiceJson {
//    @GET("items/435790b2e8674ca20cd39519ad3219ba664887fe")
//    //@GET("me")
//    fun getFolderContent() : Call<List<GetDataItems>>
//}

interface HttpBinServiceJson {
    @GET("items/435790b2e8674ca20cd39519ad3219ba664887fe")
    //@GET("me")
    fun getFolderContent() : Call<List<GetDataItems>>
}