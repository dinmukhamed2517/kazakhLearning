package kz.digis.kazakhlearning.presentation.screens

import android.media.AudioAttributes
import android.media.MediaPlayer
import kz.digis.kazakhlearning.data.local.LocalWordProvider.wordList
import kz.digis.kazakhlearning.data.models.WordCard
import kz.digis.kazakhlearning.databinding.FragmentHomeBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    private var currentIndex = 0

    override fun onBindView() {
        super.onBindView()

        updateWordCard()

        binding.btnPlayAudio.setOnClickListener {
            wordList[currentIndex].audioUrl?.let { it1 -> playAudio(it1) }
        }

        binding.btnNextWord.setOnClickListener {
            if (currentIndex < wordList.size - 1) {
                currentIndex++
            } else {
                currentIndex = 0
            }
//            binding.flipView.flipTheView(true)
            updateWordCard()
        }


    }

    private fun updateWordCard() {
        val wordCard = wordList[currentIndex]

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




}