package session01

fun main(args: Array<String>) {
    fun printAll(vararg messages: String) {
        for (m in messages) println(m)
    }

    printAll("hello", "world ")

    fun printAllWithPrefix(vararg messages: String, prefix: String) {
        for (m in messages) println(prefix + m)
    }

    printAllWithPrefix("hello", "world", prefix = "messsage is ")

    fun log(vararg entries: String) {
        printAll(*entries)
    }

    log("hello", "world")
}





