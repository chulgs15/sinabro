package session01

class Contact

class Customer(val id:Int, var name:String)

fun main(args: Array<String>) {
    val contact = Contact()
    val customer = Customer(1, "test")

    customer.name = "Test"
    println("id = " + customer.id)
    println("name = " + customer.name)
}





