package kz.digis.kazakhlearning.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.data.models.WordCardFirebase
import kz.digis.kazakhlearning.databinding.FragmentAdminBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import java.util.UUID
import javax.inject.Inject


@AndroidEntryPoint
class AdminFragment : BaseFragment<FragmentAdminBinding>(FragmentAdminBinding::inflate) {

    private val database by lazy { FirebaseDatabase.getInstance().getReference("wordCards") }

    private var imageUri: Uri? = null
    private var audioUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            imageUri = it
            binding.textImg.text = "Изображение выбрано"
            binding.img.setImageURI(it)
        }
    }


    private val audioPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let {
            audioUri = it
            binding.audioUrl.text = "Аудио выбрано"
        }
    }

    override fun onBindView() {
        super.onBindView()

        binding.createBtn.setOnClickListener {
            uploadAndSaveCard()
        }


        binding.uploadImg.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.uploadAudio.setOnClickListener {
            audioPickerLauncher.launch("audio/*")
        }

    }


    private fun uploadAndSaveCard() {
        val word = binding.nameInput.text.toString().trim()
        val translation = binding.translateInput.text.toString().trim()
        val category = binding.categoryInput.text.toString().trim()
        val description = binding.descriptionInput.text.toString().trim()

        if (word.isEmpty() || translation.isEmpty() || category.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните обязательные поля", Toast.LENGTH_SHORT).show()
            return
        }

        val storage = FirebaseStorage.getInstance().reference

        val imageRef = imageUri?.let {
            storage.child("images/${UUID.randomUUID()}.jpg").putFile(it)
                .continueWithTask { task ->
                    if (!task.isSuccessful) throw task.exception ?: Exception("Ошибка загрузки изображения")
                    task.result.storage.downloadUrl
                }
        } ?: Tasks.forResult(null)

        val audioRef = audioUri?.let {
            storage.child("audio/${UUID.randomUUID()}.mp3").putFile(it)
                .continueWithTask { task ->
                    if (!task.isSuccessful) throw task.exception ?: Exception("Ошибка загрузки аудио")
                    task.result.storage.downloadUrl
                }
        } ?: Tasks.forResult(null)

        Tasks.whenAllSuccess<Uri?>(imageRef, audioRef).addOnSuccessListener { urls ->
            val imageUrl = urls[0]?.toString()
            val audioUrl = urls[1]?.toString()

            val card = WordCardFirebase(
                kazakhWord = word,
                translation = translation,
                description = description,
                audioUrl = audioUrl,
                category = category,
                imageUrl = imageUrl
            )

            val key = FirebaseDatabase.getInstance().getReference("wordCards").push().key ?: return@addOnSuccessListener
            FirebaseDatabase.getInstance().getReference("wordCards").child(key).setValue(card)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Карточка добавлена!", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Ошибка сохранения карточки", Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Ошибка загрузки файла", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        with(binding) {
            nameInput.setText("")
            translateInput.setText("")
            categoryInput.setText("")
            descriptionInput.setText("")
            audioUrl.setText("")
        }
    }
}
