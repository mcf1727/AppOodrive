package androidkotlin.kev.appoodrive.detailFolder

import androidkotlin.kev.appoodrive.Item

interface IView {
    fun setPresenter(presenter: IPresenter)

    fun showSuccessfulData(items: List<Item>, idFolders: List<String>)
    fun showFailedData(throwable: Throwable)
}