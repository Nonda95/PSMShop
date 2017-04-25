package pl.edu.pwr.wiz.psmshop.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityOptionsCompat
import com.wnafee.vector.MorphButton
import kotlinx.android.synthetic.main.activity_details.*
import pl.edu.pwr.wiz.psmshop.R
import pl.edu.pwr.wiz.psmshop.model.Album
import pl.edu.pwr.wiz.psmshop.model.CarouselItem
import pl.edu.pwr.wiz.psmshop.presenter.DetailsPresenter
import pl.edu.pwr.wiz.psmshop.util.load
import android.support.v4.view.ViewCompat.getTransitionName
import android.view.View
import kotlinx.android.synthetic.main.content_details.*


const val ITEM_ID_KEY = "itemId"

class DetailsActivity : BaseMVPActivity(), DetailsView {
    override fun getLayoutRes(): Int = R.layout.activity_details

    private val presenter by lazy { DetailsPresenter() }
    private val bsBehavior by lazy { BottomSheetBehavior.from(bs_related) }

    private var transitionView: View? = null

    val albumId by lazy { intent.getIntExtra(ITEM_ID_KEY, 0) }

    private val transitionName: String by lazy { intent.getStringExtra("transitionName") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportPostponeEnterTransition()
        presenter.attachView(this)
        presenter.getAlbum(albumId)
        presenter.getRelatedItems()
        iv_cover.transitionName = transitionName
        but_play_pause.setOnStateChangedListener { changedTo, _ -> presenter.onPlayPauseClick(changedTo == MorphButton.MorphState.END) }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showAlbum(album: Album) {
        tv_name.text = album.name
        tv_artist.text = album.artist
        iv_cover.load(album.coverLink) {
            supportStartPostponedEnterTransition()
        }
    }

    override fun showRelatedAlbums(list: List<CarouselItem>) {
        rv_related_albums_carousel.setCarouselItems(list)
        rv_related_albums_carousel.setCarouselTitle("Powiązane")
        rv_related_albums_carousel.setTitlePadding(resources.getDimensionPixelSize(R.dimen.padding_normal))
        rv_related_albums_carousel.setCarouselItemClickListener { id, view ->
            transitionView = view
            presenter.onCarouselItemClick(id)
        }
    }

    override fun showDetails(id: Int) {
        val intent = Intent(this, DetailsActivity::class.java).putExtra(ITEM_ID_KEY, id).putExtra("transitionName", transitionView?.transitionName)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitionView, transitionView?.transitionName)
        transitionView = null
        startActivity(intent, options.toBundle())
    }

    override fun onBackPressed() {
        if(bsBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            finishAfterTransition()
        }
    }
}
