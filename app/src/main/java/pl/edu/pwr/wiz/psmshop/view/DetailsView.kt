package pl.edu.pwr.wiz.psmshop.view

import pl.edu.pwr.wiz.psmshop.model.Album
import pl.edu.pwr.wiz.psmshop.model.CarouselItem

interface DetailsView {
    fun showAlbum(album: Album)
    fun showRelatedAlbums(list: List<CarouselItem>)
    fun showDetails(id: Int)

}