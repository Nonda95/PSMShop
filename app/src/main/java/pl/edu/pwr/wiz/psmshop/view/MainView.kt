package pl.edu.pwr.wiz.psmshop.view

import pl.edu.pwr.wiz.psmshop.model.CarouselItem

/**
 * Created by osmalek on 15.04.2017.
 */
interface MainView {
    fun displayCarousels(carouselsData: Map<String ,List<CarouselItem>>)
}

