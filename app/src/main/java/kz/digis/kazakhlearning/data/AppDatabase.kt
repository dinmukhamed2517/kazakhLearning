package kz.digis.kazakhlearning.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kz.digis.kazakhlearning.data.models.WordCard



@Database(entities = [WordCard::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordCardDao(): WordCardDao
}

