package main.kotlin.session03

data class User(val name: String, val id: Int) {
    override fun equals(other: Any?): Boolean {
        return other is User && other.id == this.id
    }
}

fun main(args: Array<String>) {
    val user = User("Alex", 1)
    println(user)

    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)

    println("user == secondUser : ${user == secondUser}")
    println("user == thirdUser : ${user == thirdUser}")

    // hascode function
    // 와 대박...원래 java도 이런가?
    println(user.hashCode())
    println(secondUser.hashCode())
    println(thirdUser.hashCode())


    // copy() function
    println(user.copy())
    println(user === user.copy())
    println(user.copy("Max"))
    println(user.copy(id = 3))

    mutableListOf(1, 2)

    println("name = ${user.component1()}")
    println("id = ${user.component2()}")



}