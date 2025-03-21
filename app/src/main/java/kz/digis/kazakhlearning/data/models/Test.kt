package kz.digis.kazakhlearning.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "tests")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val category: String,
    val questions:List<Question>,

)