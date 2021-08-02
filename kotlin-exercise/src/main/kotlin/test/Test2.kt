package test

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    val text = "2022/07/16 09:42"
    val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
    val t = LocalDateTime.parse(text, pattern)

    println(t)
}