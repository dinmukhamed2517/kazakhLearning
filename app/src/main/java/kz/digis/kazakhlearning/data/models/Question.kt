package kz.digis.kazakhlearning.data.models

import androidx.annotation.DrawableRes


data class Question(
    val id:Int,
    val title: String,
    val variants:List<String>,
    val correctAnswer:String,
    val audioUrl:String? = null,
    @DrawableRes
    val imageId:Int? = null,
)