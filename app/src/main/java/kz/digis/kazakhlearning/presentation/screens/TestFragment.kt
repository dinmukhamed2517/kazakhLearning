package kz.digis.kazakhlearning.presentation.screens

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.firebase.UserDao
import kz.digis.kazakhlearning.databinding.FragmentTestBinding
import kz.digis.kazakhlearning.data.local.getAllTests
import kz.digis.kazakhlearning.data.models.Achievement
import kz.digis.kazakhlearning.data.models.Question
import kz.digis.kazakhlearning.data.models.Test
import kz.digis.kazakhlearning.databinding.CustomAchievementDialogBinding
import kz.digis.kazakhlearning.databinding.DialogTestFailureBinding
import kz.digis.kazakhlearning.databinding.DialogTestSuccessBinding
import javax.inject.Inject


@AndroidEntryPoint
class TestFragment : Fragment() {


    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var userDao: UserDao
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val args: TestFragmentArgs by navArgs()

    private var currentQuestionIndex = 0
    private lateinit var currentTest: Test
    private lateinit var currentQuestion: Question
    private val answeredQuestions = mutableMapOf<Int, Boolean>()
    private var isAnswered = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("current category", "${args.category}")
        val category = args.category
        currentTest = getAllTests().find { it.category == category } ?: getAllTests().first()
        loadQuestion()
        setupListeners()
    }

    private fun loadQuestion() {
        currentQuestion = currentTest.questions[currentQuestionIndex]
        binding.questionText.text = currentQuestion.title
        binding.cardView4.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main_color))

        isAnswered = false

        val answerViews = listOf(
            Pair(binding.answerA, binding.responseText1),
            Pair(binding.answerB, binding.responseText2),
            Pair(binding.answerC, binding.responseText3),
            Pair(binding.answerD, binding.responseText4)
        )

        answerViews.forEachIndexed { index, pair ->
            pair.second.text = currentQuestion.variants[index]
            pair.first.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            pair.second.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
            pair.first.setOnClickListener { checkAnswer(pair.second.text.toString()) }
        }

        updateQuestionButtons()
    }

    private fun checkAnswer(selectedAnswer: String) {
        if (isAnswered) return

        val isCorrect = selectedAnswer == currentQuestion.correctAnswer
        answeredQuestions[currentQuestionIndex] = isCorrect
        isAnswered = true

        if (isCorrect) {
            binding.questionText.text = "Правильно!"
        } else {
            binding.questionText.text = "Неправильно!"
        }

        updateQuestionButtons()

        binding.questionText.postDelayed({
            if (currentQuestionIndex < currentTest.questions.size - 1) {
                currentQuestionIndex++
                loadQuestion()
            } else {

                updateQuestionButtons()
                val correctAnswers = answeredQuestions.values.count { it }
                val totalQuestions = currentTest.questions.size
                if(correctAnswers > totalQuestions/2){
                    showSuccessDialog(correctAnswers, totalQuestions)
                    awardAchievementForTest()

                }else{
                    showFailureDialog(correctAnswers, totalQuestions)
                }
            }
        }, 1000)
    }

    private fun updateQuestionButtons() {
        val questionButtons = listOf(
            binding.question1 to binding.question1Text,
            binding.question2 to binding.question2Text,
            binding.question3 to binding.question3Text,
            binding.question4 to binding.question4Text,
            binding.question5 to binding.question5Text,
            binding.question6 to binding.question6Text
        )

        questionButtons.forEachIndexed { index, (button, textView) ->
            when {
                answeredQuestions.containsKey(index) -> {
                    val color = if (answeredQuestions[index] == true) R.color.green else R.color.red
                    button.setCardBackgroundColor(ContextCompat.getColor(requireContext(), color))
                    textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                index == currentQuestionIndex -> {
                    button.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                    textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                else -> {
                    button.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                    textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
                }
            }
        }
    }

    private fun setupListeners() {
        val questionButtons = listOf(
            binding.question1, binding.question2, binding.question3,
            binding.question4, binding.question5, binding.question6
        )

        questionButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (index < currentTest.questions.size && !answeredQuestions.containsKey(index) && isAnswered) {
                    currentQuestionIndex = index
                    loadQuestion()
                }
            }
        }
    }


    private fun showSuccessDialog(correctAnswers: Int, totalQuestions: Int) {
        val successBinding = DialogTestSuccessBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(successBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        successBinding.successMessage.text = "Вы ответили правильно на $correctAnswers из $totalQuestions вопросов!"


        successBinding.okBtn.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment_to_categoryFragment)
        }

        dialog.show()
    }


    private fun showFailureDialog(correctAnswers: Int, totalQuestions: Int) {
        val dialogBinding = DialogTestFailureBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.failureMessage.text = "Вы ответили правильно на $correctAnswers из $totalQuestions вопросов. Попробуйте ещё раз!"

        dialogBinding.retakeButton.setOnClickListener {
            dialog.dismiss()
            answeredQuestions.clear()
            currentQuestionIndex = 0
            loadQuestion()
        }

        dialog.show()
    }

    private fun awardAchievementForTest() {
        val achievement = Achievement(
            title = "Первый тест завершен",
            imageId = R.drawable.bronze,
            reachCount = 1,
            isUnlocked = true,
            description = "Поздравляем! Вы завершили свой первый тест и получили достижение."
        )

        userDao.saveAchievement(achievement)

        val dialogBinding = CustomAchievementDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBinding.achievementImage.setImageResource(achievement.imageId)
        dialogBinding.subtitleAchievement.text = achievement.description
        dialogBinding.dialogTitle.text = achievement.title
        dialogBinding.okBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }




}
