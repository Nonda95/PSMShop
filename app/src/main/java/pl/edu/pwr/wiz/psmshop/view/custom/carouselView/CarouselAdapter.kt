package pl.edu.pwr.wiz.psmshop.view.custom.carouselView

import android.animation.ObjectAnimator
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.carousel_item.view.*
import pl.edu.pwr.wiz.psmshop.R
import pl.edu.pwr.wiz.psmshop.model.CarouselItem
import pl.edu.pwr.wiz.psmshop.util.load
import pl.edu.pwr.wiz.psmshop.view.custom.BaseViewHolder
import java.util.*

const val ITEM_WIDTH_PERCENT = 0.4

class CarouselAdapter(private val containerWidth: Int) : RecyclerView.Adapter<BaseViewHolder<CarouselItem>>() {
    private var carouselItems: List<CarouselItem>? = null

    private var carouselItemClickListener: OnCarouselItemClickListener? = null

    init {
        carouselItems = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CarouselItem> {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.carousel_item, parent, false)
        val width = calculateWidth()
        return CarouselViewHolder(itemView, width)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CarouselItem>, position: Int) {
        holder.bind(carouselItems!![position])
    }

    override fun getItemCount(): Int = carouselItems?.size ?: 0

    private fun calculateWidth(): Int {
        return (containerWidth * ITEM_WIDTH_PERCENT).toInt()
    }

    fun setCarouselItems(carouselItems: List<CarouselItem>?) {
        this.carouselItems = carouselItems
        notifyDataSetChanged()
    }

    fun setCarouselItemClickListener(carouselItemClickListener: OnCarouselItemClickListener?) {
        this.carouselItemClickListener = carouselItemClickListener
    }

    internal inner class CarouselViewHolder(itemView: View, width: Int) : BaseViewHolder<CarouselItem>(itemView) {

        private var itemId: Int = 0

        init {
            setWidth(itemView, width)
        }

        private fun setWidth(itemView: View, width: Int) {
            val layoutParams = itemView.layoutParams
            layoutParams.width = width
            itemView.layoutParams = layoutParams
        }

        override fun bind(item: CarouselItem) {
            itemId = item.id
            with(itemView) {
                tv_carousel_item_title.text = item.name
                iv_carousel_item_photo.load(item.coverLink) {
                    //                    transition(DrawableTransitionOptions.withCrossFade())
                }
                container_carousel_item.setOnClickListener {
                    val transitionName = UUID.randomUUID().toString()
                    iv_carousel_item_photo.transitionName = transitionName

                    carouselItemClickListener?.invoke(itemId, iv_carousel_item_photo, ObjectAnimator.ofInt(tv_carousel_item_title, "height", 0))
                }
            }
        }
    }
}
