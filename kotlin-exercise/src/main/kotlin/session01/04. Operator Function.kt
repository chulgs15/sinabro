package session01

fun main(args: Array<String>) {
    operator fun Int.times(str: String) = str.repeat(this)
    println(2 * "Bye ")

    operator fun String.get(range: IntRange) = substring(range)
    val str = "너의 적을 용서해라. 괴롭히는 것은 문제가 있다."
    println(str[0..4])
}

