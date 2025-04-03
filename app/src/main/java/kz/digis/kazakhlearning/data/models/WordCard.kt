package kz.digis.kazakhlearning.data.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_cards")
data class WordCard(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val kazakhWord: String,
    val translation: String,
    val description: String = "",
    val audioUrl: String? = "",
    val category: String,
    val isKnown: Boolean = false,
    @DrawableRes
    val image:Int? = null
)
