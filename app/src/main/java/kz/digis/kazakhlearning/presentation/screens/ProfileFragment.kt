package kz.digis.kazakhlearning.presentation.screens


import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.data.models.Achievement
import kz.digis.kazakhlearning.databinding.FragmentProfileBinding
import kz.digis.kazakhlearning.presentation.adapters.AchievementAdapter
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint

class ProfileFragment: BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userDao: UserDao
    override fun onBindView() {
        binding.loadingView.isVisible = true

        val achievements = listOf(
            Achievement(title = "Первые выученные 3 слово", imageId = R.drawable.bronze, reachCount = 3, description = "Вы выучили первые 3 слово"),
            Achievement(title = "Первые выученные 10 слов", imageId = R.drawable.bronze, reachCount = 10, description = "Вы выучили первые 10 слово"),
            Achievement(title = "Первые выученные 20 слово", imageId = R.drawable.bronze, reachCount = 20, description = "Вы выучили первые 20 слово"),
            Achievement(title = "Первые выученные 50 слово", imageId = R.drawable.bronze, reachCount = 50, description = "Вы выучили первые 50 слово"),
            Achievement(title = "Первые выученные 100 слово", imageId = R.drawable.bronze, reachCount = 100, description = "Вы выучили первые 100 слово"),
            Achievement(title = "Первые выученные 200 слово", imageId = R.drawable.bronze, reachCount = 200, description = "Вы выучили первые 200 слово"),
            Achievement(title = "Первые выученные 500 слово", imageId = R.drawable.bronze, reachCount = 500, description = "Вы выучили первые 500 слово"),
        )


        val adapter = AchievementAdapter()
        binding.achievementRecycler.adapter = adapter
        binding.achievementRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        userDao.getData()
        super.onBindView()
        binding.signOutBtn.setOnClickListener {
            signOut()
        }
        binding.settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_updateProfileFragment)
        }
        binding.email.text = firebaseAuth.currentUser?.email;

        userDao.getDataLiveData.observe(viewLifecycleOwner) { userData ->
            binding.loadingView.isVisible = false

            if (userData?.isAdmin == false) {
                binding.adminBtn.isVisible = false
            }

            binding.name.text = userData?.name
            if (userData?.pictureUrl != null) {
                Glide.with(requireContext())
                    .load(userData.pictureUrl)
                    .into(binding.ava)
            } else {
                binding.ava.setImageResource(R.drawable.profile_icon)
            }
//            val unlockedAchievements = userData?.achievements?.values?.toList() ?: emptyList()
            val learnedWordsCount = userData?.learnedWordsCount ?: 0

            val availableAchievements = achievements.filter { it.reachCount <= learnedWordsCount }


            adapter.submitList(availableAchievements)

        }
        binding.activateBtn.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_adminFragment)
        }
    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

    }

    private fun signOut() {
        var alertDialog: AlertDialog? = null
        alertDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Выйти с аккаунта")
            .setMessage("Вы уверены что хотите выйти с аккаунта?")
            .setPositiveButton("Да") { _, _ ->
                firebaseAuth.signOut()
                alertDialog?.dismiss()
                findNavController().navigate(
                    R.id.action_profileFragment_to_loginFragment
                )
            }
            .setNegativeButton("Отмена") { _, _ ->
                alertDialog?.dismiss()
            }
            .show()
    }


}