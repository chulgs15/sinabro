package main.kotlin.session03

sealed class Mammel(val name: String)

class Cat(val catName: String) : Mammel(catName)
class Human(val humanName: String) : Mammel(humanName)

fun greetMammal(mammel: Mammel): String {
    when (mammel){
        is Human -> return "Hello ${mammel.name}"
        is Cat -> return "Hello ${mammel.name}"
    }
}

fun main() {
    println(greetMammal(Cat("Snowy")))
}