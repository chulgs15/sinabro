package session01

open class Tiger(val origin: String){
    fun sayHello() {
        println("A tiger from $origin says: grrhh!")
    }
}

class SiberianTiger : Tiger("Siberia")

fun main() {
    val tiger = SiberianTiger()
    tiger.sayHello()
}