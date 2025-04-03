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

val testNumbers = Test(
    id = 3,
    category = "Цифры",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Бір'?",
            variants = listOf("0", "2", "1", "3"),
            correctAnswer = "1"
        ),
        Question(
            id = 2,
            title = "Что означает 'Жүз'?",
            variants = listOf("1000", "10", "100", "20"),
            correctAnswer = "100"
        ),
        Question(
            id = 3,
            title = "Как переводится 'Сегіз'?",
            variants = listOf("6", "8", "7", "9"),
            correctAnswer = "8"
        ),
        Question(
            id = 4,
            title = "Что означает 'Мың'?",
            variants = listOf("100", "10", "1000", "1"),
            correctAnswer = "1000"
        ),
        Question(
            id = 5,
            title = "Как переводится 'Төрт'?",
            variants = listOf("3", "5", "4", "2"),
            correctAnswer = "4"
        ),
        Question(
            id = 6,
            title = "Что означает 'Қырық'?",
            variants = listOf("30", "40", "50", "60"),
            correctAnswer = "40"
        )
    )
)
val testFamily = Test(
    id = 4,
    category = "Семья",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Ана'?",
            variants = listOf("Папа", "Мама", "Сестра", "Дочь"),
            correctAnswer = "Мама"
        ),
        Question(
            id = 2,
            title = "Что означает 'Аға'?",
            variants = listOf("Младший брат", "Старший брат", "Дедушка", "Отец"),
            correctAnswer = "Старший брат"
        ),
        Question(
            id = 3,
            title = "Как переводится 'Әже'?",
            variants = listOf("Бабушка", "Тётя", "Сестра", "Мама"),
            correctAnswer = "Бабушка"
        ),
        Question(
            id = 4,
            title = "Что означает 'Күйеу'?",
            variants = listOf("Муж", "Жена", "Ребёнок", "Брат"),
            correctAnswer = "Муж"
        ),
        Question(
            id = 5,
            title = "Как переводится 'Қыз'?",
            variants = listOf("Сын", "Сестра", "Дочь", "Жена"),
            correctAnswer = "Дочь"
        ),
        Question(
            id = 6,
            title = "Что означает 'Бауыр'?",
            variants = listOf("Родственник", "Брат", "Отец", "Муж"),
            correctAnswer = "Брат"
        )
    )
)
val testAnimals = Test(
    id = 5,
    category = "Животные",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Ит'?",
            variants = listOf("Кошка", "Собака", "Лошадь", "Корова"),
            correctAnswer = "Собака"
        ),
        Question(
            id = 2,
            title = "Что означает 'Аю'?",
            variants = listOf("Барс", "Ёж", "Медведь", "Лиса"),
            correctAnswer = "Медведь"
        ),
        Question(
            id = 3,
            title = "Как переводится 'Тауық'?",
            variants = listOf("Курица", "Петух", "Овца", "Утка"),
            correctAnswer = "Курица"
        ),
        Question(
            id = 4,
            title = "Что означает 'Жолбарыс'?",
            variants = listOf("Тигр", "Лев", "Барс", "Медведь"),
            correctAnswer = "Тигр"
        ),
        Question(
            id = 5,
            title = "Как переводится 'Қоян'?",
            variants = listOf("Ёж", "Кролик", "Лиса", "Ворона"),
            correctAnswer = "Кролик"
        ),
        Question(
            id = 6,
            title = "Что означает 'Бүркіт'?",
            variants = listOf("Лебедь", "Орёл", "Голубь", "Ворона"),
            correctAnswer = "Орёл"
        )
    )
)
val testVerbs = Test(
    id = 6,
    category = "Часто употребляемые глаголы",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Жүру'?",
            variants = listOf("Спать", "Ходить", "Бегать", "Слушать"),
            correctAnswer = "Ходить"
        ),
        Question(
            id = 2,
            title = "Что означает 'Келу'?",
            variants = listOf("Уходить", "Приходить", "Сказать", "Смеяться"),
            correctAnswer = "Приходить"
        ),
        Question(
            id = 3,
            title = "Как переводится 'Ұйықтау'?",
            variants = listOf("Слушать", "Писать", "Спать", "Играть"),
            correctAnswer = "Спать"
        ),
        Question(
            id = 4,
            title = "Что означает 'Оқу'?",
            variants = listOf("Читать / Учиться", "Работать", "Отдыхать", "Плакать"),
            correctAnswer = "Читать / Учиться"
        ),
        Question(
            id = 5,
            title = "Как переводится 'Сөйлеу'?",
            variants = listOf("Писать", "Слушать", "Говорить", "Плакать"),
            correctAnswer = "Говорить"
        ),
        Question(
            id = 6,
            title = "Что означает 'Күлу'?",
            variants = listOf("Плакать", "Смеяться", "Видеть", "Работать"),
            correctAnswer = "Смеяться"
        )
    )
)

val testTime = Test(
    id = 7,
    category = "Время и дни недели",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Бүгін'?",
            variants = listOf("Вчера", "Сегодня", "Завтра", "День"),
            correctAnswer = "Сегодня"
        ),
        Question(
            id = 2,
            title = "Что означает 'Ертең'?",
            variants = listOf("Утро", "Ночь", "Завтра", "Вчера"),
            correctAnswer = "Завтра"
        ),
        Question(
            id = 3,
            title = "Как переводится 'Сәрсенбі'?",
            variants = listOf("Среда", "Пятница", "Воскресенье", "Четверг"),
            correctAnswer = "Среда"
        ),
        Question(
            id = 4,
            title = "Что означает 'Кеш'?",
            variants = listOf("День", "Утро", "Вечер", "Ночь"),
            correctAnswer = "Вечер"
        ),
        Question(
            id = 5,
            title = "Как переводится 'Жұма'?",
            variants = listOf("Суббота", "Пятница", "Среда", "Понедельник"),
            correctAnswer = "Пятница"
        ),
        Question(
            id = 6,
            title = "Что означает 'Сағат'?",
            variants = listOf("Минута", "Час", "Секунда", "День"),
            correctAnswer = "Час"
        )
    )
)

val testFood = Test(
    id = 8,
    category = "Еда",
    questions = listOf(
        Question(
            id = 1,
            title = "Как переводится 'Нан'?",
            variants = listOf("Соль", "Молоко", "Хлеб", "Мясо"),
            correctAnswer = "Хлеб"
        ),
        Question(
            id = 2,
            title = "Что означает 'Сүт'?",
            variants = listOf("Чай", "Сметана", "Молоко", "Вода"),
            correctAnswer = "Молоко"
        ),
        Question(
            id = 3,
            title = "Как переводится 'Ет'?",
            variants = listOf("Мясо", "Овощи", "Фрукты", "Рыба"),
            correctAnswer = "Мясо"
        ),
        Question(
            id = 4,
            title = "Что означает 'Қант'?",
            variants = listOf("Сахар", "Соль", "Мёд", "Масло"),
            correctAnswer = "Сахар"
        ),
        Question(
            id = 5,
            title = "Как переводится 'Қарбыз'?",
            variants = listOf("Арбуз", "Груша", "Яблоко", "Виноград"),
            correctAnswer = "Арбуз"
        ),
        Question(
            id = 6,
            title = "Что означает 'Сәбіз'?",
            variants = listOf("Лук", "Капуста", "Морковь", "Картофель"),
            correctAnswer = "Морковь"
        )
    )
)


fun getAllTests():List<Test>{
    return listOf(
        testColors, testGreetings, testFamily, testVerbs, testNumbers, testAnimals, testTime, testFood
    )
}

