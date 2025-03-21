package kz.digis.kazakhlearning.presentation.screens

import kz.digis.kazakhlearning.databinding.FragmentUpdateProfileBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment


import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.databinding.FragmentProfileBinding
import javax.inject.Inject


@AndroidEntryPoint
class UpdateProfileFragment: BaseFragment<FragmentUpdateProfileBinding>(FragmentUpdateProfileBinding::inflate) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var storageReference: StorageReference

    @Inject
    lateinit var userDao: UserDao

    private var imageUri: Uri? = null

    private var oldName: String? = null
    private var oldLastName: String? = null
    private var oldPictureUrl: String? = null
    private var oldAppTime: Int? = null

    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()

        userDao.getData()

        userDao.getDataLiveData.observe(viewLifecycleOwner) { userData ->
            binding.loadingView.isVisible = false

            if (userData != null) {
                oldName = userData.name
                oldLastName = userData.lastname
                oldPictureUrl = userData.pictureUrl
                oldAppTime = userData.dailyTime

                binding.nameInput.setText(userData.name ?: "")
                binding.lastNameInput.setText(userData.lastname ?: "")
                binding.timeInput.setText(userData.dailyTime.toString())

                if (!userData.pictureUrl.isNullOrEmpty()) {
                    Glide.with(requireContext())
                        .load(userData.pictureUrl)
                        .into(binding.avatar)
                }
            }
        }

        binding.avatar.setOnClickListener {
            resultLauncher.launch("image/*")
        }

        binding.updateBtn.setOnClickListener {
            updateProfile()
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.avatar.setImageURI(uri)
            imageUri = uri
        }
    }


    private fun updateProfile() {
        binding.loadingView.isVisible = true

        val newName = binding.nameInput.text.toString().trim()
        val newLastName = binding.lastNameInput.text.toString().trim()
        val newAppTime = binding.timeInput.text.toString().trim().toInt()

        if (imageUri != null) {
            storageReference.putFile(imageUri!!)
                .addOnSuccessListener { task ->
                    task.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                        val newPicUrl = uri.toString()

                        userDao.saveProfilePic(newPicUrl)

                        if (newName.isNotEmpty() && newName != oldName) {
                            userDao.saveName(newName)
                        }

                        if (newLastName.isNotEmpty() && newLastName != oldLastName) {
                            userDao.saveLastName(newLastName)
                        }
                        if(newAppTime != oldAppTime){
                            userDao.saveAppTime(newAppTime)
                        }

                        binding.loadingView.isVisible = false
                        showCustomDialog("Успех", "Данные успешно обновлены")
                    }
                }
                .addOnFailureListener {
                    binding.loadingView.isVisible = false
                    Toast.makeText(requireContext(), "Ошибка при загрузке фото", Toast.LENGTH_SHORT).show()
                }
        } else {
            if (newName.isNotEmpty() && newName != oldName) {
                userDao.saveName(newName)
            }
            if (newLastName.isNotEmpty() && newLastName != oldLastName) {
                userDao.saveLastName(newLastName)
            }
            if(newAppTime != oldAppTime){
                userDao.saveAppTime(newAppTime)
            }
            binding.loadingView.isVisible = false
            showCustomDialog("Успех", "Данные успешно обновлены")
        }
    }
}
