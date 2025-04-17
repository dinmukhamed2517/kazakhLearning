package kz.digis.kazakhlearning.presentation.screens

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.databinding.FragmentTutorialBinding

@AndroidEntryPoint
class TutorialFragment : Fragment() {

    private var _binding: FragmentTutorialBinding? = null
    private val binding get() = _binding!!
    private val args: TutorialFragmentArgs by navArgs()

    private val tutorialLinks = listOf(
        TutorialLink("Урок - 1. Алфавит казахского языка", "https://www.youtube.com/watch?v=8xpFFNMzsUs", "Урок 1"),
        TutorialLink("Урок - 2 . Порядок слов в предложении", "https://www.youtube.com/watch?v=ml8C181KXJc&t=1s", "Урок 2"),
        TutorialLink("Урок - 3. Личные окончания. Единственное число", "https://www.youtube.com/watch?v=OoZ2g-2MJsM", "Урок 3"),
        TutorialLink("Урок - 4. Множественные число. Көптік жалғау", "https://www.youtube.com/watch?v=7Y3o4A-5FnY", "Урок 4"),
        TutorialLink("Урок - 5 . Вопросительные частицы в Казахском языке", "https://www.youtube.com/watch?v=cecep46ALQo", "Урок 5"),
        TutorialLink("Урок - 6. Отрицательные суффиксы в казахском языке", "https://www.youtube.com/watch?v=lysFevF5BpI", "Урок 6"),
        TutorialLink("Урок - 7. Дни недели", "https://www.youtube.com/watch?v=RdsRTeVsR4A", "Урок 7"),
        TutorialLink("Урок - 8. Единственное число", "https://www.youtube.com/watch?v=tVWpSe_rsrY", "Урок 8"),
        TutorialLink("Урок - 9. Глагол(Етістік) в казахском языке", "https://www.youtube.com/watch?v=W9JmeR43bKI", "Урок 9"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val category = args.category

        val videoUrl = tutorialLinks.firstOrNull { it.category == category }?.url
        val title = tutorialLinks.firstOrNull {it.category == category}?.title
        binding.title.text = title
        if (videoUrl != null) {
            val videoId = getYoutubeVideoIdFromUrl(videoUrl)
            val youtubePlayerView = binding.youtubePlayerView
            lifecycle.addObserver(youtubePlayerView)

            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(player: YouTubePlayer) {
                    player.loadVideo(videoId, 0f)
                }
            })
        }

        binding.skipBtn.setOnClickListener {
            findNavController().navigate(TutorialFragmentDirections.actionTutorialFragmentToGrammarFragment(args.category))
        }
    }

    private fun getYoutubeVideoIdFromUrl(url: String): String {
        return Uri.parse(url).getQueryParameter("v") ?: url.substringAfterLast("/")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class TutorialLink(
    val title: String,
    val url: String,
    val category: String
)

