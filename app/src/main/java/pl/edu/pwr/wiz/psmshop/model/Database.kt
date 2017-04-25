package pl.edu.pwr.wiz.psmshop.model

import pl.edu.pwr.wiz.psmshop.R

class Database {
    companion object {
        val previews = mapOf(
                1 to R.raw.folder86bpm,
                2 to R.raw.alternatywy816bpm,
                3 to R.raw.beztempagamon,
                4 to R.raw.digitriggi80bpm,
                5 to R.raw.kajtek93bpm,
                6 to R.raw.miodpazdzier98bpm,
                7 to R.raw.nietoperek88bpm,
                8 to R.raw.nowa75bpm,
                9 to R.raw.pro88bpm,
                10 to R.raw.thecrusaders77bpm,
                11 to R.raw.wartent85bpm,
                12 to R.raw.zelipapastyl80bpm)
        val covers = mapOf(
                1 to "https://unsplash.it/1920/1080/?image=10",
                2 to "https://unsplash.it/1920/1080/?image=11",
                3 to "https://unsplash.it/1920/1080/?image=12",
                4 to "https://unsplash.it/1920/1080/?image=13",
                5 to "https://unsplash.it/1920/1080/?image=14",
                6 to "https://unsplash.it/1920/1080/?image=15",
                7 to "https://unsplash.it/1920/1080/?image=16",
                8 to "https://unsplash.it/1920/1080/?image=17",
                9 to "https://unsplash.it/1920/1080/?image=18",
                10 to "https://unsplash.it/1920/1080/?image=19",
                11 to "https://unsplash.it/1920/1080/?image=20",
                12 to "https://unsplash.it/1920/1080/?image=21")
        val names = mapOf(
                1 to "30 minut z życia",
                2 to "Humanz",
                3 to "The Fall",
                4 to "G-Sides",
                5 to "Wilczy humor",
                6 to "Dzieła Organowe",
                7 to "Requiem",
                8 to "Marmur",
                9 to "25",
                10 to "Lemonade",
                11 to "Fallen Angels",
                12 to "Hardwired")
        val artists = mapOf(
                1 to "O.S.T.R",
                2 to "Gorillaz",
                3 to "Gorillaz",
                4 to "Gorillaz",
                5 to "Bisz/Radex",
                6 to "J. S. Bach",
                7 to "W. A. Mozart",
                8 to "Taco Hemingway",
                9 to "Adele",
                10 to "Beyoncé",
                11 to "Bob Dylan",
                12 to "Metallica")
        val categories = mapOf(
                "Pop" to listOf(9, 10, 11),
                "Hip-hop" to listOf(1, 5, 8),
                "Rock" to listOf(2, 3, 4, 12),
                "Classical" to listOf(6, 7)
        )
    }
}
