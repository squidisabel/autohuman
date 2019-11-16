package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Files

@ExperimentalStdlibApi
fun main() {
    val originalMessages = Reader.extract("/home/hillierd/OxHack/mlchatbot/botmaker/src/mockdata/maxdylan.json")
    val message = Converter.convert(originalMessages, "Dylan Hillier", "Maximilien Tirard")
    val cutmessage = message.drop(1).dropLast(1)
    val json = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cutmessage)
    Files.write(File("/home/hillierd/OxHack/mlchatbot/botmaker/src/mockdata/data.json").toPath(), json.encodeToByteArray())
}