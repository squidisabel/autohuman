package com.botmaker.parser

object Converter {
    fun convert(originalMessages: List<Message>, robotName: String, humanName: String): List<TrainingMessage> {
        val filteredListOfMessages =
            originalMessages.filter { !it.content.isNullOrEmpty() }.map { Pair(it.sender_name, it.content!!) }
                .asReversed()
        return collapse(
            filteredListOfMessages,
            robotName,
            humanName
        )// Here we want to tuple the input and output together
    }

    fun collapse(inputList: List<Pair<String, String>>, robotName: String, humanName: String): List<TrainingMessage> {
        var robotString = ""
        var humanString = ""
        var prevName = robotName
        var formattedList = listOf<TrainingMessage>()
        inputList.forEach {
            //TODO Kill this loop
            if (prevName == robotName) {
                if (it.first == humanName) {
                    formattedList = formattedList.plus(TrainingMessage(humanString, robotString))
                    humanString = it.second
                    robotString = ""
                    prevName = humanName
                } else {
                    robotString = "$robotString ${it.second}"//TODO work on spacing
                }
            } else {
                if (it.first == robotName) {
                    robotString = it.second
                    prevName = robotName
                } else {
                    humanString = "$humanString ${it.second}"
                }
            }
        }
        return formattedList
    }
}