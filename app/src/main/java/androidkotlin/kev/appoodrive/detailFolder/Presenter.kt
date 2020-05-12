package androidkotlin.kev.appoodrive.detailFolder

import androidkotlin.kev.appoodrive.Item
import okhttp3.OkHttpClient

interface IPresenter {
    fun setView(view: IView)
    fun setModel(model: IModel)

    fun authenticationRequestSent(user: String, password: String): OkHttpClient?
    fun requestUserSent(url: String, okHttpClient: OkHttpClient)
    fun requestFolderSent(url: String, okHttpClient: OkHttpClient, idFolder: String)

    fun onSuccessfulDataHandled(items: List<Item>, idFolders: List<String>)
    fun onFailedDataHandled(throwable: Throwable)
}

class Presenter: IPresenter {

    private var view: IView? = null
    private var model: IModel? = null

    override fun setView(view: IView) {
        this.view = view
    }
    override fun setModel(model: IModel) {
        this.model = model
    }


    override fun authenticationRequestSent(user: String, password: String): OkHttpClient? =
        model?.authentication(user, password)

    override fun requestUserSent(url: String, okHttpClient: OkHttpClient) {
        model?.requestUser(url, okHttpClient)
    }
    override fun requestFolderSent(url: String, okHttpClient: OkHttpClient, idFolder: String) {
        model?.requestFolder(url, okHttpClient, idFolder)
    }

    override fun onSuccessfulDataHandled(items: List<Item>, idFolders: List<String>) {
        view?.showSuccessfulData(items, idFolders)
    }
    override fun onFailedDataHandled(throwable: Throwable) {
        view?.showFailedData(throwable)
    }
}