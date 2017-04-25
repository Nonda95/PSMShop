package pl.edu.pwr.wiz.psmshop.model

/**
 * Created by osmalek on 23.04.2017.
 */
class CategoriesProvider {

    fun getCategories(): Map<String, List<Int>> {
        return Database.categories
    }

    fun getCategoryElements(category: String): List<Int> {
        return Database.categories[category]!!
    }
}