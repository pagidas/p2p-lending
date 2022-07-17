package org.example.quote.csv

import java.io.File
import java.io.IOException

fun getResources(fileName: String): File {
    val uri = object {}.javaClass.getResource(fileName)?.toURI()
        ?: throw IOException("Cannot find test.csv file with given path $fileName")
    return File(uri)
}

