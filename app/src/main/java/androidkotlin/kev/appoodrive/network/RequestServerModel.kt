package androidkotlin.kev.appoodrive.network

import androidkotlin.kev.appoodrive.Item
import androidkotlin.kev.appoodrive.MainActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestServerModel(val view: MainActivity) {

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
     * GET request to server (content in the folder)
     */
    fun requestFolder(url: String, okHttpClient: OkHttpClient, viewId: Int, idFolder: String) {
        val retrofitJson = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceJson: HttpBinServiceFolder = retrofitJson.create(
            HttpBinServiceFolder::class.java)
        val callJson = serviceJson.getFolderContent(idFolder)

        callJson.enqueue(object: Callback<List<GetDataItems>> {
            override fun onResponse(call: Call<List<GetDataItems>>?, response: Response<List<GetDataItems>>) {
                val getDataItems = response.body()
                val items = mutableListOf<Item>()
                val idFolders = mutableListOf<String>()
                for (i in 0 until getDataItems?.size!!) {
                    items.add(Item(getDataItems[i].name))
                    idFolders.add(getDataItems[i].id)    //*
                }
                println("MainActivity idFolders in onResponse $idFolders")          //need to remove

                view.onSuccessfulDataHandled(items, viewId, idFolders)
            }
            override fun onFailure(call: Call<List<GetDataItems>>, throwable: Throwable) {
                view.onFailedDataHandled(throwable)
            }
        })
    }

    /**
     * GET request to server (current user + content in the root folder)
     */
    fun requestUser(url: String, okHttpClient: OkHttpClient, viewId: Int) {

        val retrofitJson = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val serviceJson: HttpBinServiceUser = retrofitJson.create(
            HttpBinServiceUser::class.java)
        val callJson = serviceJson.getFolderContent()

        callJson.enqueue(object: Callback<GetUser>{
            override fun onResponse(call: Call<GetUser>, response: Response<GetUser>) {
                val getUser = response.body()
                // request the content in the root folder
                requestFolder(url, okHttpClient, viewId, getUser!!.rootItem.id)
            }
            override fun onFailure(call: Call<GetUser>, throwable: Throwable) {
                view.onFailedDataHandled(throwable)
            }
        })
    }
}