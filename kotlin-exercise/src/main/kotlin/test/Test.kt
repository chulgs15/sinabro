package test

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//[MY COMPANY] 승인
//5503 신철규님
//07/16 09:42
//7,300원 일시불
//메가엠지커피호계지식산업센

fun main(args: Array<String>) {
    println("hello world")

    val text: String =
        """
        [MY COMPANY] 승인
        5503 신철규님
        07/16 09:42
        7,300원 일시불
        메가엠지커피호계지식산업센           
        """.trimIndent()

    val lines = text.lines()

    var user: String = ""
    var userList: List<String>
    var useDate: LocalDateTime = LocalDateTime.now()
    var amount: BigDecimal = BigDecimal("0")
    var vendor: String = ""

    val hangulPattern = "[가-힣]+".toRegex();
    val numberPattern = "[^0-9]$".toRegex();

    for (i in 1..lines.size - 1) {
        println(lines.get(i))
        when (i) {
            1 -> {
                user = hangulPattern.find(lines.get(i))!!.value
                    .replace("님", "")
            }
            2 -> {
                val pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
                useDate = LocalDateTime.parse((LocalDate.now().year.toString() + "/" + lines.get(i)), pattern)
            }
            3 -> {
                val filter = lines.get(i).filter { it.isDigit() }
                amount = BigDecimal(filter)
            }
            4 -> {
                vendor = lines.get(4)
            }
        }
    }

    println("user = $user")
    println("usedate = " + useDate)
    println("amount = " + amount)
    println("vendor = $vendor")
}


