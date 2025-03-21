package kz.digis.kazakhlearning.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.digis.kazakhlearning.data.models.Achievement
import kz.digis.kazakhlearning.data.models.WordCard

@Dao
interface AchievementsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: Achievement)

    @Query("UPDATE achievements SET isUnlocked = 1 WHERE id = :achievementId")
    suspend fun markAsUnlocked(achievementId: Int)

    @Query("SELECT * FROM achievements")
    fun getAchievements(): List<Achievement>

}
