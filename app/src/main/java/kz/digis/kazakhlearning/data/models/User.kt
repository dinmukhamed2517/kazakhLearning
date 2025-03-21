package kz.digis.kazakhlearning.data.models


data class User(
    var name: String? = null,
    var lastname: String?= null,
    var pictureUrl: String? = null,
    var dailyTime:Int? = null,
    var isAdmin:Boolean = false,
    var achievements:List<Achievement>? = emptyList()
//    var favorites: Map<String, Product> = emptyMap(),
//    var favoritesRent: Map<String, Product> = emptyMap(),
)