package session01

fun main() {
    val mutableStack = MutableStack(0.62, 3.14, 2.7, 9.87)
    println(mutableStack.peek())

    println(mutableStack.size())
}

class MutableStack<E>(vararg items: E) {
    private val elements = items.toMutableList()

    fun push(element: E) = elements.add(element)

    fun peek(): E = elements.last()

    fun pop(): E = elements.removeAt(elements.size - 1)

    fun isEmpty() = elements.isEmpty()

    fun size() = elements.size

    override fun toString(): String = "MutableStack(${elements.joinToString()})"
}




