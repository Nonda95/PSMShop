package pl.edu.pwr.wiz.psmshop.model

/**
 * Created by osmalek on 23.04.2017.
 */
class CarouselItemProvider {
    fun getAllItems(): List<CarouselItem> {
        val carouselItems = mutableListOf<CarouselItem>()
        Database.names.mapTo(carouselItems) {(k, v) -> CarouselItem(k, v, Database.covers[k]!!) }
        return carouselItems
    }
    fun getItems(ids: List<Int>): List<CarouselItem> {
        val carouselItems = mutableListOf<CarouselItem>()
        Database.names.filter { (k, _) -> ids.contains(k) }.mapTo(carouselItems) {(k, v) -> CarouselItem(k, v, Database.covers[k]!!) }
        return carouselItems
    }
}