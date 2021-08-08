package session02

fun main() {
    val cakes = listOf("carrot", "cheese", "chocolate")
    for (cake in cakes) {
        println("Yummy, it's a $cake cake!")
    }

    fun eatACake() = println("Eat a Cake")
    fun bakeACake() = println("bake a Cake")

    var cakesEaten = 0
    var cakesBaked = 0

    while (cakesEaten < 5) {
        eatACake();
        cakesEaten++
    }

    do {
        bakeACake()
        cakesBaked++
    } while (cakesBaked < cakesEaten)

    class Animal(val name: String)

    class Zoo(val animals: List<Animal>) {
        operator fun iterator(): Iterator<Animal> {
            return animals.iterator()
        }
    }

    val zoo = Zoo(listOf(Animal("zebra"), Animal("lion")))

    for (animal in zoo) {
        println("Wath out. it is a ${animal.name}")
    }
}