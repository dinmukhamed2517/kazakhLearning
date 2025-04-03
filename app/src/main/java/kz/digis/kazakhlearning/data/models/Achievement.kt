package kz.digis.kazakhlearning.data.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    @DrawableRes val imageId: Int = 0,
    val reachCount: Int = 0,
    val isUnlocked: Boolean = false,
    val description: String = ""
)
