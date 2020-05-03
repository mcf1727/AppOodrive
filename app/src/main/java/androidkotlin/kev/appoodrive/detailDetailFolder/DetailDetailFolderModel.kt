package androidkotlin.kev.appoodrive.detailDetailFolder

import androidkotlin.kev.appoodrive.Internet.AuthenticationInterceptor
import androidkotlin.kev.appoodrive.Internet.GetDataItems
import androidkotlin.kev.appoodrive.Internet.Item
import androidkotlin.kev.appoodrive.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailDetailFolderModel(val view: MainActivity) {

    /**
     * HTTP Basic Authentication before all API request
     */
    fun authentication(user: String, password: String): OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(
            AuthenticationInterceptor(
                user,
                password
            )
        )
        .build()

    /**
     * GET request to server
     */
    fun requestServer(url: String, okHttpClient: OkHttpClient, viewId: Int) {
        val retrofitJson = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //val serviceJson: HttpBinServiceJson =retrofitJson.create(HttpBinServiceJson::class.java)
        val serviceJson: HttpBinServiceJsonDetailDetail =retrofitJson.create(HttpBinServiceJsonDetailDetail::class.java)
        val callJson = serviceJson.getFolderContent()

        callJson.enqueue(object: Callback<List<GetDataItems>> {
            override fun onResponse(call: Call<List<GetDataItems>>?, response: Response<List<GetDataItems>>) {
                val getDataItems = response.body()
                val items = mutableListOf<Item>()
                for (i in 0 until getDataItems?.size!!) {
                    items.add(Item(getDataItems[i].name))
                }
                view.onSuccessfulDataHandled(items, viewId)
            }
            override fun onFailure(call: Call<List<GetDataItems>>, throwable: Throwable) {
                view.onFailedDataHandled(throwable)
            }
        })
    }

}