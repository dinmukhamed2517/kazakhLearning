package kz.digis.kazakhlearning.presentation.screens

import android.content.ClipData
import android.graphics.Color
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.startDragAndDrop
import androidx.navigation.fragment.navArgs
import kz.digis.kazakhlearning.R
import kz.digis.kazakhlearning.data.models.Sentence
import kz.digis.kazakhlearning.data.models.SentenceCategory
import kz.digis.kazakhlearning.databinding.FragmentGrammarBinding
import kz.digis.kazakhlearning.presentation.base.BaseFragment

class GrammarFragment : BaseFragment<FragmentGrammarBinding>(FragmentGrammarBinding::inflate) {

    private val args:GrammarFragmentArgs by navArgs()
    private var currentSentenceIndex = 0
    private lateinit var filteredCategory: SentenceCategory


    private lateinit var currentSentence: Sentence
    private val slotList = mutableListOf<FrameLayout>()


    val sampleData = listOf(
        SentenceCategory(
            name = "Урок 1",
            sentences = listOf(
                Sentence(id = 1, correctWords = listOf("Менде", "бес", "кітап", "бар")),
                Sentence(id = 2, correctWords = listOf("Сенде", "он", "қалам", "бар", "ма?"))
            )
        ),
        SentenceCategory(
            name = "Урок 2",
            sentences = listOf(
                Sentence(id = 3, correctWords = listOf("Сәлеметсіз", "бе,", "сіз", "қалайсыз?")),
                Sentence(id = 4, correctWords = listOf("Сау", "болыңыз,", "ертең", "көрісеміз"))
            )
        ),
        SentenceCategory(
            name = "Урок 3",
            sentences = listOf(
                Sentence(id = 5, correctWords = listOf("Менің", "анамның", "аты", "Айгүл")),
                Sentence(id = 6, correctWords = listOf("Менің", "бауырым", "мектепке", "барады"))
            )
        ),
        SentenceCategory(
            name = "Урок 4",
            sentences = listOf(
                Sentence(id = 7, correctWords = listOf("Мысық", "сүт", "ішіп", "отыр")),
                Sentence(id = 8, correctWords = listOf("Аю", "орманда", "тұрады"))
            )
        ),
        SentenceCategory(
            name = "Урок 5",
            sentences = listOf(
                Sentence(id = 9, correctWords = listOf("Бұл", "гүл", "қызыл", "түсті")),
                Sentence(id = 10, correctWords = listOf("Менің", "көйлегім", "көк", "түсті"))
            )
        ),
        SentenceCategory(
            name = "Урок 6",
            sentences = listOf(
                Sentence(id = 11, correctWords = listOf("Мен", "ет", "жегенді", "ұнатамын")),
                Sentence(id = 12, correctWords = listOf("Сүт", "денсаулыққа", "пайдалы"))
            )
        ),
        SentenceCategory(
            name = "Урок 7",
            sentences = listOf(
                Sentence(id = 13, correctWords = listOf("Мен", "кешке", "жүгіремін")),
                Sentence(id = 14, correctWords = listOf("Ол", "кітап", "оқып", "отыр"))
            )
        ),
        SentenceCategory(
            name = "Урок 8",
            sentences = listOf(
                Sentence(id = 15, correctWords = listOf("Бүгін", "дүйсенбі")),
                Sentence(id = 16, correctWords = listOf("Жексенбіде", "мен", "демаламын"))
            )
        ),
        SentenceCategory(
            name = "Урок 9",
            sentences = listOf(
                Sentence(id = 15, correctWords = listOf("Бүгін", "дүйсенбі")),
                Sentence(id = 16, correctWords = listOf("Жексенбіде", "мен", "демаламын"))
            )
        )

    )

    override fun onBindView() {
        super.onBindView()

        filteredCategory = sampleData.first { it.name == args.category }
        currentSentenceIndex = 0
        currentSentence = filteredCategory.sentences[currentSentenceIndex]

        setupSentence(currentSentence)
    }


    private fun setupSentence(sentence: Sentence) {
        with(binding) {
            slotsContainer.removeAllViews()
            wordsContainer.removeAllViews()
            resultText.text = ""
            slotList.clear()

            repeat(sentence.correctWords.size) {
                val slot = FrameLayout(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        200, 150
                    ).apply {
                        setMargins(12, 12, 12, 12)
                    }
                    minimumWidth = 100
                    minimumHeight = 100
                    setPadding(16, 16, 16, 16)
                    background = ContextCompat.getDrawable(requireContext(), R.drawable.slot_bg)
                    setOnDragListener(dragListener)
                }
                slotList.add(slot)
                slotsContainer.addView(slot)
            }

            // Generate draggable words
            sentence.correctWords.shuffled().forEach { word ->
                val card = CardView(requireContext()).apply {
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(8, 8, 8, 8)
                    }
                    radius = 16f
                    cardElevation = 4f
                    setCardBackgroundColor(Color.WHITE)

                    val wordView = TextView(requireContext()).apply {
                        text = word
                        textSize = 18f
                        setPadding(24, 16, 24, 16)
                        setTextColor(Color.BLACK)
                        setOnLongClickListener {
                            val clipData = ClipData.newPlainText("word", word)
                            val shadow = View.DragShadowBuilder(this)
                            startDragAndDrop(clipData, shadow, this, 0)
                            true
                        }
                    }
                    addView(wordView)
                }

                wordsContainer.addView(card)

            }
        }
    }

    private val dragListener = View.OnDragListener { view, event ->
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val draggedView = event.localState as View
                val owner = draggedView.parent as ViewGroup
                val target = view as FrameLayout

                if (target.childCount == 0) {
                    owner.removeView(draggedView)
                    target.addView(draggedView)

                    target.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(12, 12, 12, 12)
                    }

                    checkAnswer()
                }

                true
            }
            else -> true
        }
    }

    private fun checkAnswer() {
        val userWords = slotList.map { slot ->
            if (slot.childCount > 0) (slot.getChildAt(0) as TextView).text.toString() else ""
        }

        if (userWords.any { it.isBlank() }) return

        val isCorrect = userWords == currentSentence.correctWords

        binding.resultText.text = if (isCorrect) {
            "✅ Правильно!"
        } else {
            "❌ Попробуйте еще раз!"
        }

        binding.resultText.postDelayed({
            if (isCorrect) {
                if (currentSentenceIndex < filteredCategory.sentences.lastIndex) {
                    currentSentenceIndex++
                    currentSentence = filteredCategory.sentences[currentSentenceIndex]
                    setupSentence(currentSentence)
                } else {
                    binding.resultText.text = "🎉 Все предложения пройдены!"
                }
            } else {
                setupSentence(currentSentence) // restart current
            }
        }, 1000)
    }

}
