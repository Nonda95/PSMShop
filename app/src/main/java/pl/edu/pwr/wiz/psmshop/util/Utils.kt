package pl.edu.pwr.wiz.psmshop.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import pl.edu.pwr.wiz.psmshop.R

fun ImageView.load(url: String, afterLoad: () -> Unit = {}): Unit {
    Glide.with(this)
            .load(url)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
            .apply(RequestOptions.errorOf(R.drawable.ic_error_outline_black_24dp))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    afterLoad()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    afterLoad()
                    return false
                }

            })
            .into(this)
}

fun Context.getWindowMetrics(): DisplayMetrics {
    val dm = DisplayMetrics()
    (this.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(dm)
    return dm
}