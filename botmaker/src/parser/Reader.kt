package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.nio.file.Path

object Reader {

    fun extract(path: Path): FileStruc? {
//        var file = readFile(fileName)
//        file.replace("\"", "\\\"")
        //var file = readFile(fileName).replace("\"\"", "\"")

        var struc = mapToObject(readFile(path))
        if (struc?.participants?.size != 2) struc = null
        return struc
    }

    private fun readFile(fileName: Path): String {
        return fileName.toFile().readText()
    }

    private fun mapToObject(json: String): FileStruc? {
        val mapper = jacksonObjectMapper()
        return try {
            mapper.readValue(json)
        }
        catch (e: Exception){
            println(e.localizedMessage)
            null
        }
    }
}