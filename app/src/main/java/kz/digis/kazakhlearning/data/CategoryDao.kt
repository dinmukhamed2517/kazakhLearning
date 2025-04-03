package kz.digis.kazakhlearning.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kz.digis.kazakhlearning.data.models.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM chosen_categories")
    fun getAllChosenCategories(): LiveData<List<Category>>

    @Query("DELETE FROM chosen_categories")
    suspend fun clearAll()
}
