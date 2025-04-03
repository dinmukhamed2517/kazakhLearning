package kz.digis.kazakhlearning.presentation.screens

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.data.local.LocalWordProvider
import kz.digis.kazakhlearning.data.models.Category
import kz.digis.kazakhlearning.data.models.WordCard
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

        filteredWords = LocalWordProvider.wordList.filter { it.category == args.category }

        if (filteredWords.isEmpty()) {
            navigateToTestFragment()
            return
        }

        updateWordCard()


        binding.btnAudio.setOnClickListener {
            filteredWords[currentIndex].audioUrl?.let { audioUrl -> playAudio(audioUrl) }
        }

        binding.btnNextWord.setOnClickListener {
            if (currentIndex < filteredWords.size - 1) {
                currentIndex++
            } else {
                // When the user loops through the list once, navigate to TestFragment
                if (!hasLoopedOnce) {
                    hasLoopedOnce = true
                    navigateToTestFragment()
                } else {
                    currentIndex = 0 // Reset to first word for a second loop if needed
                    updateWordCard()
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


    override fun onDestroyView() {
        timer?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null

        super.onDestroyView()
    }
    private fun updateWordCard() {
        val wordCard = filteredWords[currentIndex]
        binding.tvTranslation.text = wordCard.translation
        binding.tvDescription.text = wordCard.description
        binding.tvKazakhWord.text = wordCard.kazakhWord
        if(wordCard.image != null){
            binding.wordcardImage.setImageResource(wordCard.image)
        }
    }

    private var mediaPlayer: MediaPlayer? = null

    private fun playAudio(audioFileName: String) {
        val resId = resources.getIdentifier(audioFileName, "raw", requireContext().packageName)
        if (resId == 0) {
            return
        }

        mediaPlayer?.release() // Release previous if any
        mediaPlayer = MediaPlayer.create(requireContext(), resId)
        mediaPlayer?.apply {
            setOnPreparedListener {
                start()
            }
            setOnCompletionListener {
                release()
                mediaPlayer = null
            }
        }
    }


    private fun navigateToTestFragment() {
        findNavController().navigate(CategoryCardFragmentDirections.actionCategoryCardFragmentToTestFragment(args.category))
    }
}

