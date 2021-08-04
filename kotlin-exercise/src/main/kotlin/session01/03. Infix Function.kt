package session01

fun main() {
    infix fun Int.times(str: String) = str.repeat(this)
    println(2 times "Bye ")

    val pair = "ferrari" to "Katrina"
    println(pair)

    infix fun String.onto(other: String) = Pair(this, other)
    val myPair = "McLaren" onto "Lucas"
    println(myPair)

    val triplePair = "McLare" onto "Lucas" to "Katrina"
    println(triplePair)

    val sophia = Person("Sophia")
    val claudia = Person("Claudia")
    sophia likes claudia
    println(sophia)
}

class Person(val name: String) {
    val likedPeople = mutableListOf<Person>()
    infix fun likes(other: Person) {
        likedPeople.add(other)
    }

    override fun toString(): String {
        return "Person(name='$name', likedPeople=$likedPeople)"
    }


}