package kz.digis.kazakhlearning.data.models

data class SentenceCategory(
    val name: String,
    val sentences: List<Sentence>
)

data class Sentence(
    val id: Int,
    val correctWords: List<String>
)
