package pl.edu.pwr.wiz.psmshop.presenter

interface BaseMVPPresenter<in T> {
    fun attachView(v : T)
    fun detachView()
}
