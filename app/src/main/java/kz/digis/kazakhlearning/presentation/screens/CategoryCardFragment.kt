package kz.digis.kazakhlearning.presentation.screens

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.data.local.LocalWordProvider
import kz.digis.kazakhlearning.data.models.Category
import kz.digis.kazakhlearning.data.models.WordCard
import kz.digis.kazakhlearning.databinding.FragmentCategoryCardBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment






@AndroidEntryPoint
class CategoryCardFragment : BaseFragment<FragmentCategoryCardBinding>(FragmentCategoryCardBinding::inflate) {

    private val args: CategoryCardFragmentArgs by navArgs()
    private var currentIndex = 0
    private var filteredWords = emptyList<WordCard>()
    private var hasLoopedOnce = false

    override fun onBindView() {
        super.onBindView()

        binding.categoryText.text = args.category

        filteredWords = LocalWordProvider.wordList.filter { it.category == args.category }

        if (filteredWords.isEmpty()) {
            navigateToTestFragment()
            return
        }

        updateWordCard()

        binding.btnPlayAudio.setOnClickListener {
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

    private fun updateWordCard() {
        val wordCard = filteredWords[currentIndex]
        binding.tvTranslation.text = wordCard.translation
        binding.tvDescription.text = wordCard.description
        binding.tvKazakhWord.text = wordCard.kazakhWord
    }

    private fun playAudio(audioUrl: String) {
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(audioUrl)
            prepare()
            start()
        }
    }

    private fun navigateToTestFragment() {
        findNavController().navigate(CategoryCardFragmentDirections.actionCategoryCardFragmentToTestFragment(args.category))
    }
}

