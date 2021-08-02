package session01

fun main(args: Array<String>) {
    println("Hello")
    printMessageWithPrefix(message = "hello world")
    printMessageWithPrefix(message = "hello world", prefix = "it is ")
    val result = sum (1, 2)
    println("result is $result")
    println(multiply(2, 4))
}

fun printMessage(message: String): Unit {
    println(message)
}

fun printMessageWithPrefix(message: String, prefix: String = "Info") {
    println("$prefix $message")
}

fun sum(x: Int, y: Int):Int {
    return x + y
}

fun multiply(x: Int, y: Int) = x * y

