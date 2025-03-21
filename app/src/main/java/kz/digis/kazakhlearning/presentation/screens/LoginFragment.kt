package kz.digis.kazakhlearning.presentation.screens

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.databinding.FragmentLoginBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()

        binding.loginBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showCustomDialog("Успех", "Вы зашли в свой аккаунт")
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                    } else {
                        binding.tilEmail.isErrorEnabled = true
                        binding.tilPassword.isErrorEnabled = true
                        binding.tilEmail.error = "Что то не так"
                        binding.tilPassword.error = "Что то не так"
                        Toast.makeText(context, "Ошибка в авторизации: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        binding.noAccountBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
    }
}