package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Files

@ExperimentalStdlibApi
fun main() {
    val robotName = "Dylan Hillier"
    val appendedlist = readFiles("/home/hillierd/OxHack/mlchatbot/botmaker/src/mockdata/input",robotName)
    val json = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(appendedlist)
    Files.write(File("/home/hillierd/OxHack/mlchatbot/botmaker/src/mockdata/data.json").toPath(), json.encodeToByteArray())
}

fun readFiles(basePath: String, robotName: String): List<TrainingMessage> {
    val files = File(basePath).listFiles()
    var appendedList = listOf<TrainingMessage>()
    files?.forEach{
        val originalMessages = Reader.extract(it.absolutePath)
        val message = Converter.convert(originalMessages.messages, robotName, originalMessages.participants.filter { !(it.name == "Dylan Hillier") }[0].name)
        val cutmessage = message.drop(1).dropLast(1)
        appendedList = appendedList + cutmessage
    }
    return appendedList
}