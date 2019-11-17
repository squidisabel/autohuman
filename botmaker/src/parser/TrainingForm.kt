package com.botmaker.parser

data class TrainingForm(val messages: List<TrainingMessage>)

data class TrainingMessage(val input: String, val response: String)