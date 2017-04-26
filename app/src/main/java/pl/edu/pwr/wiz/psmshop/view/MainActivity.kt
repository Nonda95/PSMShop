package pl.edu.pwr.wiz.psmshop.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.transition.Transition
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import pl.edu.pwr.wiz.psmshop.R
import pl.edu.pwr.wiz.psmshop.model.CarouselItem
import pl.edu.pwr.wiz.psmshop.presenter.MainPresenter
import pl.edu.pwr.wiz.psmshop.view.custom.carouselView.CarouselView

class MainActivity : BaseMVPActivity(), MainView {
    override fun getLayoutRes(): Int = R.layout.activity_main

    val presenter: MainPresenter by lazy { MainPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        presenter.attachView(this)
        presenter.getCarousels()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun displayCarousels(carouselsData: Map<String, List<CarouselItem>>) {
        carouselsData.forEach { t, u ->
            val carouselView = CarouselView(this)
            home_container.addView(carouselView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
            carouselView.apply {
                val params = layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(0, resources.getDimensionPixelSize(R.dimen.margin_small), 0, 0)
                layoutParams = params
                setCarouselTitle(t)
                setCarouselItems(u)
                setCarouselItemClickListener { id, view, animator ->
                    showDetails(id, view, animator)
                }
            }
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
}

