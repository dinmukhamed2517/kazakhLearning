package kz.digis.kazakhlearning.data.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chosen_categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @DrawableRes val imageId: Int,
    val categoryTitle: String
)