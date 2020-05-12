package androidkotlin.kev.appoodrive.detailFolder

import androidkotlin.kev.appoodrive.Item
import androidkotlin.kev.appoodrive.network.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface IModel {
    fun setPresenter(presenter: IPresenter)

    fun authentication(user: String, password: String): OkHttpClient
    fun requestUser(url: String, okHttpClient: OkHttpClient)
    fun requestFolder(url: String, okHttpClient: OkHttpClient, idFolder: String)
}

class RequestServerModel : IModel {

    private var presenter: IPresenter? = null

    override fun setPresenter(presenter: IPresenter) {
        this.presenter = presenter
    }

    /**
     * HTTP Basic Authentication before all API request
     */
    override fun authentication(user: String, password: String): OkHttpClient =
        OkHttpClient().newBuilder().addInterceptor(AuthenticationInterceptor(user, password)).build()

    /**
     * GET request to server (content in the folder)
     */
    override fun requestFolder(url: String, okHttpClient: OkHttpClient, idFolder: String) {
        val retrofitJson = createRetrofit(okHttpClient, url)
        val serviceJson: HttpBinServiceFolder = retrofitJson.create(HttpBinServiceFolder::class.java)
        val callJson = serviceJson.getFolderContent(idFolder)

        callJson.enqueue(object : Callback<List<androidkotlin.kev.appoodrive.network.Item>> {
            override fun onResponse(call: Call<List<androidkotlin.kev.appoodrive.network.Item>>?,
                                    response: Response<List<androidkotlin.kev.appoodrive.network.Item>>) {
                val getDataItems = response.body()
                val items = mutableListOf<Item>()
                val idFolders = mutableListOf<String>()
                for (i in 0 until getDataItems?.size!!) {
                    items.add(Item(getDataItems[i].name))
                    idFolders.add(getDataItems[i].id)
                }

                presenter?.onSuccessfulDataHandled(items, idFolders)
            }

            override fun onFailure(call: Call<List<androidkotlin.kev.appoodrive.network.Item>>, throwable: Throwable) {
                presenter?.onFailedDataHandled(throwable)
            }
        })
    }

    /**
     * GET request to server (current user + content in the root folder)
     */
    override fun requestUser(url: String, okHttpClient: OkHttpClient) {
        val retrofitJson = createRetrofit(okHttpClient, url)
        val serviceJson: HttpBinServiceUser = retrofitJson.create(HttpBinServiceUser::class.java)
        val callJson = serviceJson.getCurrentUser()

        callJson.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val getUser = response.body()
                requestFolder(url, okHttpClient, getUser!!.rootItem.id)
            }

            override fun onFailure(call: Call<User>, throwable: Throwable) {
                presenter?.onFailedDataHandled(throwable)
            }
        })
    }

    private fun createRetrofit(okHttpClient: OkHttpClient, url: String) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}