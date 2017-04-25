package pl.edu.pwr.wiz.psmshop.presenter

import android.util.Log.wtf
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import pl.edu.pwr.wiz.psmshop.model.Album
import pl.edu.pwr.wiz.psmshop.model.CarouselItemProvider
import pl.edu.pwr.wiz.psmshop.model.CategoriesProvider
import pl.edu.pwr.wiz.psmshop.model.DetailsProvider
import pl.edu.pwr.wiz.psmshop.view.DetailsView

/**
 * Created by osmalek on 23.04.2017.
 */
class DetailsPresenter(private val detailsProvider: DetailsProvider = DetailsProvider(), private val categoriesProvider: CategoriesProvider = CategoriesProvider(), private val carouselItemProvider: CarouselItemProvider = CarouselItemProvider()) : BaseMVPPresenter<DetailsView> {
    var view: DetailsView? = null
    var album : Album? = null
    var task: Job? = null
    override fun attachView(v: DetailsView) {
        view = v
    }

    override fun detachView() {
        view = null
    }

    fun getAlbum(albumId: Int) {
        task = launch(UI) {
            run(CommonPool) {
                album ?: detailsProvider.getDetail(albumId)
            }.let {
                album = it
                view?.showAlbum(it)
            }
        }
    }

    fun getRelatedItems() {
        launch(UI) {
            task?.join()
            run(CommonPool) {
                album?.category?.flatMap {
                    carouselItemProvider.getItems(categoriesProvider.getCategoryElements(it))
                }?.filter { album?.id != it.id }?.distinct()
            }?.let {
                view?.showRelatedAlbums(it)
            }
        }
    }

    fun onCarouselItemClick(id: Int) {
        view?.showDetails(id)
    }

    fun onPlayPauseClick(isPlaying: Boolean) {
        wtf("isPlaying: ", isPlaying.toString())
    }
}

