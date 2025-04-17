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
    var isKnown: Boolean = false,
    @DrawableRes
    val image:Int? = null
)


data class WordCardFirebase(
    val kazakhWord: String = "",
    val translation: String = "",
    val description: String = "",
    val audioUrl: String? = "",
    val category: String = "",
    val isKnown: Boolean = false,
    val imageUrl: String? = null
)


fun WordCard.toFirebaseModel(imageUrl: String? = null): WordCardFirebase {
    return WordCardFirebase(
        kazakhWord = this.kazakhWord,
        translation = this.translation,
        description = this.description,
        audioUrl = this.audioUrl,
        category = this.category,
        isKnown = this.isKnown,
        imageUrl = imageUrl
    )
}
