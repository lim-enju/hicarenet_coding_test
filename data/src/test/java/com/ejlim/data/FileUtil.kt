package com.ejlim.data

import java.io.InputStreamReader

object FileUtil {
    fun readFileResource(filename: String):String {
        val inputStream = FileUtil::class.java.getResourceAsStream(filename)
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}