package kz.digis.kazakhlearning.presentation.screens

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.databinding.FragmentRegisterBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()


        binding.registerBtn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etPasswordRepeat.text.toString()



            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showCustomDialog("Успех", "Вы зарегистрировались")
                                binding.tilEmail.isErrorEnabled = false
                                findNavController().navigate(
                                    R.id.action_registerFragment_to_userDetailsFragment
                                )
                            } else {
                                binding.tilEmail.isErrorEnabled = true
                                binding.tilPasswordRepeat.isErrorEnabled = true
                                binding.tilPassword.isErrorEnabled = true
                                binding.tilEmail.error = "Что то не так"
                                binding.tilPasswordRepeat.error = "Что то не так"
                                binding.tilPassword.error = "Что то не так"
                            }
                        }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Пароли не совпадают",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        binding.haveAccountBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
}