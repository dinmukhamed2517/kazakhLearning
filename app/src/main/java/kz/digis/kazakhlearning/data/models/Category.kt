package kz.digis.kazakhlearning.data.models

import androidx.annotation.DrawableRes

data class Category(
    val id:Int,
    @DrawableRes
    val imageId:Int,
    val categoryTitle:String
)