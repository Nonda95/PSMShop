package pl.edu.pwr.wiz.psmshop.presenter

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import pl.edu.pwr.wiz.psmshop.model.CarouselItemProvider
import pl.edu.pwr.wiz.psmshop.model.CategoriesProvider
import pl.edu.pwr.wiz.psmshop.view.MainView

class MainPresenter(private val categoriesProvider: CategoriesProvider = CategoriesProvider(), private val carouselItemProvider: CarouselItemProvider = CarouselItemProvider()) : BaseMVPPresenter<MainView> {
    var view: MainView? = null
    var task: Job? = null
    override fun attachView(v: MainView) {
        view = v
    }

    override fun detachView() {
        view = null
        task?.cancel()
    }

    fun getCarousels() {
        launch(UI) {
            run(CommonPool) {
                categoriesProvider.getCategories().map { (k, v) -> k to carouselItemProvider.getItems(v) }.toMap()
            }.let {
                view?.displayCarousels(it)
            }
        }
    }


}