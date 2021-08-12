package session02

fun main() {
    val authors = setOf("A", "B", "C")
    val writers = setOf("C", "A", "B")


    println(authors == writers)
    println(authors === writers)

}