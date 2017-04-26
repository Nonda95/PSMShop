package pl.edu.pwr.wiz.psmshop.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.ActivityOptionsCompat
import android.transition.Transition
import android.view.View
import com.wnafee.vector.MorphButton
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import pl.edu.pwr.wiz.psmshop.R
import pl.edu.pwr.wiz.psmshop.model.Album
import pl.edu.pwr.wiz.psmshop.model.CarouselItem
import pl.edu.pwr.wiz.psmshop.presenter.DetailsPresenter
import pl.edu.pwr.wiz.psmshop.util.load


const val ITEM_ID_KEY = "itemId"

class DetailsActivity : BaseMVPActivity(), DetailsView {
    override fun getLayoutRes(): Int = R.layout.activity_details

    private val presenter by lazy { DetailsPresenter() }
    private val bsBehavior by lazy { BottomSheetBehavior.from(bs_related) }

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
        rv_related_albums_carousel.setCarouselItemClickListener { id, view, animator ->
            showDetails(id, view, animator)
        }
    }

    fun showDetails(id: Int, view: View, animator: Animator?) {
        val intent = Intent(this, DetailsActivity::class.java).putExtra(ITEM_ID_KEY, id).putExtra("transitionName", view.transitionName)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, view.transitionName)
        animator?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) = Unit

            override fun onAnimationCancel(animation: Animator?) = Unit

            override fun onAnimationStart(animation: Animator?) = Unit

            override fun onAnimationEnd(animation: Animator?) {
                startActivity(intent, options.toBundle())
                animation?.removeListener(this)
            }
        })
        animator?.start()
        window.sharedElementReenterTransition = window.sharedElementReenterTransition.clone().addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                (animator as ObjectAnimator?)?.reverse()
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) = Unit

            override fun onTransitionPause(transition: Transition?) = Unit

            override fun onTransitionCancel(transition: Transition?) = Unit

            override fun onTransitionStart(transition: Transition?) = Unit

        })
    }

    override fun onBackPressed() {
        if (bsBehavior.state != BottomSheetBehavior.STATE_COLLAPSED) {
            bsBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            finishAfterTransition()
        }
    }
}
