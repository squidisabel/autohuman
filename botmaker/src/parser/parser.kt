package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Files

@ExperimentalStdlibApi
fun main() {
    val originalMessages = Reader.extract("/root/IdeaProjects/chatbot/botmaker/src/mockdata/mockdata.json")
    val message = Converter.convert(originalMessages, "Kerry Xu", "Dylan Hillier")
    val cutmessage = message.drop(1).dropLast(1)
    val json = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cutmessage)
    Files.write(File("/root/IdeaProjects/chatbot/botmaker/src/mockdata/data.json").toPath(), json.encodeToByteArray())
}