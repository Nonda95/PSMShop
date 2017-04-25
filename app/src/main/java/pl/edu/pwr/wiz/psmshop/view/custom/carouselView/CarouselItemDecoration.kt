package pl.edu.pwr.wiz.psmshop.view.custom.carouselView

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by osmalek on 23.04.2017.
 */

class CarouselItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter.itemCount > 1) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.left = offset
            }
            outRect.right = offset
        }
    }
}
