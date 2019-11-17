package com.botmaker.parser

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.nio.file.Files
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors


@ExperimentalStdlibApi
@JsonIgnoreProperties(ignoreUnknown = true)
fun main() {
    val robotName = "Dylan Hillier"
    val appendedlist = readFiles(Paths.get("src/mockdata/input"), robotName)
    val json = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(appendedlist)
    Files.write(File("src/mockdata/data.json").toPath(), json.encodeToByteArray())
}

fun readFiles(basePath: Path, robotName: String): List<TrainingMessage> {
    val files = Files.list(basePath)
        .collect(Collectors.toList())
    println(files.toString())
        //.filter{it.endsWith(".json")}
    var appendedList = listOf<TrainingMessage>()
    files.forEach {
        val path = it
        if (it.toString().endsWith(".json")) {
            println(it.fileName)
            val originalMessages = Reader.extract(it.toAbsolutePath())
            if (originalMessages != null) {
                val message = Converter.convert(
                    originalMessages.messages,
                    robotName,
                    originalMessages.participants.filter { !(it.name == robotName) }[0].name
                )
                val cutmessage = message.drop(1).dropLast(1)
                appendedList = appendedList + cutmessage
            }
        }
        else if (Files.isDirectory(path))
        appendedList = appendedList + readFiles(path,robotName)
        else print(path.toString())
    }
    return appendedList
}