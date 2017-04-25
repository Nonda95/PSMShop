package pl.edu.pwr.wiz.psmshop.view.custom

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.AttrRes
import android.support.annotation.LayoutRes
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

abstract class BaseCustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {
    val view: View by lazy { LayoutInflater.from(context).inflate(layoutResource, this, true) }

    /**
     * Method required to initialize the view

     * @return layout resource pointing to view
     */
    @get:LayoutRes
    protected abstract val layoutResource: Int

    public override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.childrenStates = SparseArray<Parcelable>()
        for (i in 0..childCount - 1) {
            getChildAt(i).saveHierarchyState(ss.childrenStates as SparseArray<Parcelable>)
        }
        return ss
    }


    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        for (i in 0..childCount - 1) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates as SparseArray<Parcelable>)
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    private class SavedState : View.BaseSavedState {
        internal var childrenStates: SparseArray<*>? = null

        internal constructor(superState: Parcelable) : super(superState)

        private constructor(`in`: Parcel, classLoader: ClassLoader?) : super(`in`) {
            childrenStates = `in`.readSparseArray(classLoader)
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSparseArray(childrenStates as SparseArray<Any>)
        }

        companion object {

            val CREATOR: Parcelable.ClassLoaderCreator<SavedState> = object : Parcelable.ClassLoaderCreator<SavedState> {
                override fun createFromParcel(source: Parcel, loader: ClassLoader?): SavedState {
                    return SavedState(source, loader)
                }

                override fun createFromParcel(source: Parcel): SavedState {
                    return createFromParcel(source, null)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
