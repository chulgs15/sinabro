package org.example.test

import org.apache.commons.io.FilenameUtils

fun main(args: Array<String>) {
    val filename = "c:/lang/../project.txt"
    val normailzed = FilenameUtils.normalize(filename)
    println(normailzed)
}

