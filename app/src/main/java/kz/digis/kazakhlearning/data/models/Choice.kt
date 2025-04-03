package kz.digis.kazakhlearning.data.models

data class Choice(
    val index: Int,
    val message: Message
)

data class Message(
    val role: String,
    val content: String
)
