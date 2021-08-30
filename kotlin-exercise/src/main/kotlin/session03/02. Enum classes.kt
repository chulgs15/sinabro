package main.kotlin.session03

enum class Color(val rgb: Int) {

    RED(0xFF0000) {
        override fun getStringName() = "RED"
    },
    GREEN(0x00FF00) {
        override fun getStringName(): String = "GREEN"
    };

    abstract fun getStringName(): String
}