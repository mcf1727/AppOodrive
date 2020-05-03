package androidkotlin.kev.appoodrive.detailDetailFolder

import androidkotlin.kev.appoodrive.Internet.GetDataItems
import retrofit2.Call
import retrofit2.http.GET

interface HttpBinServiceJsonDetailDetail {
    //@GET("items/435790b2e8674ca20cd39519ad3219ba664887fe")
    @GET("items/4049f4613c8060370aebf6e4df244aa105a0132b")
    fun getFolderContent() : Call<List<GetDataItems>>
}