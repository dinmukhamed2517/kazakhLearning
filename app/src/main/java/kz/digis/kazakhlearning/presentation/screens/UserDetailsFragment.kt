package kz.digis.kazakhlearning.presentation.screens

import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.databinding.FragmentUserDetailsBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment


import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.data.models.User
import kz.digis.kazakhlearning.presentation.viewmodels.SharedViewModel
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsFragment: BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {

    private val viewModel: SharedViewModel by activityViewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userDao: UserDao
    private var imageUri: Uri? = null

    @Inject
    lateinit var storageReference: StorageReference


    override var showBottomNavigation: Boolean = false
    override fun onBindView() {
        super.onBindView()
        var ok = true
        with(binding){
            avatar.setOnClickListener {
                resultLauncher.launch("image/*")
            }

            nextBtn.setOnClickListener {
                if (imageUri != null) {
                    uploadProfilePic()
                }
                if (nameInput.text?.isEmpty() == true) {
                    nameLayout.error = "Fill up"
                    nameLayout.isErrorEnabled = true
                    ok = false
                } else {
                    nameLayout.isErrorEnabled = false
                    viewModel.name = nameInput.text.toString()
                    ok = true
                }
                if (lastNameInput.text?.isEmpty() == true) {
                    lastNameLayout.error = "Fill up"
                    lastNameLayout.isErrorEnabled = true
                    ok = false

                } else {
                    lastNameLayout.isErrorEnabled = false
                    viewModel.lastname = lastNameInput.text.toString()
                    ok = true
                }
                if (ok) {
                    val user = User(
                        viewModel.name,
                        viewModel.lastname,
                    )
                    userDao.saveData(user)
                    findNavController().navigate(
                        R.id.action_userDetailsFragment_to_timePickerFragment
                    )
                }
            }
        }

    }
    private fun uploadProfilePic() {
        imageUri?.let {
            storageReference.putFile(it).addOnSuccessListener { task ->
                task.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val imgUrl = uri.toString()
                    userDao.saveProfilePic(imgUrl)
                    showCustomDialog("Успех", "Информация сохранена")

                }

            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Невозможно загрузить фотографию", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.avatar.setImageURI(it)
        imageUri = it
    }
}