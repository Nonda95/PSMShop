package pl.edu.pwr.wiz.psmshop.view.custom.carouselView

import android.content.Context

import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.carousel_view.view.*
import pl.edu.pwr.wiz.psmshop.R
import pl.edu.pwr.wiz.psmshop.model.CarouselItem
import pl.edu.pwr.wiz.psmshop.util.getWindowMetrics
import pl.edu.pwr.wiz.psmshop.view.custom.BaseCustomView
import pl.edu.pwr.wiz.psmshop.view.custom.HorizontalSnapHelper

typealias OnCarouselItemClickListener = (Int, View) -> Unit

class CarouselView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : BaseCustomView(context, attrs, defStyle) {

    private val adapter: CarouselAdapter

    override val layoutResource: Int
        get() = R.layout.carousel_view

    init {
        adapter = CarouselAdapter(widthWithoutPadding)
        with(view) {
            rv_carousel.adapter = adapter
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rv_carousel.layoutManager = layoutManager
            rv_carousel.addItemDecoration(CarouselItemDecoration(resources.getDimensionPixelSize(R.dimen.margin_small)))
            val snapHelper = HorizontalSnapHelper(layoutManager, resources.getDimensionPixelSize(R.dimen.margin_small))
            snapHelper.attachToRecyclerView(rv_carousel)
        }
    }

    fun setCarouselTitle(title: String) {
        tv_title.text = title
    }

    fun setCarouselItems(items: List<CarouselItem>) {
        if (items.isEmpty()) {
            showNoItemsText()
        } else {
            hideNoItemsText()
        }
        adapter.setCarouselItems(items)
    }

    fun setCarouselItemClickListener(carouselItemClickListener: OnCarouselItemClickListener) {
        adapter.setCarouselItemClickListener(carouselItemClickListener)
    }

    private fun hideNoItemsText() {
        tv_no_items.visibility = GONE
    }

    private fun showNoItemsText() {
        tv_no_items.visibility = VISIBLE
    }

    private val widthWithoutPadding: Int
        get() = context.getWindowMetrics().widthPixels - resources.getDimensionPixelSize(R.dimen.margin_small) * 2

    fun setTitlePadding(size: Int) {
        tv_title.setPadding(size, size, size, size)
    }
}

