package kz.digis.kazakhlearning.presentation.screens

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.databinding.FragmentForgotPasswordBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject


@AndroidEntryPoint
class ForgotPasswordFragment:BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    @Inject
    lateinit var firebaseAuth:FirebaseAuth
    override fun onBindView() {
        super.onBindView()

        binding.sendBtn.setOnClickListener {
            firebaseAuth.sendPasswordResetEmail(binding.etEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showCustomDialog("Успех!", "Проверьте почту",)
                    } else {
                        Toast.makeText(requireContext(), "Ошибка! Проверьте подключение к интернету и правильность почты", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}