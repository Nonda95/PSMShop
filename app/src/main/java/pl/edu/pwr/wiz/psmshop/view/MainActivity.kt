package pl.edu.pwr.wiz.psmshop.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
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

    private var transitionView: View? = null

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
                setCarouselItemClickListener { id, view ->
                    transitionView = view
                    presenter.onCarouselItemClick(id)
                }
            }
        }
    }

    override fun showDetails(id: Int) {
        val intent = Intent(this, DetailsActivity::class.java).putExtra(ITEM_ID_KEY, id).putExtra("transitionName", transitionView?.transitionName)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitionView, transitionView?.transitionName)
        transitionView = null
        startActivity(intent, options.toBundle())
    }
}

