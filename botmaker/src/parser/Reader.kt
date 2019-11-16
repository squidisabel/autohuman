package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

object Reader {

    fun extract(fileName: String): FileStruc {
        return mapToObject(readFile(fileName))
    }

    private fun readFile(fileName: String): String {
        return File(fileName).readText()
    }

    private fun mapToObject(json: String): FileStruc {
        val mapper = jacksonObjectMapper()
        return mapper.readValue(json)
    }
}