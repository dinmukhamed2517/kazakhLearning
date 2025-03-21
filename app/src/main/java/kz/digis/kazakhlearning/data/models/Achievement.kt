package kz.digis.kazakhlearning.data.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title:String,
    @DrawableRes
    val imageId:Int,
    val reachCount:Int,
    val isUnlocked:Boolean = false,
    val description: String,
)