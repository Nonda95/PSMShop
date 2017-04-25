package pl.edu.pwr.wiz.psmshop.view.custom

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View

class HorizontalSnapHelper(layoutManager: RecyclerView.LayoutManager, val offset: Int) : LinearSnapHelper() {
    private val horizontalHelper: OrientationHelper = OrientationHelper.createHorizontalHelper(layoutManager)

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val coordinates = IntArray(2)
        coordinates[0] = horizontalHelper.getDecoratedStart(targetView) - offset - horizontalHelper.startAfterPadding
        return coordinates
    }


    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager is LinearLayoutManager) {
            return findView(layoutManager)
        }
        return super.findSnapView(layoutManager)
    }

    private fun findView(linearLayoutManager: LinearLayoutManager): View? {
        val firstVisible = linearLayoutManager.findFirstVisibleItemPosition()
        if (firstVisible == RecyclerView.NO_POSITION) {
            return null
        }
        val child = linearLayoutManager.findViewByPosition(firstVisible)
        val visibilityPercent = horizontalHelper.getDecoratedEnd(child) / horizontalHelper.getDecoratedMeasurement(child).toDouble()
        val end = linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.itemCount - 1
        if (visibilityPercent > 0.5 && !end) {
            return child
        }
        if(end) {
            return null
        }
        return linearLayoutManager.findViewByPosition(linearLayoutManager.findFirstCompletelyVisibleItemPosition())
    }
}
