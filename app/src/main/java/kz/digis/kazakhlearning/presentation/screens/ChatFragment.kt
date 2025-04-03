package kz.digis.kazakhlearning.presentation.screens

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.data.models.Choice
import kz.digis.kazakhlearning.data.models.Message
import kz.digis.kazakhlearning.databinding.FragmentChatBinding
import kz.digis.kazakhlearning.presentation.adapters.ItemMessageAdapter
import kz.digis.kazakhlearning.presentation.base.BaseFragment
import kz.sdk.tussup.network.GeminiApiHelper

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(FragmentChatBinding::inflate) {

    private lateinit var geminiHelper: GeminiApiHelper

    private var messageList = mutableListOf<Choice>()
    var index: Int = 0

    override fun onBindView() {
        super.onBindView()
        geminiHelper = GeminiApiHelper("AIzaSyAnHDTVcsqPaqGcWn_ePatx2ZT3ZJEhBKM")

        val adapter = ItemMessageAdapter()
        binding.messageList.adapter = adapter
        binding.messageList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)


        binding.til.setEndIconOnClickListener {
            val userText = binding.editText.text.toString().trim()
            if (userText.isNotEmpty()) {
                binding.hello.isVisible = false
                binding.messageList.isVisible = true

                val userMessage = Message("user", userText)
                messageList.add(Choice(index++, userMessage))

                val updatedList = messageList.reversed().toMutableList()
                adapter.submitList(updatedList)
                binding.messageList.post {
                    binding.messageList.scrollToPosition(0)
                }

                binding.editText.text?.clear()

                geminiHelper.askKazakhExpert(userText) { reply ->
                    val assistantMessage = Message("assistant", reply ?: "Кешіріңіз, қате орын алды.")
                    messageList.add(Choice(index++, assistantMessage))

                    requireActivity().runOnUiThread {
                        val updated = messageList.reversed().toMutableList()
                        adapter.submitList(updated)
                        binding.messageList.post {
                            binding.messageList.scrollToPosition(0)
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }



    }

}