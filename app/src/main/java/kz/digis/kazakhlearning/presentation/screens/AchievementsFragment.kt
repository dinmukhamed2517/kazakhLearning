package kz.digis.kazakhlearning.presentation.screens

import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.google.api.Distribution.BucketOptions.Linear
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.data.models.Achievement
import kz.digis.kazakhlearning.databinding.FragmentAchievementsBinding
import kz.digis.kazakhlearning.presentation.adapters.AchievementAdapter
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class AchievementsFragment: BaseFragment<FragmentAchievementsBinding>(FragmentAchievementsBinding::inflate) {

    @Inject lateinit var userDao: UserDao




    val achievements = listOf(
        Achievement(title = "Первый тест завершен", imageId = R.drawable.bronze, reachCount = 1, description = "Поздравляем! Вы завершили свой первый тест и получили достижение."),
        Achievement(title = "Первые выученные 3 слово", imageId = R.drawable.bronze, reachCount = 3, description = "Вы выучили первые 3 слово"),
        Achievement(title = "Первые выученные 3 слово", imageId = R.drawable.bronze, reachCount = 3, description = "Вы выучили первые 3 слово"),
        Achievement(title = "Первые выученные 3 слово", imageId = R.drawable.bronze, reachCount = 3, description = "Вы выучили первые 3 слово"),
        Achievement(title = "Первые выученные 3 слово", imageId = R.drawable.bronze, reachCount = 3, description = "Вы выучили первые 3 слово"),
        )
    override fun onBindView() {
        super.onBindView()

        val adapter = AchievementAdapter()
        binding.achievementRecycler.adapter = adapter
        binding.achievementRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.loadingView.isVisible = true
        userDao.getData()

        userDao.getDataLiveData.observe(viewLifecycleOwner) { userData ->
            binding.loadingView.isVisible = false

            val unlockedAchievements = userData?.achievements?.values?.toList() ?: emptyList()
            Log.d("unlockedAchievements", "${unlockedAchievements}")
            val mergedAchievements = achievements.map { achievement ->
                val isUnlocked = unlockedAchievements.any { it.title == achievement.title }
                achievement.copy(isUnlocked = isUnlocked)
            }

            adapter.submitList(unlockedAchievements)
        }
    }




}

