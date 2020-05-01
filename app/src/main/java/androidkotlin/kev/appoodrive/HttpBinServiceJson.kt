package androidkotlin.kev.appoodrive

import retrofit2.Call
import retrofit2.http.GET

interface HttpBinServiceJson {
    @GET("items/435790b2e8674ca20cd39519ad3219ba664887fe")
    //@GET("items/435790b2e8674ca20cd39519ad3219ba664887fe")
    fun getFolderContent() : Call<List<GetDataItems>>
}