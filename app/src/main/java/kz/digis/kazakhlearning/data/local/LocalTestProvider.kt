package kz.digis.kazakhlearning.data.local

import kz.digis.kazakhlearning.data.models.Question
import kz.digis.kazakhlearning.data.models.Test

val testGreetings = Test(
    id = 1,
    category = "Приветствия и прощания",
    questions = listOf(
        Question(
            id = 1,
            title = "Как сказать 'Здравствуйте' на казахском языке?",
            variants = listOf("Сәлем!", "Қалайсыз?", "Сәлеметсіз бе?", "Сау бол!"),
            correctAnswer = "Сәлеметсіз бе?"
        ),
        Question(
            id = 2,
            title = "Что означает 'Қайырлы күн!'?",
            variants = listOf("Добрый вечер", "Добрый день", "Доброе утро", "Пока"),
            correctAnswer = "Добрый день"
        ),
        Question(
            id = 3,
            title = "Как правильно сказать 'До свидания' уважительно?",
            variants = listOf("Сау бол!", "Сау болыңыз!", "Келгеніңізге рақмет!", "Көріскенше!"),
            correctAnswer = "Сау болыңыз!"
        ),
        Question(
            id = 4,
            title = "Как переводится 'Жақсы, рахмет!'?",
            variants = listOf("Спасибо", "Хорошо, спасибо!", "До встречи!", "Как дела?"),
            correctAnswer = "Хорошо, спасибо!"
        ),
        Question(
            id = 5,
            title = "Как сказать 'До встречи'?",
            variants = listOf("Сау бол!", "Кездескенше!", "Қалың қалай?", "Жақсы, рахмет!"),
            correctAnswer = "Кездескенше!"
        ),
        Question(
            id = 6,
            title = "Какой ответ правильный на 'Ассалаумағалейкум'?",
            variants = listOf("Қалың қалай?", "Сәлем!", "Уағалейкумассалам!", "Қайырлы түн!"),
            correctAnswer = "Уағалейкумассалам!"
        )
    )
)
val testColors = Test(
    id = 2,
    category = "Цвета",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Қара'?",
            variants = listOf("Красный", "Чёрный", "Синий", "Белый"),
            correctAnswer = "Чёрный"
        ),
        Question(
            id = 2,
            title = "Что означает 'Көк'?",
            variants = listOf("Фиолетовый", "Синий", "Голубой", "Жёлтый"),
            correctAnswer = "Синий"
        ),
        Question(
            id = 3,
            title = "Какое слово обозначает 'Жёлтый'?",
            variants = listOf("Қара", "Сары", "Қызыл", "Ақ"),
            correctAnswer = "Сары"
        ),
        Question(
            id = 4,
            title = "Как перевести 'Қызыл'?",
            variants = listOf("Красный", "Синий", "Чёрный", "Розовый"),
            correctAnswer = "Красный"
        ),
        Question(
            id = 5,
            title = "Какое слово обозначает 'Розовый'?",
            variants = listOf("Көк", "Қызғылт", "Алтын", "Қоңыр"),
            correctAnswer = "Қызғылт"
        ),
        Question(
            id = 6,
            title = "Как переводится 'Күміс'?",
            variants = listOf("Золотой", "Фиолетовый", "Серебряный", "Светло-зелёный"),
            correctAnswer = "Серебряный"
        )
    )

)

fun getAllTests():List<Test>{
    return listOf(
        testColors, testGreetings
    )
}

