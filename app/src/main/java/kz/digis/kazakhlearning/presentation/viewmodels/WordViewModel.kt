package kz.digis.kazakhlearning.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.digis.kazakhlearning.data.WordCardDao
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

    fun getWordsByCategory(category: String) = dao.getWordsByCategory(category)

    fun getWordsByKnownStatus(status: Boolean) = dao.getWordsByKnownStatus(status)
}
