package kz.digis.kazakhlearning.presentation.screens


import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.databinding.FragmentProfileBinding
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
        }
//        binding.activateBtn.setOnClickListener{
//            findNavController().navigate(R.id.action_profileFragment_to_choiceFragment)
//        }
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