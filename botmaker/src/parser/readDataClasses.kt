package com.botmaker.parser

data class FileStruc(
    val participants: List<Names>,
    val messages: List<Message>,
    val title: String,
    val is_still_participant: Boolean,
    val thread_type: String,
    val thread_path: String
)

data class Names(val name: String)

data class Message(
    val sender_name: String,
    val timestamp_ms: Long,
    val content: String?,
    val photos: List<Any>?,
    val share: Any?,
    val call_duration: Int,
    val missed: Boolean,
    val gifs: Any?,
    val audio_files: Any?,
    val reactions: List<Any>?,
    val sticker: Any?,
    val videos: Any?,
    val files: Any?,
    val users: Any?,
    val plan: Any?,
    val type: String
)

//sealed class MessageElement(val sender_name: String,val timestamp_ms: Int, val content: String? = null, val photos: List<Any>? = null, val share: Any? = null, val type: String)
//
//data class StandardElement (val sender_name: String,val timestamp_ms: Int,val content: String,val type: String) : MessageElement(sender_name,timestamp_ms,content,type = type)
//
//data class PhotoElement (val sender_name: String, val timestamp_ms: Int, val photos: List<Any>, val type: String) : MessageElement()
//
//data class ShareElement (val sender_name: String,val timestamp_ms: Int, val content: String,val share: Any,val type: String): MessageElement()


