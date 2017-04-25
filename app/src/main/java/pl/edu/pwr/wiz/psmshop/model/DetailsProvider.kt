package pl.edu.pwr.wiz.psmshop.model

/**
 * Created by osmalek on 23.04.2017.
 */
class DetailsProvider {
    fun getDetail(id: Int) = Album(id,
            Database.names[id]!!,
            Database.artists[id]!!,
            Database.covers[id]!!,
            Database.categories.filter { (_, v) -> v.contains(id) }.keys,
            Database.previews[id]!!)
}