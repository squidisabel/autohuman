package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Files
import com.fasterxml.jackson.annotation.JsonIgnoreProperties



@ExperimentalStdlibApi
@JsonIgnoreProperties(ignoreUnknown = true)
fun main() {
    val robotName = "Kerry Xu"
    val appendedlist = readFiles("/Users/lukyxu/Documents/Oxford Hack/mlchatbot/botmaker/src/mockdata/input",robotName)
    val json = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(appendedlist)
    Files.write(File("/Users/lukyxu/Documents/Oxford Hack/mlchatbot/botmaker/src/mockdata/data.json").toPath(), json.encodeToByteArray())
}

fun readFiles(basePath: String, robotName: String): List<TrainingMessage> {
    val files = File(basePath).listFiles()
    var appendedList = listOf<TrainingMessage>()
    files?.forEach{
        val originalMessages = Reader.extract(it.absolutePath)
        val message = Converter.convert(originalMessages.messages, robotName, originalMessages.participants.filter { !(it.name == "Kerry Xu") }[0].name)
        val cutmessage = message.drop(1).dropLast(1)
        appendedList = appendedList + cutmessage
    }
    return appendedList
}