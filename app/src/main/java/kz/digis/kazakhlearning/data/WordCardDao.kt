package kz.digis.kazakhlearning.data

import androidx.room.*
import kz.digis.kazakhlearning.data.models.WordCard

@Dao
interface WordCardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(wordCard: WordCard)

    @Query("UPDATE word_cards SET isKnown = 1 WHERE id = :wordId")
    suspend fun markAsKnown(wordId: Int)

    @Query("SELECT * FROM word_cards WHERE category = :category")
    fun getWordsByCategory(category: String): List<WordCard>

    @Query("SELECT * FROM word_cards WHERE isKnown = :status")
    fun getWordsByKnownStatus(status: Boolean): List<WordCard>
    @Query("SELECT * FROM word_cards WHERE category = :category AND isKnown = 1")
    suspend fun getKnownWords(category: String): List<WordCard>
    @Update
    suspend fun updateAll(cards: List<WordCard>)
    @Query("SELECT * FROM word_cards WHERE isKnown = 1")
    suspend fun getAllKnownWords(): List<WordCard>

    @Query("DELETE FROM word_cards")
    suspend fun deleteAll()


}
