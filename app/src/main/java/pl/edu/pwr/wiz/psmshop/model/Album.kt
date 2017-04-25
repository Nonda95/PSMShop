package pl.edu.pwr.wiz.psmshop.model

import android.support.annotation.RawRes

/**
 * Created by osmalek on 23.04.2017.
 */
data class Album(val id: Int,
                 val name: String,
                 val artist: String,
                 val coverLink: String,
                 val category: Set<String>,
                 @RawRes val preview: Int)