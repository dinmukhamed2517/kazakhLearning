package kz.digis.kazakhlearning.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.digis.kazakhlearning.data.WordCardDao
import kz.digis.kazakhlearning.data.models.WordCard
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val dao: WordCardDao
) : ViewModel() {

    fun markWordAsKnown(wordId: Int) {
        viewModelScope.launch {
            dao.markAsKnown(wordId)
        }
    }

    suspend fun getKnownWordNames(category: String): List<String> {
        return dao.getKnownWords(category).map { it.kazakhWord }
    }

    fun markWordsAsKnown(words: List<WordCard>) {
        viewModelScope.launch {
            dao.updateAll(words.map { it.copy(isKnown = true) })
        }
    }

    fun getWordsByCategory(category: String) = dao.getWordsByCategory(category)

    fun getWordsByKnownStatus(status: Boolean) = dao.getWordsByKnownStatus(status)

    suspend fun getKnownWords(category: String) = dao.getKnownWords(category)

    fun insertWord(word: WordCard) {
        viewModelScope.launch {
            dao.insertWord(word)
        }
    }

    fun insertAll(words: List<WordCard>) {
        viewModelScope.launch {
            dao.updateAll(words)
        }
    }
}
