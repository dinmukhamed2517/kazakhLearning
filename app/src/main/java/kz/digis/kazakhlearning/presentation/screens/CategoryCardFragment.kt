package kz.digis.kazakhlearning.presentation.screens

import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.data.local.LocalWordProvider
import kz.digis.kazakhlearning.data.models.WordCard
import kz.digis.kazakhlearning.data.models.WordCardFirebase
import kz.digis.kazakhlearning.databinding.FragmentCategoryCardBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import kz.digis.kazakhlearning.presentation.viewmodels.WordViewModel
import javax.inject.Inject

@AndroidEntryPoint
class CategoryCardFragment : BaseFragment<FragmentCategoryCardBinding>(FragmentCategoryCardBinding::inflate) {

    private var timer: CountDownTimer? = null

    @Inject
    lateinit var userDao: UserDao
    private val wordViewModel: WordViewModel by viewModels()
    private val args: CategoryCardFragmentArgs by navArgs()
    private var currentIndex = 0
    private var filteredWords = emptyList<WordCard>()
    private var hasLoopedOnce = false
    private var mediaPlayer: MediaPlayer? = null

    private val databaseRef = FirebaseDatabase.getInstance().getReference("wordCards")

    override fun onBindView() {
        super.onBindView()
        binding.loadingView.isVisible = true
        binding.categoryText.text = args.category

        userDao.getData()
        userDao.getDataLiveData.observe(viewLifecycleOwner) { user ->
            user?.dailyTime?.let { timeInMinutes ->
                startTimer(timeInMinutes * 60 * 1000L)
            }
            binding.loadingView.isVisible = false
        }

        val localWords = LocalWordProvider.wordList.filter { it.category == args.category }

        databaseRef.get().addOnSuccessListener { dataSnapshot ->
            val firebaseWords = dataSnapshot.children.mapNotNull { it.getValue(WordCardFirebase::class.java) }
                .filter { it.category == args.category }
                .map {
                    WordCard(
                        kazakhWord = it.kazakhWord,
                        translation = it.translation,
                        description = it.description,
                        audioUrl = it.audioUrl,
                        category = it.category,
                        isKnown = it.isKnown,
                        image = null
                    )
                }

            wordViewModel.deleteAllWords() // Clean database

            val preparedWords = (localWords + firebaseWords).map {
                it.copy(id = 0) // THIS IS IMPORTANT – let Room auto-generate!
            }
            filteredWords = preparedWords

            wordViewModel.insertAll(filteredWords)


            if (filteredWords.isEmpty()) {
                navigateToTestFragment()
                return@addOnSuccessListener
            }

            updateWordCard()
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Ошибка загрузки данных из Firebase", Toast.LENGTH_SHORT).show()
            filteredWords = localWords

            if (filteredWords.isEmpty()) {
                navigateToTestFragment()
                return@addOnFailureListener
            }

            updateWordCard()
        }

        binding.btnAudio.setOnClickListener {
            filteredWords[currentIndex].audioUrl?.let { audioUrl -> playAudio(audioUrl) }
        }

        binding.btnNextWord.setOnClickListener {
            markCurrentWordAsKnown()

            if (currentIndex < filteredWords.size - 1) {
                currentIndex++
            } else {
                if (!hasLoopedOnce) {
                    hasLoopedOnce = true
                    navigateToTestFragment()
                } else {
                    currentIndex = 0
                }
            }
            updateWordCard()
        }
    }

    private fun startTimer(millisInFuture: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(millisInFuture, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.timingText.text = String.format("%d:%02d", minutes, seconds)
                userDao.saveAppTime(minutes.toInt())
            }

            override fun onFinish() {
                binding.timingText.text = "0:00"
                userDao.saveAppTime(0)
            }
        }.start()
    }

    private fun updateWordCard() {
        val wordCard = filteredWords[currentIndex]
        binding.tvTranslation.text = wordCard.translation
        binding.tvDescription.text = wordCard.description
        binding.tvKazakhWord.text = wordCard.kazakhWord
        wordCard.image?.let { binding.wordcardImage.setImageResource(it) }
    }

    private fun markCurrentWordAsKnown() {
        val word = filteredWords[currentIndex]
        if (!word.isKnown) {
            word.isKnown = true
            lifecycleScope.launch {
                wordViewModel.markWordAsKnown(word.id)
                updateLearnedWordsCount()
            }
        }
    }

    private fun updateLearnedWordsCount() {
        lifecycleScope.launch {
            val knownWords = wordViewModel.getAllKnownWords()
            Log.d("LearnedWordsDebug", "Known words count: ${knownWords.size}")
            knownWords.forEach { Log.d("LearnedWordsDebug", it.kazakhWord) }
            userDao.saveLearnedWordsCount(knownWords.size)
        }
    }


    private fun playAudio(audioFileName: String) {
        val resId = resources.getIdentifier(audioFileName, "raw", requireContext().packageName)
        if (resId == 0) return

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(requireContext(), resId)
        mediaPlayer?.apply {
            setOnPreparedListener { start() }
            setOnCompletionListener {
                release()
                mediaPlayer = null
            }
        }
    }

    private fun navigateToTestFragment() {
        findNavController().navigate(CategoryCardFragmentDirections.actionCategoryCardFragmentToTestFragment(args.category))
    }

    override fun onDestroyView() {
        timer?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroyView()
    }
}
