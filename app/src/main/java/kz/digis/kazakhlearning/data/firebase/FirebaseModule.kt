package kz.digis.kazakhlearning.data.firebase

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.digis.kazakhlearning.data.AppDatabase
import kz.digis.kazakhlearning.data.CategoryDao
import kz.digis.kazakhlearning.data.WordCardDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {

    @Provides
    fun provideFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

//    @Provides
//    fun provideUserDao(firebaseAuth: FirebaseAuth): UserDao {
//        return UserDao(firebaseAuth)
//    }

    @Provides
    fun provideStorageRef(firebaseAuth: FirebaseAuth): StorageReference {
        return FirebaseStorage.getInstance()
            .getReference("Users/" + "${firebaseAuth.currentUser?.uid}")
    }
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    @Provides
    fun provideUserDao(firebaseAuth: FirebaseAuth):UserDao{
        return UserDao(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "word_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideWordCardDao(database: AppDatabase): WordCardDao {
        return database.wordCardDao()
    }
    @Provides
    fun provideCategoryDao(database:AppDatabase):CategoryDao{
        return database.categoryDao()
    }

}